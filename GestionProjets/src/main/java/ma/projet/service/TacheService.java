package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache, Integer> {

    @Override
    public Tache save(Tache o) {
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
    public Tache update(Tache o) {
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
    public boolean delete(Tache o) {
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
    public Tache findById(Integer k) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Tache.class, k);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Tache> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Tache", Tache.class).list();
        } finally {
            session.close();
        }
    }

    // Méthode : Tâches avec prix > 1000 DH (requête nommée)
    public List<Tache> findTachesPrixSuperieurA1000() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Tache> query = session.createNamedQuery("Tache.findByPriceAbove1000", Tache.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode : Tâches réalisées entre deux dates
    public List<Tache> findTachesRealiseesEntreDates(Date startDate, Date endDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = """
                SELECT DISTINCT et.tache FROM EmployeTache et
                WHERE et.dateDebutReelle BETWEEN :start AND :end
                OR et.dateFinReelle BETWEEN :start AND :end
                """;
            Query<Tache> query = session.createQuery(hql, Tache.class);
            query.setParameter("start", startDate);
            query.setParameter("end", endDate);
            return query.list();
        } finally {
            session.close();
        }
    }
}