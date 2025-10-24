package ma.projet.beans;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "femme")
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages;

    // Constructeurs
    public Femme() {}

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    // Getters & Setters
    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }

    @Override
    public String toString() {
        return "Femme{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", telephone='" + getTelephone() + '\'' +
                ", adresse='" + getAdresse() + '\'' +
                ", dateNaissance=" + getDateNaissance() +
                '}';
    }
}