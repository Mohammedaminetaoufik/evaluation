package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class ProjetService implements IDao<Projet, Integer> {

    @Override
    public Projet save(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return o;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Projet update(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return o;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Projet findById(Integer k) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Projet.class, k);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Projet> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Projet", Projet.class).list();
        } finally {
            session.close();
        }
    }

    // Méthode : Afficher les tâches planifiées pour un projet
    public List<Tache> findTachesPlanifieesByProjet(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Tache t WHERE t.projet.id = :projId";
            Query<Tache> query = session.createQuery(hql, Tache.class);
            query.setParameter("projId", projetId);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode : Afficher les tâches réalisées avec dates réelles
    public void afficherTachesRealiseesByProjet(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = """
                SELECT et.tache, et.dateDebutReelle, et.dateFinReelle FROM EmployeTache et
                WHERE et.tache.projet.id = :projId
                """;
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("projId", projetId);

            List<Object[]> results = query.list();
            if (results.isEmpty()) {
                System.out.println("Aucune tâche réalisée pour ce projet.");
                return;
            }

            // Affichage
            Projet projet = session.get(Projet.class, projetId);
            System.out.println("Projet : " + projetId + "      Nom : " + projet.getNom() +
                    "     Date début : " + projet.getDateDebut());
            System.out.println("Liste des tâches:");
            System.out.printf("%-5s %-20s %-20s %-20s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");

            for (Object[] row : results) {
                Tache t = (Tache) row[0];
                Date debut = (Date) row[1];
                Date fin = (Date) row[2];
                System.out.printf("%-5d %-20s %-20s %-20s%n",
                        t.getId(), t.getNom(),
                        debut != null ? debut.toString() : "",
                        fin != null ? fin.toString() : "");
            }
        } finally {
            session.close();
        }
    }
}