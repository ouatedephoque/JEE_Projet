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
import src.entities.GroupeCompetence;

/**
 *
 * @author leonardo.distasio
 */
@Stateless
public class GroupeCompetenceFacade extends AbstractFacade<GroupeCompetence> {
    @PersistenceContext(unitName = "ProjetIsicPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupeCompetenceFacade() {
        super(GroupeCompetence.class);
    }
    
    public List<Assistant> getAllAssistantByGroupAndPourcent(GroupeCompetence gc, Integer pourcent)
    {
        List<Assistant> listAllAssistant = em.createNamedQuery("Assistant.findByGroupCompetenceAndTauxEngagement")
                                             .setParameter("groupeId", gc)
                                             .setParameter("tauxEngagement", pourcent).getResultList();
        
        return listAllAssistant;
    }
    
    public List<Assistant> getAllAssistantByPourcent(Integer pourcent)
    {
        List<Assistant> listAllAssistant = em.createNamedQuery("Assistant.findByMaxTauxEngagement")
                                             .setParameter("tauxEngagement", pourcent).getResultList();
        
        return listAllAssistant;
    }
    
}
