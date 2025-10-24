package ma.projet.classes;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EmployeTacheId implements Serializable {
    private int employe;
    private int tache;

    public EmployeTacheId() {}

    public EmployeTacheId(int employe, int tache) {
        this.employe = employe;
        this.tache = tache;
    }

    // Getters & Setters
    public int getEmploye() { return employe; }
    public void setEmploye(int employe) { this.employe = employe; }

    public int getTache() { return tache; }
    public void setTache(int tache) { this.tache = tache; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeTacheId)) return false;
        EmployeTacheId that = (EmployeTacheId) o;
        return employe == that.employe && tache == that.tache;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(employe, tache);
    }
}