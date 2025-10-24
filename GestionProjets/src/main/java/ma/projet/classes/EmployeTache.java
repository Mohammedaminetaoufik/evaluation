package ma.projet.classes;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "employe_tache")
@IdClass(EmployeTacheId.class)
public class EmployeTache {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employe_id", nullable = false)
    private Employe employe;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tache_id", nullable = false)
    private Tache tache;

    @Column(name = "dateDebutReelle", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Column(name = "dateFinReelle", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    // Constructeurs
    public EmployeTache() {}

    public EmployeTache(Employe employe, Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
    }

    // Getters & Setters
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }

    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }

    public Date getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(Date dateDebutReelle) { this.dateDebutReelle = dateDebutReelle; }

    public Date getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(Date dateFinReelle) { this.dateFinReelle = dateFinReelle; }

    @Override
    public String toString() {
        return "EmployeTache{" +
                "employe=" + employe +
                ", tache=" + tache +
                ", dateDebutReelle=" + dateDebutReelle +
                ", dateFinReelle=" + dateFinReelle +
                '}';
    }
}