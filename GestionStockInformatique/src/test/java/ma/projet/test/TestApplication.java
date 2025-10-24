package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestApplication {
    public static void main(String[] args) {
        // Initialiser les services
        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneService = new LigneCommandeService();

        // --- Vérifier si catégories existent déjà ---
        List<Categorie> categories = categorieService.findAll();
        Categorie pc = null;
        Categorie periph = null;

        for (Categorie c : categories) {
            if ("PC".equals(c.getCode())) {
                pc = c;
            } else if ("PERIPH".equals(c.getCode())) {
                periph = c;
            }
        }

        // Si elles n'existent pas, on les crée
        if (pc == null) {
            pc = categorieService.save(new Categorie("PC", "Ordinateurs"));
        }
        if (periph == null) {
            periph = categorieService.save(new Categorie("PERIPH", "Périphériques"));
        }

        System.out.println("Catégories sauvegardées ou récupérées : " + pc + ", " + periph);

        // Créer et sauvegarder les produits
        Produit p1 = new Produit("ES12", 120.0f, pc);
        Produit p2 = new Produit("ZR85", 100.0f, periph);
        Produit p3 = new Produit("EE85", 200.0f, pc);

        p1 = produitService.save(p1);
        p2 = produitService.save(p2);
        p3 = produitService.save(p3);

        System.out.println("Produits sauvegardés : " + p1 + ", " + p2 + ", " + p3);

        // Créer et sauvegarder une commande
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCmd = new Date();
        Commande cmd = new Commande(dateCmd);
        cmd = commandeService.save(cmd);

        System.out.println("Commande sauvegardée : " + cmd);

        // Créer et sauvegarder les lignes de commande
        LigneCommandeProduit l1 = new LigneCommandeProduit(cmd, p1, 7);
        LigneCommandeProduit l2 = new LigneCommandeProduit(cmd, p2, 14);
        LigneCommandeProduit l3 = new LigneCommandeProduit(cmd, p3, 5);

        ligneService.save(l1);
        ligneService.save(l2);
        ligneService.save(l3);

        System.out.println("Lignes de commande sauvegardées.");

        // --- TESTS ---
        System.out.println("\n=== TEST 1 : Afficher produits par catégorie ===");
        List<Produit> produitsPC = produitService.findProduitsByCategorie(pc.getId());
        produitsPC.forEach(System.out::println);

        System.out.println("\n=== TEST 2 : Afficher produits commandés entre deux dates ===");
        Date start = new Date(113, 0, 1); // 1er janvier 2013
        Date end = new Date(113, 11, 31); // 31 décembre 2013
        List<Produit> produitsEntreDates = produitService.findProduitsCommandesBetweenDates(start, end);
        produitsEntreDates.forEach(System.out::println);

        System.out.println("\n=== TEST 3 : Afficher produits dans commande ===");
        produitService.afficherProduitsDansCommande(cmd.getId());

        System.out.println("\n=== TEST 4 : Produits avec prix > 100 DH ===");
        List<Produit> produitsChers = produitService.findProduitsPrixSuperieurA100();
        produitsChers.forEach(System.out::println);

        // Fermer la session factory
        HibernateUtil.shutdown();
    }
}