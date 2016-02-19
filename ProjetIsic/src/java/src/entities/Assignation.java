/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author leonardo.distasio
 */
@Entity
@Table(name = "assignation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assignation.findAll", query = "SELECT a FROM Assignation a"),
    @NamedQuery(name = "Assignation.findByAssignationId", query = "SELECT a FROM Assignation a WHERE a.assignationId = :assignationId"),
    @NamedQuery(name = "Assignation.findByTauxEngagmentProjet", query = "SELECT a FROM Assignation a WHERE a.tauxEngagmentProjet = :tauxEngagmentProjet")})
public class Assignation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assignation_id")
    private Integer assignationId;
    @Column(name = "taux_engagment_projet")
    private Integer tauxEngagmentProjet;
    @JoinColumn(name = "assistant_id", referencedColumnName = "assistant_id")
    @ManyToOne(optional = false)
    private Assistant assistantId;
    @JoinColumn(name = "projet_id", referencedColumnName = "projet_id")
    @ManyToOne(optional = false)
    private Projet projetId;

    public Assignation() {
    }

    public Assignation(Integer assignationId) {
        this.assignationId = assignationId;
    }

    public Integer getAssignationId() {
        return assignationId;
    }

    public void setAssignationId(Integer assignationId) {
        this.assignationId = assignationId;
    }

    public Integer getTauxEngagmentProjet() {
        return tauxEngagmentProjet;
    }

    public void setTauxEngagmentProjet(Integer tauxEngagmentProjet) {
        this.tauxEngagmentProjet = tauxEngagmentProjet;
    }

    public Assistant getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(Assistant assistantId) {
        this.assistantId = assistantId;
    }

    public Projet getProjetId() {
        return projetId;
    }

    public void setProjetId(Projet projetId) {
        this.projetId = projetId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assignationId != null ? assignationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assignation)) {
            return false;
        }
        Assignation other = (Assignation) object;
        if ((this.assignationId == null && other.assignationId != null) || (this.assignationId != null && !this.assignationId.equals(other.assignationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "src.entities.Assignation[ assignationId=" + assignationId + " ]";
    }
    
}
