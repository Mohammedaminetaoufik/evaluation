package ma.projet.beans;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mariage")
@NamedQueries({
        @NamedQuery(
                name = "Femme.findMarriedAtLeastTwice",
                query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2"
        )
})
@NamedNativeQuery(
        name = "Femme.countChildrenBetweenDates",
        query = "SELECT COUNT(*) FROM mariage m JOIN femme f ON m.femme_id = f.id WHERE f.id = ?1 AND m.dateDebut BETWEEN ?2 AND ?3",
        resultClass = Long.class
)
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dateDebut", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "dateFin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "nbrEnfant", nullable = false)
    private int nbrEnfant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homme_id", nullable = false)
    private Homme homme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "femme_id", nullable = false)
    private Femme femme;

    // Constructeurs
    public Mariage() {}

    public Mariage(Date dateDebut, Date dateFin, int nbrEnfant, Homme homme, Femme femme) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
        this.homme = homme;
        this.femme = femme;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }

    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }

    public int getNbrEnfant() { return nbrEnfant; }
    public void setNbrEnfant(int nbrEnfant) { this.nbrEnfant = nbrEnfant; }

    public Homme getHomme() { return homme; }
    public void setHomme(Homme homme) { this.homme = homme; }

    public Femme getFemme() { return femme; }
    public void setFemme(Femme femme) { this.femme = femme; }

    @Override
    public String toString() {
        return "Mariage{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbrEnfant=" + nbrEnfant +
                ", homme=" + homme +
                ", femme=" + femme +
                '}';
    }
}