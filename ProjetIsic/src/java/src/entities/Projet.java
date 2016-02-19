/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author leonardo.distasio
 */
@Entity
@Table(name = "projet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projet.findAll", query = "SELECT p FROM Projet p"),
    @NamedQuery(name = "Projet.findByProjetId", query = "SELECT p FROM Projet p WHERE p.projetId = :projetId"),
    @NamedQuery(name = "Projet.findByNom", query = "SELECT p FROM Projet p WHERE p.nom = :nom"),
    @NamedQuery(name = "Projet.findBySagex", query = "SELECT p FROM Projet p WHERE p.sagex = :sagex"),
    @NamedQuery(name = "Projet.findByDateDebut", query = "SELECT p FROM Projet p WHERE p.dateDebut = :dateDebut"),
    @NamedQuery(name = "Projet.findByDateFin", query = "SELECT p FROM Projet p WHERE p.dateFin = :dateFin"),
    @NamedQuery(name = "Projet.findByTarifHoraireAssistant", query = "SELECT p FROM Projet p WHERE p.tarifHoraireAssistant = :tarifHoraireAssistant"),
    @NamedQuery(name = "Projet.findByTarifHoraireAdjoint", query = "SELECT p FROM Projet p WHERE p.tarifHoraireAdjoint = :tarifHoraireAdjoint"),
    @NamedQuery(name = "Projet.findByBudget", query = "SELECT p FROM Projet p WHERE p.budget = :budget"),
    @NamedQuery(name = "Projet.findByChef", query = "SELECT p FROM Projet p WHERE p.chef = :chef")})
public class Projet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "projet_id")
    private Integer projetId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sagex")
    private long sagex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_debut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;
    @Column(name = "date_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;
    @Column(name = "tarif_horaire_assistant")
    private BigInteger tarifHoraireAssistant;
    @Column(name = "tarif_horaire_adjoint")
    private BigInteger tarifHoraireAdjoint;
    @Column(name = "budget")
    private BigInteger budget;
    @Size(max = 40)
    @Column(name = "chef")
    private String chef;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projetId")
    private Collection<Assignation> assignationCollection;

    public Projet() {
    }

    public Projet(Integer projetId) {
        this.projetId = projetId;
    }

    public Projet(Integer projetId, String nom, long sagex, Date dateDebut) {
        this.projetId = projetId;
        this.nom = nom;
        this.sagex = sagex;
        this.dateDebut = dateDebut;
    }

    public Integer getProjetId() {
        return projetId;
    }

    public void setProjetId(Integer projetId) {
        this.projetId = projetId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getSagex() {
        return sagex;
    }

    public void setSagex(long sagex) {
        this.sagex = sagex;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public BigInteger getTarifHoraireAssistant() {
        return tarifHoraireAssistant;
    }

    public void setTarifHoraireAssistant(BigInteger tarifHoraireAssistant) {
        this.tarifHoraireAssistant = tarifHoraireAssistant;
    }

    public BigInteger getTarifHoraireAdjoint() {
        return tarifHoraireAdjoint;
    }

    public void setTarifHoraireAdjoint(BigInteger tarifHoraireAdjoint) {
        this.tarifHoraireAdjoint = tarifHoraireAdjoint;
    }

    public BigInteger getBudget() {
        return budget;
    }

    public void setBudget(BigInteger budget) {
        this.budget = budget;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    @XmlTransient
    public Collection<Assignation> getAssignationCollection() {
        return assignationCollection;
    }

    public void setAssignationCollection(Collection<Assignation> assignationCollection) {
        this.assignationCollection = assignationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projetId != null ? projetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projet)) {
            return false;
        }
        Projet other = (Projet) object;
        if ((this.projetId == null && other.projetId != null) || (this.projetId != null && !this.projetId.equals(other.projetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "src.entities.Projet[ projetId=" + projetId + " ]";
    }
    
}
