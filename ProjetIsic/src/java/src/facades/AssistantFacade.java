/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.facades;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import src.entities.Assistant;

/**
 *
 * @author leonardo.distasio
 */
@Stateless
public class AssistantFacade extends AbstractFacade<Assistant> {
    @PersistenceContext(unitName = "ProjetIsicPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AssistantFacade() {
        super(Assistant.class);
    }
    
}
