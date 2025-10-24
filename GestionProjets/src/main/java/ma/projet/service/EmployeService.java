package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeService implements IDao<Employe, Integer> {

    @Override
    public Employe save(Employe o) {
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
    public Employe update(Employe o) {
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
    public boolean delete(Employe o) {
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
    public Employe findById(Integer k) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Employe.class, k);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Employe> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Employe", Employe.class).list();
        } finally {
            session.close();
        }
    }

    // Méthode : Afficher les tâches réalisées par un employé
    public List<Tache> findTachesRealiseesByEmploye(int employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = """
                SELECT DISTINCT et.tache FROM EmployeTache et
                WHERE et.employe.id = :empId
                """;
            Query<Tache> query = session.createQuery(hql, Tache.class);
            query.setParameter("empId", employeId);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode : Afficher les projets gérés par un employé
    public List<Projet> findProjetsGeresByEmploye(int employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Projet p WHERE p.chefProjet.id = :empId";
            Query<Projet> query = session.createQuery(hql, Projet.class);
            query.setParameter("empId", employeId);
            return query.list();
        } finally {
            session.close();
        }
    }
}