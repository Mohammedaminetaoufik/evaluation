package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache, Integer> {

    @Override
    public EmployeTache save(EmployeTache o) {
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
    public EmployeTache update(EmployeTache o) {
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
    public boolean delete(EmployeTache o) {
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
    public EmployeTache findById(Integer k) {
        // Pas d'ID simple — à adapter si besoin
        return null;
    }

    @Override
    public List<EmployeTache> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM EmployeTache", EmployeTache.class).list();
        } finally {
            session.close();
        }
    }

    // Ajout d'une méthode utile : trouver lignes par employé
    public List<EmployeTache> findByEmploye(int employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM EmployeTache et WHERE et.employe.id = :empId";
            Query<EmployeTache> query = session.createQuery(hql, EmployeTache.class);
            query.setParameter("empId", employeId);
            return query.list();
        } finally {
            session.close();
        }
    }
}