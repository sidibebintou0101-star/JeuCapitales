package tech.synelia.dao;

import tech.synelia.Capitales;
import tech.synelia.Question;
import tech.synelia.configuration.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CapitalesDAO {

    // Récupérer toutes les capitales depuis PostgreSQL
    public List<Capitales> obtenirToutesLesCapitales() {

        List<Capitales> liste = new ArrayList<>();

        String sql = """
                SELECT id, nom_capitale, pays_id, type_capitale
                FROM capitales
                ORDER BY id
                """;

        try (
                Connection connexion = DatabaseConnection.getConnection();
                PreparedStatement statement = connexion.prepareStatement(sql);
                ResultSet resultat = statement.executeQuery()
        ) {

            while (resultat.next()) {

                Capitales capitale = new Capitales(
                        resultat.getInt("id"),
                        resultat.getString("nom_capitale"),
                        resultat.getInt("pays_id"),
                        resultat.getString("type_capitale")
                );

                liste.add(capitale);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Erreur lors de la récupération des capitales : "
                            + e.getMessage()
            );
        }

        return liste;
    }

    // Afficher les capitales
    public void afficherCapitales() {

        List<Capitales> liste = obtenirToutesLesCapitales();

        for (Capitales capitale : liste) {
            System.out.println("ID : " + capitale.getId());
            System.out.println("Capitale : " + capitale.getNomCapitale());
            System.out.println("ID du pays : " + capitale.getPaysId());
            System.out.println("Type : " + capitale.getTypeCapitale());
            System.out.println("-----------------------------");
        }
    }

    // Récupérer les pays associés à leurs capitales
    public void afficherPaysEtCapitales() {

        String sql = """
                SELECT p.nom AS nom_pays,
                       c.nom_capitale AS nom_capitale
                FROM pays p
                INNER JOIN capitales c ON p.id = c.pays_id
                ORDER BY p.id
                """;

        try (
                Connection connexion = DatabaseConnection.getConnection();
                PreparedStatement statement = connexion.prepareStatement(sql);
                ResultSet resultat = statement.executeQuery()
        ) {

            while (resultat.next()) {

                System.out.println(
                        "Pays : " + resultat.getString("nom_pays")
                );

                System.out.println(
                        "Capitale : " + resultat.getString("nom_capitale")
                );

                System.out.println("-----------------------------");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Erreur lors de la récupération des pays et capitales : "
                            + e.getMessage()
            );
        }
    }

    // Récupérer toutes les questions depuis PostgreSQL
    public List<Question> obtenirToutesLesQuestions() {

        List<Question> questions = new ArrayList<>();

        String sql = """
                SELECT p.id AS pays_id,
                       p.nom AS nom_pays,
                       c.nom_capitale AS nom_capitale
                FROM pays p
                INNER JOIN capitales c ON p.id = c.pays_id
                ORDER BY p.id
                """;

        try (
                Connection connexion = DatabaseConnection.getConnection();
                PreparedStatement statement = connexion.prepareStatement(sql);
                ResultSet resultat = statement.executeQuery()
        ) {

            while (resultat.next()) {

                Question question = new Question(
                        resultat.getInt("pays_id"),
                        resultat.getString("nom_pays"),
                        resultat.getString("nom_capitale")
                );

                questions.add(question);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Erreur lors de la récupération des questions : "
                            + e.getMessage()
            );
        }

        // Mélanger les questions
        Collections.shuffle(questions);

        return questions;
    }
}