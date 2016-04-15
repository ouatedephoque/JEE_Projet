/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controllers;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import src.entities.Assistant;
import src.facades.AssistantFacade;
import sun.misc.BASE64Encoder;

/**
 *
 * @author leonardo.distasio
 */

@ManagedBean(name = "userAuthentification")
@SessionScoped
public class UserAuthentification {
    
    private Assistant assistant;
    private String login;
    private String password;
    
    @EJB 
    private AssistantFacade ejbAssistant;
    
    public UserAuthentification()
    {
        
    }
    
    public String logout()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "/index?faces-redirect=true";
    }

    public Assistant getAssistant() 
    {
        if(assistant == null)
        {
            HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            login = req.getRemoteUser();
            assistant = ejbAssistant.getAssistantByLogin(login);
        }
        return assistant;
    }

    public String getLogin() 
    {
        if(login == null)
        {
            HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            setLogin(req.getRemoteUser());
        }
        return login;
    }

    public void setLogin(String login) 
    {
        if(assistant == null || !this.login.equals(login))
        {
            assistant = ejbAssistant.getAssistantByLogin(login);
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    private String getEncryptedPasswd(String pswd)
    {
        try 
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pswd.getBytes());
            return new BASE64Encoder().encode(md.digest());
        } 
        catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserAuthentification.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
}
