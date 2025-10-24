package ma.projet.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "ligne_commande_produit")
@IdClass(LigneCommandeProduitId.class)
public class LigneCommandeProduit {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(name = "quantite", nullable = false)
    private int quantite;

    // Constructeurs
    public LigneCommandeProduit() {}

    public LigneCommandeProduit(Commande commande, Produit produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
    }

    // Getters & Setters
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    @Override
    public String toString() {
        return "LigneCommandeProduit{" +
                "commande=" + commande +
                ", produit=" + produit +
                ", quantite=" + quantite +
                '}';
    }
}