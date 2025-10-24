package ma.projet.classes;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LigneCommandeProduitId implements Serializable {
    private int commande;
    private int produit;

    public LigneCommandeProduitId() {}

    public LigneCommandeProduitId(int commande, int produit) {
        this.commande = commande;
        this.produit = produit;
    }

    // Getters & Setters
    public int getCommande() { return commande; }
    public void setCommande(int commande) { this.commande = commande; }

    public int getProduit() { return produit; }
    public void setProduit(int produit) { this.produit = produit; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LigneCommandeProduitId)) return false;
        LigneCommandeProduitId that = (LigneCommandeProduitId) o;
        return commande == that.commande && produit == that.produit;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(commande, produit);
    }
}