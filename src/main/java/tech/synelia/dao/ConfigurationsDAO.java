package tech.synelia.dao;

import tech.synelia.configuration.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigurationsDAO {

    public String obtenirValeur(String cle) {

        String sql = "SELECT valeur FROM configurations WHERE cle = ?";

        try (
                Connection connexion = DatabaseConnection.getConnection();
                PreparedStatement statement = connexion.prepareStatement(sql)
        ) {
            statement.setString(1, cle);

            try (ResultSet resultat = statement.executeQuery()) {

                if (resultat.next()) {
                    return resultat.getString("valeur");
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Erreur lors de la lecture de la configuration : "
                            + e.getMessage()
            );
        }

        return null;
    }

    public int obtenirNombreQuestions() {

        String valeur = obtenirValeur("nombre_questions");

        if (valeur == null) {
            return 10;
        }

        try {
            return Integer.parseInt(valeur);
        } catch (NumberFormatException e) {
            System.out.println(
                    "Nombre de questions invalide. Valeur par défaut : 10."
            );
            return 10;
        }
    }
}
