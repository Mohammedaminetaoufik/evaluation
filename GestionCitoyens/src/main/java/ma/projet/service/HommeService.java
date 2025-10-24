package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.projet.util.HibernateUtil;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme, Integer> {

    @Override
    public Homme save(Homme o) {
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
    public Homme update(Homme o) {
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
    public boolean delete(Homme o) {
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
    public Homme findById(Integer k) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Homme.class, k);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Homme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Homme", Homme.class).list();
        } finally {
            session.close();
        }
    }

    // Méthode : Afficher les épouses d’un homme entre deux dates
    public List<Femme> findEpousesByHommeAndDates(int hommeId, Date startDate, Date endDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = """
                SELECT DISTINCT m.femme FROM Mariage m
                WHERE m.homme.id = :hommeId
                AND m.dateDebut BETWEEN :start AND :end
                """;
            Query<Femme> query = session.createQuery(hql, Femme.class);
            query.setParameter("hommeId", hommeId);
            query.setParameter("start", startDate);
            query.setParameter("end", endDate);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode : Afficher les mariages d’un homme avec tous les détails
    public void afficherMariagesDetails(int hommeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = """
                SELECT m FROM Mariage m
                WHERE m.homme.id = :hommeId
                ORDER BY m.dateDebut DESC
                """;
            Query<Mariage> query = session.createQuery(hql, Mariage.class);
            query.setParameter("hommeId", hommeId);

            List<Mariage> mariages = query.list();
            if (mariages.isEmpty()) {
                System.out.println("Aucun mariage trouvé pour cet homme.");
                return;
            }

            Homme homme = session.get(Homme.class, hommeId);
            System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
            System.out.println("Mariages En Cours :");

            int count = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() == null) {
                    System.out.printf("%d. Femme : %s %s   Date Début : %s    Nbr Enfants : %d%n",
                            count++,
                            m.getFemme().getNom(),
                            m.getFemme().getPrenom(),
                            m.getDateDebut(),
                            m.getNbrEnfant());
                }
            }

            System.out.println("\nMariages échoués :");
            count = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() != null) {
                    System.out.printf("%d. Femme : %s %s   Date Début : %s    Date Fin : %s    Nbr Enfants : %d%n",
                            count++,
                            m.getFemme().getNom(),
                            m.getFemme().getPrenom(),
                            m.getDateDebut(),
                            m.getDateFin(),
                            m.getNbrEnfant());
                }
            }
        } finally {
            session.close();
        }
    }
}