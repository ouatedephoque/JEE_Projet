/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author leonardo.distasio
 */
@Entity
@Table(name = "groupe_competence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupeCompetence.findAll", query = "SELECT g FROM GroupeCompetence g"),
    @NamedQuery(name = "GroupeCompetence.findById", query = "SELECT g FROM GroupeCompetence g WHERE g.id = :id"),
    @NamedQuery(name = "GroupeCompetence.findByAcronyme", query = "SELECT g FROM GroupeCompetence g WHERE g.acronyme = :acronyme"),
    @NamedQuery(name = "GroupeCompetence.findByNom", query = "SELECT g FROM GroupeCompetence g WHERE g.nom = :nom")})
public class GroupeCompetence implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "acronyme")
    private String acronyme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nom")
    private String nom;
    @OneToMany(mappedBy = "groupeId")
    private Collection<Assistant> assistantCollection;

    public GroupeCompetence() {
    }

    public GroupeCompetence(Integer id) {
        this.id = id;
    }

    public GroupeCompetence(Integer id, String acronyme, String nom) {
        this.id = id;
        this.acronyme = acronyme;
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlTransient
    public Collection<Assistant> getAssistantCollection() {
        return assistantCollection;
    }

    public void setAssistantCollection(Collection<Assistant> assistantCollection) {
        this.assistantCollection = assistantCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupeCompetence)) {
            return false;
        }
        GroupeCompetence other = (GroupeCompetence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "src.entities.GroupeCompetence[ id=" + id + " ]";
    }
    
}
