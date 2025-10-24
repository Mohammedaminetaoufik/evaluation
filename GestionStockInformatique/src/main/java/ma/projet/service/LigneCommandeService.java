package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit, Integer> {

    @Override
    public LigneCommandeProduit save(LigneCommandeProduit o) {
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
    public LigneCommandeProduit update(LigneCommandeProduit o) {
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
    public boolean delete(LigneCommandeProduit o) {
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
    public LigneCommandeProduit findById(Integer k) {
        // Pas d'ID simple — à adapter si besoin
        return null;
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM LigneCommandeProduit", LigneCommandeProduit.class).list();
        } finally {
            session.close();
        }
    }

    // Ajout d'une méthode utile : trouver lignes par commande
    public List<LigneCommandeProduit> findByCommande(int commandeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM LigneCommandeProduit lc WHERE lc.commande.id = :cmdId";
            Query<LigneCommandeProduit> query = session.createQuery(hql, LigneCommandeProduit.class);
            query.setParameter("cmdId", commandeId);
            return query.list();
        } finally {
            session.close();
        }
    }
}