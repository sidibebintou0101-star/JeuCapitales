package tech.synelia.dao;

import tech.synelia.Scores;
import tech.synelia.configuration.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ScoresDAO {

    public boolean enregistrerScore(Scores score) {

        String sql = """
                INSERT INTO scores
                (bonnes_reponses, mauvaises_reponses, date_partie, utilisateur_id)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection connexion = DatabaseConnection.getConnection();
                PreparedStatement statement = connexion.prepareStatement(sql)
        ) {
            statement.setInt(1, score.getBonnesReponses());
            statement.setInt(2, score.getMauvaisesReponses());
            statement.setTimestamp(3, Timestamp.valueOf(score.getDatePartie()));
            statement.setInt(4, score.getUtilisateurId());

            statement.executeUpdate();

            System.out.println("Score enregistré avec succès !");
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement du score : "
                    + e.getMessage());
            return false;
        }
    }
}
