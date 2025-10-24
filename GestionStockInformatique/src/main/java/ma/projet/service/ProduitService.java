package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit, Integer> {

    @Override
    public Produit save(Produit o) {
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
    public Produit update(Produit o) {
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
    public boolean delete(Produit o) {
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
    public Produit findById(Integer k) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Produit.class, k);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Produit> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Produit", Produit.class).list();
        } finally {
            session.close();
        }
    }

    // Méthode : Afficher produits par catégorie
    public List<Produit> findProduitsByCategorie(int categorieId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Produit p WHERE p.categorie.id = :catId";
            Query<Produit> query = session.createQuery(hql, Produit.class);
            query.setParameter("catId", categorieId);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode : Produits commandés entre deux dates
    public List<Produit> findProduitsCommandesBetweenDates(Date startDate, Date endDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = """
                SELECT DISTINCT p FROM Produit p
                JOIN p.lignesCommande lc
                JOIN lc.commande c
                WHERE c.date BETWEEN :start AND :end
                """;
            Query<Produit> query = session.createQuery(hql, Produit.class);
            query.setParameter("start", startDate);
            query.setParameter("end", endDate);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode : Produits dans une commande donnée
    public void afficherProduitsDansCommande(int commandeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = """
                SELECT lc.produit, lc.quantite FROM LigneCommandeProduit lc
                WHERE lc.commande.id = :cmdId
                """;
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("cmdId", commandeId);

            List<Object[]> results = query.list();
            if (results.isEmpty()) {
                System.out.println("Aucun produit trouvé pour cette commande.");
                return;
            }

            // Affichage
            System.out.println("Commande : " + commandeId + "     Date : " + getCommandeDate(session, commandeId));
            System.out.println("Liste des produits :");
            System.out.printf("%-10s %-10s %-10s%n", "Référence", "Prix", "Quantité");

            for (Object[] row : results) {
                Produit p = (Produit) row[0];
                int quantite = (int) row[1];
                System.out.printf("%-10s %-10.2f DH %-10d%n", p.getReference(), p.getPrix(), quantite);
            }
        } finally {
            session.close();
        }
    }

    private Date getCommandeDate(Session session, int commandeId) {
        String hql = "SELECT c.date FROM Commande c WHERE c.id = :id";
        Query<Date> query = session.createQuery(hql, Date.class);
        query.setParameter("id", commandeId);
        return query.uniqueResult();
    }

    // Méthode : Produits dont le prix > 100 DH (requête nommée)
    public List<Produit> findProduitsPrixSuperieurA100() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Produit> query = session.createNamedQuery("Produit.findByPriceAbove100", Produit.class);
            return query.list();
        } finally {
            session.close();
        }
    }
}