/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import src.entities.Assignation;

/**
 *
 * @author leonardo.distasio
 */
@Stateless
public class AssignationFacade extends AbstractFacade<Assignation> {
    @PersistenceContext(unitName = "ProjetIsicPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AssignationFacade() {
        super(Assignation.class);
    }
    
}
