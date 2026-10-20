package tech.synelia;
import tech.synelia.configuration.DatabaseConnection;
import tech.synelia.dao.UtilisateursDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // Vérifier la connexion à PostgreSQL
        try (Connection connexion =
                     DatabaseConnection.getConnection()) {
            System.out.println(
                    "Connexion à PostgreSQL réussie !"
            );
        } catch (SQLException e) {
            System.out.println(
                    "Erreur de connexion : " + e.getMessage()
            );
            return;
        }
        try (Scanner scanner = new Scanner(System.in)) {
            UtilisateursDAO utilisateursDAO =
                    new UtilisateursDAO();
            System.out.println("\n================================");
            System.out.println("       JEU DES CAPITALES");
            System.out.println("================================");


            while (true) {
                afficherMenuPrincipal();
                System.out.print("Ton choix : ");
                String choix = scanner.nextLine().trim();
                switch (choix) {
                    // ==================================
                    // SE CONNECTER
                    // ==================================
                    case "1": {
                        String email = demanderEmailValide(
                                scanner,
                                utilisateursDAO
                        );
                        Utilisateurs utilisateur =
                                utilisateursDAO.rechercherParEmail(email);
                        if (utilisateur != null) {
                            // L'e-mail existe :
                            // connexion directe.
                            afficherBienvenue(utilisateur);
                            lancerJeu(scanner, utilisateur);
                            return;
                        } else {
                            System.out.println(
                                    "\nCette adresse e-mail n'existe pas."
                            );
                            System.out.println(
                                    "Veuillez vous inscrire."
                            );
                            boolean continuer = true;
                            while (continuer) {
                                System.out.println("\n1. S'inscrire");
                                System.out.println("2. Quitter");
                                System.out.print("Ton choix : ");
                                String choixSuivant =
                                        scanner.nextLine().trim();
                                switch (choixSuivant) {
                                    case "1": {
                                        // Réutiliser l'e-mail saisi.
                                        Utilisateurs nouvelUtilisateur =
                                                inscrireUtilisateur(
                                                        scanner,
                                                        utilisateursDAO,
                                                        email
                                                );
                                        if (nouvelUtilisateur != null) {
                                            // Après l'inscription,
                                            // demander de se connecter
                                            // ou de quitter.
                                            if (confirmerConnexion(scanner)) {
                                                afficherBienvenue(
                                                        nouvelUtilisateur
                                                );
                                                lancerJeu(
                                                        scanner,
                                                        nouvelUtilisateur
                                                );
                                            }
                                            return;
                                        }
                                        continuer = false;
                                        break;
                                    }
                                    case "2":
                                        System.out.println("\nAu revoir !");
                                        return;
                                    default:
                                        System.out.println(
                                                "Choix invalide. "
                                                        + "Choisis 1 ou 2."
                                        );
                                }
                            }
                        }
                        break;
                    }
                    // ==================================
                    // S'INSCRIRE
                    // ==================================
                    case "2": {
                        Utilisateurs nouvelUtilisateur =
                                inscrireUtilisateur(
                                        scanner,
                                        utilisateursDAO,
                                        null
                                );
                        if (nouvelUtilisateur != null) {
                            // Après l'inscription seulement,
                            // proposer de se connecter ou quitter.
                            if (confirmerConnexion(scanner)) {
                                afficherBienvenue(nouvelUtilisateur);
                                lancerJeu(
                                        scanner,
                                        nouvelUtilisateur
                                );
                            }
                            return;
                        }
                        break;
                    }
                    // ==================================
                    // QUITTER
                    // ==================================
                    case "3":
                        System.out.println("\nAu revoir !");
                        return;
                    default:
                        System.out.println(
                                "Choix invalide. Choisis 1, 2 ou 3."
                        );
                }
            }
        }
    }
    // MENU PRINCIPAL
    private static void afficherMenuPrincipal() {
        System.out.println("\n1. Se connecter");
        System.out.println("2. S'inscrire");
        System.out.println("3. Quitter");
    }
    // DEMANDER UN E-MAIL VALIDE
    private static String demanderEmailValide(
            Scanner scanner,
            UtilisateursDAO utilisateursDAO
    ) {
        while (true) {
            System.out.print("Entrez votre adresse e-mail : ");
            String email = scanner.nextLine().trim();
            if (!utilisateursDAO.emailValide(email)) {
                System.out.println(
                        "Adresse e-mail invalide. "
                                + "Exemple : mariam@gmail.com"
                );
                continue;
            }
            return email;
        }
    }
    // INSCRIPTION
    private static Utilisateurs inscrireUtilisateur(
            Scanner scanner,
            UtilisateursDAO utilisateursDAO,
            String emailDejaSaisi
    ) {
        System.out.println("\n========== INSCRIPTION ==========");
        String email = emailDejaSaisi;
        // Demander l'e-mail seulement s'il n'a pas déjà été saisi.
        if (email == null || email.isEmpty()) {
            email = demanderEmailValide(
                    scanner,
                    utilisateursDAO
            );
        }
        while (true) {
            if (!utilisateursDAO.emailValide(email)) {
                System.out.println("Adresse e-mail invalide.");
                email = demanderEmailValide(
                        scanner,
                        utilisateursDAO
                );
                continue;
            }
            if (utilisateursDAO.emailExiste(email)) {
                System.out.println(
                        "\nCette adresse e-mail est déjà utilisée."
                );
                System.out.println(
                        "Veuillez saisir une nouvelle adresse e-mail."
                );
                email = demanderEmailValide(
                        scanner,
                        utilisateursDAO
                );
                continue;
            }
            break;
        }
        // Demander le nom et le prénom.
        System.out.print("Entrez votre nom : ");
        String nom = scanner.nextLine().trim();
        System.out.print("Entrez votre prénom : ");
        String prenom = scanner.nextLine().trim();
        // Enregistrer l'utilisateur.
        boolean inscriptionReussie =
                utilisateursDAO.inscrire(
                        nom,
                        prenom,
                        email
                );
        if (!inscriptionReussie) {
            System.out.println(
                    "Échec de l'inscription."
            );
            return null;
        }
        System.out.println("\nInscription réussie !");
        return utilisateursDAO.rechercherParEmail(email);
    }
    // MENU APRÈS INSCRIPTION
    private static boolean confirmerConnexion(Scanner scanner) {
        while (true) {
            System.out.println("\n1. Se connecter");
            System.out.println("2. Quitter");
            System.out.print("Ton choix : ");
            String choix = scanner.nextLine().trim();
            if (choix.equals("1")) {
                return true;
            }
            if (choix.equals("2")) {
                System.out.println("\nAu revoir !");
                return false;
            }
            System.out.println(
                    "Choix invalide. Choisis 1 ou 2."
            );
        }
    }
    // MESSAGE DE BIENVENUE
    private static void afficherBienvenue(
            Utilisateurs utilisateur
    ) {
        System.out.println("\nConnexion réussie !");
        System.out.println(
                "Bienvenue " + utilisateur.getPrenom() + " !"
        );
    }
    // LANCER LE JEU
    private static void lancerJeu(
            Scanner scanner,
            Utilisateurs utilisateur
    ) {
        System.out.println(
                "\nLe jeu des capitales commence !"
        );
        Jeu jeu = new Jeu();
        String rejouer;
        do {
            jeu.jouer(scanner, utilisateur);
            do {
                System.out.print(
                        "\nVeux-tu rejouer ? (oui/non) : "
                );
                rejouer = scanner.nextLine().trim();
                if (!rejouer.equalsIgnoreCase("oui")
                        && !rejouer.equalsIgnoreCase("non")) {
                    System.out.println(
                            "Réponse invalide. Écris oui ou non."
                    );
                }
            } while (!rejouer.equalsIgnoreCase("oui")
                    && !rejouer.equalsIgnoreCase("non"));
        } while (rejouer.equalsIgnoreCase("oui"));
        System.out.println(
                "\nMerci d'avoir joué au Jeu des capitales !"
        );
    }
}