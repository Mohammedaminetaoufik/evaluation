package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme, Integer> {

    @Override
    public Femme save(Femme o) {
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
    public Femme update(Femme o) {
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
    public boolean delete(Femme o) {
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
    public Femme findById(Integer k) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Femme.class, k);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Femme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Femme", Femme.class).list();
        } finally {
            session.close();
        }
    }

    // Méthode : Nombre d’enfants d’une femme entre deux dates (requête native nommée)
    public long countChildrenBetweenDates(int femmeId, Date startDate, Date endDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            NativeQuery<Long> query = (NativeQuery<Long>) session.createNamedQuery("Femme.countChildrenBetweenDates", Long.class);
            query.setParameter(1, femmeId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    // Méthode : Femmes mariées au moins deux fois (requête nommée)
    public List<Femme> findMarriedAtLeastTwice() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createNamedQuery("Femme.findMarriedAtLeastTwice", Femme.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode : Nombre d’hommes mariés à quatre femmes entre deux dates (API Criteria)
    public long countHommesMariedToFourFemmesBetweenDates(Date startDate, Date endDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            var cb = session.getCriteriaBuilder();
            var cq = cb.createQuery(Long.class);
            var root = cq.from(Mariage.class);

            cq.select(cb.count(root.get("homme")))
                    .where(
                            cb.between(root.get("dateDebut"), startDate, endDate),
                            cb.equal(cb.size(root.get("homme").get("mariages")), 4)
                    );

            return session.createQuery(cq).uniqueResult();
        } finally {
            session.close();
        }
    }
}