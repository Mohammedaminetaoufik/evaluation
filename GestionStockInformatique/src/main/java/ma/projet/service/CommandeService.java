package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class CommandeService implements IDao<Commande, Integer> {

    @Override
    public Commande save(Commande o) {
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
    public Commande update(Commande o) {
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
    public boolean delete(Commande o) {
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
    public Commande findById(Integer k) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Commande.class, k);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Commande> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Commande", Commande.class).list();
        } finally {
            session.close();
        }
    }

    // Recherche par date
    public List<Commande> findByDate(Date date) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Commande c WHERE c.date = :date";
            Query<Commande> query = session.createQuery(hql, Commande.class);
            query.setParameter("date", date);
            return query.list();
        } finally {
            session.close();
        }
    }
}