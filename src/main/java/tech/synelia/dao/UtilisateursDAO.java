package tech.synelia.dao;

import tech.synelia.Utilisateurs;
import tech.synelia.configuration.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class UtilisateursDAO {

    // Normaliser l'adresse e-mail
    private String normaliserEmail(String email) {
        if (email == null) {
            return "";
        }

        return email.trim().toLowerCase(Locale.ROOT);
    }

    // Vérifier le format de l'adresse e-mail
    public boolean emailValide(String email) {

        String emailNormalise = normaliserEmail(email);

        if (emailNormalise.isEmpty()) {
            return false;
        }

        String regex =
                "^[A-Za-z0-9]+([._%+-]?[A-Za-z0-9]+)*"
                        + "@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$";

        if (!emailNormalise.matches(regex)) {
            return false;
        }

        String domaine = emailNormalise.substring(
                emailNormalise.lastIndexOf('@') + 1
        );

        if (domaine.contains("..")) {
            return false;
        }

        String[] partiesDomaine = domaine.split("\\.");

        for (String partie : partiesDomaine) {
            if (partie.isEmpty()
                    || partie.startsWith("-")
                    || partie.endsWith("-")) {
                return false;
            }
        }

        return true;
    }

    // Vérifier si l'e-mail existe déjà
    public boolean emailExiste(String email) {

        String emailNormalise = normaliserEmail(email);

        if (!emailValide(emailNormalise)) {
            return false;
        }

        String sql = """
                SELECT id
                FROM utilisateurs
                WHERE LOWER(email) = ?
                """;

        try (
                Connection connexion =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connexion.prepareStatement(sql)
        ) {

            statement.setString(1, emailNormalise);

            try (ResultSet resultat = statement.executeQuery()) {
                return resultat.next();
            }

        } catch (SQLException e) {

            System.out.println(
                    "Erreur lors de la vérification de l'e-mail : "
                            + e.getMessage()
            );

            return false;
        }
    }

    // Inscrire un utilisateur
    public boolean inscrire(String nom, String prenom, String email) {

        String nomNormalise =
                nom == null ? "" : nom.trim();

        String prenomNormalise =
                prenom == null ? "" : prenom.trim();

        String emailNormalise = normaliserEmail(email);

        if (nomNormalise.isEmpty()
                || prenomNormalise.isEmpty()
                || emailNormalise.isEmpty()) {

            System.out.println(
                    "Le nom, le prénom et l'e-mail sont obligatoires."
            );

            return false;
        }

        if (!emailValide(emailNormalise)) {

            System.out.println("Adresse e-mail invalide.");

            return false;
        }

        String sql = """
                INSERT INTO utilisateurs (nom, prenom, email)
                VALUES (?, ?, ?)
                ON CONFLICT DO NOTHING
                """;

        try (
                Connection connexion =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connexion.prepareStatement(sql)
        ) {

            statement.setString(1, nomNormalise);
            statement.setString(2, prenomNormalise);
            statement.setString(3, emailNormalise);

            int lignesInserees = statement.executeUpdate();

            if (lignesInserees == 0) {
                System.out.println(
                        "Cette adresse e-mail est déjà utilisée. "
                                + "Veuillez en choisir une autre."
                );

                return false;
            }

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Erreur lors de l'inscription : "
                            + e.getMessage()
            );

            return false;
        }
    }

    // Rechercher un utilisateur par son e-mail
    public Utilisateurs rechercherParEmail(String email) {

        String emailNormalise = normaliserEmail(email);

        if (!emailValide(emailNormalise)) {
            return null;
        }

        String sql = """
                SELECT id, nom, prenom, email
                FROM utilisateurs
                WHERE LOWER(email) = ?
                """;

        try (
                Connection connexion =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connexion.prepareStatement(sql)
        ) {

            statement.setString(1, emailNormalise);

            try (ResultSet resultat = statement.executeQuery()) {

                if (resultat.next()) {
                    return new Utilisateurs(
                            resultat.getInt("id"),
                            resultat.getString("nom"),
                            resultat.getString("prenom"),
                            resultat.getString("email")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Erreur lors de la recherche de l'utilisateur : "
                            + e.getMessage()
            );
        }

        return null;
    }
}
