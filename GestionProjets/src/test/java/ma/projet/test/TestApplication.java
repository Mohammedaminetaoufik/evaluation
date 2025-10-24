package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestApplication {
    public static void main(String[] args) {
        // Initialiser les services
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService etService = new EmployeTacheService();

        // --- Créer des employés ---
        Employe emp1 = new Employe("Dupont", "Jean", "0612345678");
        Employe emp2 = new Employe("Martin", "Sophie", "0687654321");
        emp1 = employeService.save(emp1);
        emp2 = employeService.save(emp2);

        // --- Créer un projet ---
        Date dateDebut = new Date(113, 0, 14); // 14 janvier 2013
        Date dateFin = new Date(113, 3, 30);   // 30 avril 2013
        Projet projet = new Projet("Gestion de stock", dateDebut, dateFin, emp1);
        projet = projetService.save(projet);

        // --- Créer des tâches ---
        Tache t1 = new Tache("Analyse", new Date(113, 1, 10), new Date(113, 1, 20), 800.0, projet);
        Tache t2 = new Tache("Conception", new Date(113, 2, 10), new Date(113, 2, 15), 1200.0, projet);
        Tache t3 = new Tache("Développement", new Date(113, 3, 10), new Date(113, 3, 25), 1500.0, projet);
        t1 = tacheService.save(t1);
        t2 = tacheService.save(t2);
        t3 = tacheService.save(t3);

        // --- Affecter les tâches aux employés avec dates réelles ---
        EmployeTache et1 = new EmployeTache(emp1, t1, new Date(113, 1, 10), new Date(113, 1, 20));
        EmployeTache et2 = new EmployeTache(emp2, t2, new Date(113, 2, 10), new Date(113, 2, 15));
        EmployeTache et3 = new EmployeTache(emp1, t3, new Date(113, 3, 10), new Date(113, 3, 25));

        etService.save(et1);
        etService.save(et2);
        etService.save(et3);

        // --- TESTS ---
        System.out.println("\n=== TEST 1 : Tâches réalisées par l'employé 1 ===");
        List<Tache> tachesEmp1 = employeService.findTachesRealiseesByEmploye(emp1.getId());
        tachesEmp1.forEach(System.out::println);

        System.out.println("\n=== TEST 2 : Projets gérés par l'employé 1 ===");
        List<Projet> projetsEmp1 = employeService.findProjetsGeresByEmploye(emp1.getId());
        projetsEmp1.forEach(System.out::println);

        System.out.println("\n=== TEST 3 : Tâches planifiées pour le projet 1 ===");
        List<Tache> tachesPlanifiees = projetService.findTachesPlanifieesByProjet(projet.getId());
        tachesPlanifiees.forEach(System.out::println);

        System.out.println("\n=== TEST 4 : Tâches réalisées avec dates réelles pour le projet 1 ===");
        projetService.afficherTachesRealiseesByProjet(projet.getId());

        System.out.println("\n=== TEST 5 : Tâches avec prix > 1000 DH ===");
        List<Tache> tachesCheres = tacheService.findTachesPrixSuperieurA1000();
        tachesCheres.forEach(System.out::println);

        System.out.println("\n=== TEST 6 : Tâches réalisées entre 01/02/2013 et 30/03/2013 ===");
        Date start = new Date(113, 1, 1);  // 1 février 2013
        Date end = new Date(113, 2, 30);   // 30 mars 2013
        List<Tache> tachesEntreDates = tacheService.findTachesRealiseesEntreDates(start, end);
        tachesEntreDates.forEach(System.out::println);

        // Fermer la session factory
        HibernateUtil.shutdown();
    }
}