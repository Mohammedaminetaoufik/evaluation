package ma.projet.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "produit")
@NamedQueries({
        @NamedQuery(
                name = "Produit.findByPriceAbove100",
                query = "SELECT p FROM Produit p WHERE p.prix > 100"
        )
})
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "reference", nullable = false, unique = true)
    private String reference;

    @Column(name = "prix", nullable = false)
    private float prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommandeProduit> lignesCommande;

    // Constructeurs
    public Produit() {}

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public List<LigneCommandeProduit> getLignesCommande() { return lignesCommande; }
    public void setLignesCommande(List<LigneCommandeProduit> lignesCommande) { this.lignesCommande = lignesCommande; }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", prix=" + prix +
                ", categorieId=" + (categorie != null ? categorie.getId() : null) +
                '}';
    }
}