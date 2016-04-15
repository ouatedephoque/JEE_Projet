/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "assistant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assistant.findAll", query = "SELECT a FROM Assistant a"),
    @NamedQuery(name = "Assistant.findByAssistantId", query = "SELECT a FROM Assistant a WHERE a.assistantId = :assistantId"),
    @NamedQuery(name = "Assistant.findByNom", query = "SELECT a FROM Assistant a WHERE a.nom = :nom"),
    @NamedQuery(name = "Assistant.findByPrenom", query = "SELECT a FROM Assistant a WHERE a.prenom = :prenom"),
    @NamedQuery(name = "Assistant.findByAdresse", query = "SELECT a FROM Assistant a WHERE a.adresse = :adresse"),
    @NamedQuery(name = "Assistant.findByAbreviation", query = "SELECT a FROM Assistant a WHERE a.abreviation = :abreviation"),
    @NamedQuery(name = "Assistant.findByDomaine", query = "SELECT a FROM Assistant a WHERE a.domaine = :domaine"),
    @NamedQuery(name = "Assistant.findByFonction", query = "SELECT a FROM Assistant a WHERE a.fonction = :fonction"),
    @NamedQuery(name = "Assistant.findByNombreHeureAnnuelle", query = "SELECT a FROM Assistant a WHERE a.nombreHeureAnnuelle = :nombreHeureAnnuelle"),
    @NamedQuery(name = "Assistant.findByTauxEngagement", query = "SELECT a FROM Assistant a WHERE a.tauxEngagement = :tauxEngagement"),
    @NamedQuery(name = "Assistant.findByTauxEnseignement", query = "SELECT a FROM Assistant a WHERE a.tauxEnseignement = :tauxEnseignement"),
    @NamedQuery(name = "Assistant.findByTauxTachesInternes", query = "SELECT a FROM Assistant a WHERE a.tauxTachesInternes = :tauxTachesInternes"),
    @NamedQuery(name = "Assistant.findByGroupCompetence", query = "SELECT a FROM Assistant a WHERE a.groupeId = :groupeId"),
    @NamedQuery(name = "Assistant.findByMaxTauxEngagement", query = "SELECT a FROM Assistant a WHERE a.tauxEngagement <= :tauxEngagement"),
    @NamedQuery(name = "Assistant.findByGroupCompetenceAndTauxEngagement", query = "SELECT a FROM Assistant a WHERE a.groupeId = :groupeId AND a.tauxEngagement <= :tauxEngagement"),
    @NamedQuery(name = "Assistant.findByLoginPassword", query = "SELECT a FROM Assistant a WHERE a.login = :login AND a.password = :password"),
    @NamedQuery(name = "Assistant.findByLogin", query = "SELECT a FROM Assistant a WHERE a.login = :login")})
public class Assistant implements Serializable {
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assistant_id")
    private Integer assistantId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "prenom")
    private String prenom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "adresse")
    private String adresse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "abreviation")
    private String abreviation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "domaine")
    private String domaine;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "fonction")
    private String fonction;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nombre_heure_annuelle")
    private int nombreHeureAnnuelle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "taux_engagement")
    private int tauxEngagement;
    @Basic(optional = false)
    @NotNull
    @Column(name = "taux_enseignement")
    private int tauxEnseignement;
    @Basic(optional = false)
    @NotNull
    @Column(name = "taux_taches_internes")
    private int tauxTachesInternes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assistantId")
    private Collection<Assignation> assignationCollection;
    @JoinColumn(name = "groupe_id", referencedColumnName = "id")
    @ManyToOne
    private GroupeCompetence groupeId;

    public Assistant() {
    }

    public Assistant(Integer assistantId) {
        this.assistantId = assistantId;
    }

    public Assistant(Integer assistantId, String nom, String prenom, String adresse, String abreviation, String domaine, String fonction, int nombreHeureAnnuelle, int tauxEngagement, int tauxEnseignement, int tauxTachesInternes) {
        this.assistantId = assistantId;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.abreviation = abreviation;
        this.domaine = domaine;
        this.fonction = fonction;
        this.nombreHeureAnnuelle = nombreHeureAnnuelle;
        this.tauxEngagement = tauxEngagement;
        this.tauxEnseignement = tauxEnseignement;
        this.tauxTachesInternes = tauxTachesInternes;
    }

    public Integer getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(Integer assistantId) {
        this.assistantId = assistantId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public int getNombreHeureAnnuelle() {
        return nombreHeureAnnuelle;
    }

    public void setNombreHeureAnnuelle(int nombreHeureAnnuelle) {
        this.nombreHeureAnnuelle = nombreHeureAnnuelle;
    }

    public int getTauxEngagement() {
        return tauxEngagement;
    }

    public void setTauxEngagement(int tauxEngagement) {
        this.tauxEngagement = tauxEngagement;
    }

    public int getTauxEnseignement() {
        return tauxEnseignement;
    }

    public void setTauxEnseignement(int tauxEnseignement) {
        this.tauxEnseignement = tauxEnseignement;
    }

    public int getTauxTachesInternes() {
        return tauxTachesInternes;
    }

    public void setTauxTachesInternes(int tauxTachesInternes) {
        this.tauxTachesInternes = tauxTachesInternes;
    }

    @XmlTransient
    public Collection<Assignation> getAssignationCollection() {
        return assignationCollection;
    }

    public void setAssignationCollection(Collection<Assignation> assignationCollection) {
        this.assignationCollection = assignationCollection;
    }

    public GroupeCompetence getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(GroupeCompetence groupeId) {
        this.groupeId = groupeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assistantId != null ? assistantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assistant)) {
            return false;
        }
        Assistant other = (Assistant) object;
        if ((this.assistantId == null && other.assistantId != null) || (this.assistantId != null && !this.assistantId.equals(other.assistantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "src.entities.Assistant[ assistantId=" + assistantId + " ]";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
