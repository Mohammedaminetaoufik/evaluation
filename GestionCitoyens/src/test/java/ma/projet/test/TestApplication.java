package ma.projet.test;

import ma.projet.beans.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestApplication {
    public static void main(String[] args) {
        // Initialiser les services
        HommeService hommeService = new HommeService();
        FemmeService femmeService = new FemmeService();
        MariageService mariageService = new MariageService();

        // --- Créer 5 hommes ---
        Homme h1 = new Homme("SAFI", "SAID", "0612345678", "Rabat", new Date(110, 5, 10)); // 10 juin 2010
        Homme h2 = new Homme("ALI", "MOHAMED", "0687654321", "Casablanca", new Date(105, 3, 15)); // 15 avril 2005
        Homme h3 = new Homme("BEN", "AHMED", "0654321789", "Marrakech", new Date(100, 8, 20)); // 20 septembre 2000
        Homme h4 = new Homme("EL", "HASSAN", "0698765432", "Fès", new Date(95, 1, 5)); // 5 février 1995
        Homme h5 = new Homme("KARIM", "YOUSSEF", "0632178945", "Tanger", new Date(90, 11, 30)); // 30 décembre 1990

        h1 = hommeService.save(h1);
        h2 = hommeService.save(h2);
        h3 = hommeService.save(h3);
        h4 = hommeService.save(h4);
        h5 = hommeService.save(h5);

        // --- Créer 10 femmes ---
        Femme f1 = new Femme("SALIMA", "RAMI", "0611111111", "Rabat", new Date(110, 5, 12)); // 12 juin 2010
        Femme f2 = new Femme("AMAL", "ALI", "0622222222", "Casablanca", new Date(105, 3, 18)); // 18 avril 2005
        Femme f3 = new Femme("WAFA", "ALAOUI", "0633333333", "Marrakech", new Date(100, 8, 25)); // 25 septembre 2000
        Femme f4 = new Femme("KARIMA", "ALAMI", "0644444444", "Fès", new Date(95, 1, 8)); // 8 février 1995
        Femme f5 = new Femme("LAILA", "BEN", "0655555555", "Tanger", new Date(90, 11, 28)); // 28 décembre 1990
        Femme f6 = new Femme("ZINEB", "EL", "0666666666", "Rabat", new Date(85, 5, 10)); // 10 mai 1985
        Femme f7 = new Femme("NADIA", "KARIM", "0677777777", "Casablanca", new Date(80, 3, 15)); // 15 mars 1980
        Femme f8 = new Femme("SOUKAINA", "SAFI", "0688888888", "Marrakech", new Date(75, 8, 20)); // 20 août 1975
        Femme f9 = new Femme("HIBA", "ALI", "0699999999", "Fès", new Date(70, 1, 5)); // 5 janvier 1970
        Femme f10 = new Femme("AYA", "BEN", "0600000000", "Tanger", new Date(65, 11, 30)); // 30 novembre 1965

        f1 = femmeService.save(f1);
        f2 = femmeService.save(f2);
        f3 = femmeService.save(f3);
        f4 = femmeService.save(f4);
        f5 = femmeService.save(f5);
        f6 = femmeService.save(f6);
        f7 = femmeService.save(f7);
        f8 = femmeService.save(f8);
        f9 = femmeService.save(f9);
        f10 = femmeService.save(f10);

        // --- Créer des mariages ---
        Date start1 = new Date(110, 8, 3); // 3 septembre 2010
        Date end1 = null; // toujours en cours
        Mariage m1 = new Mariage(start1, end1, 4, h1, f1); // SAFI SAID - SALIMA RAMI

        Date start2 = new Date(115, 8, 3); // 3 septembre 2015
        Date end2 = null;
        Mariage m2 = new Mariage(start2, end2, 2, h1, f2); // SAFI SAID - AMAL ALI

        Date start3 = new Date(120, 10, 4); // 4 novembre 2020
        Date end3 = null;
        Mariage m3 = new Mariage(start3, end3, 3, h1, f3); // SAFI SAID - WAFA ALAOUI

        Date start4 = new Date(109, 8, 3); // 3 septembre 2009
        Date end4 = new Date(110, 8, 3); // 3 septembre 2010
        Mariage m4 = new Mariage(start4, end4, 0, h1, f4); // SAFI SAID - KARIMA ALAMI

        mariageService.save(m1);
        mariageService.save(m2);
        mariageService.save(m3);
        mariageService.save(m4);

        // --- TESTS ---
        System.out.println("\n=== TEST 1 : Liste des femmes ===");
        List<Femme> femmes = femmeService.findAll();
        femmes.forEach(System.out::println);

        System.out.println("\n=== TEST 2 : Femme la plus âgée ===");
        Femme plusAgee = femmes.stream()
                .min((f1_, f2_) -> f1_.getDateNaissance().compareTo(f2_.getDateNaissance()))
                .orElse(null);
        System.out.println("Femme la plus âgée : " + plusAgee);

        System.out.println("\n=== TEST 3 : Épouses de l'homme 1 entre 01/01/2010 et 01/01/2025 ===");
        Date start = new Date(110, 0, 1); // 1er janvier 2010
        Date end = new Date(125, 0, 1); // 1er janvier 2025
        List<Femme> epouses = hommeService.findEpousesByHommeAndDates(h1.getId(), start, end);
        epouses.forEach(System.out::println);

        System.out.println("\n=== TEST 4 : Nombre d'enfants de la femme 1 entre 01/01/2010 et 01/01/2025 ===");
        long nbEnfants = femmeService.countChildrenBetweenDates(f1.getId(), start, end);
        System.out.println("Nombre d'enfants : " + nbEnfants);

        System.out.println("\n=== TEST 5 : Femmes mariées au moins deux fois ===");
        List<Femme> femmesMultiMariees = femmeService.findMarriedAtLeastTwice();
        femmesMultiMariees.forEach(System.out::println);

        System.out.println("\n=== TEST 6 : Nombre d'hommes mariés à 4 femmes entre 01/01/2010 et 01/01/2025 ===");
        long nbHommes = femmeService.countHommesMariedToFourFemmesBetweenDates(start, end);
        System.out.println("Nombre d'hommes mariés à 4 femmes : " + nbHommes);

        System.out.println("\n=== TEST 7 : Mariages de l'homme 1 avec détails ===");
        hommeService.afficherMariagesDetails(h1.getId());

        // Fermer la session factory
        HibernateUtil.shutdown();
    }
}