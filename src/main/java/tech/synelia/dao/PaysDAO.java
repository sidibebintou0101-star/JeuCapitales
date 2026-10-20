package tech.synelia.dao;

import tech.synelia.configuration.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaysDAO {

    public void afficherPays() {

        String sql = "SELECT id, nom, superficie, nombreshabitants FROM pays ORDER BY id";

        try (
                Connection connexion = DatabaseConnection.getConnection();
                PreparedStatement statement = connexion.prepareStatement(sql);
                ResultSet resultat = statement.executeQuery()
        ) {

            while (resultat.next()) {

                int id = resultat.getInt("id");
                String nom = resultat.getString("nom");
                double superficie = resultat.getDouble("superficie");
                long nombresHabitants = resultat.getLong("nombresHabitants");

                System.out.println("ID : " + id);
                System.out.println("Pays : " + nom);
                System.out.println("Superficie : " + superficie + " km²");
                System.out.println("NombresHabitants : " + nombresHabitants);
                System.out.println("-----------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des pays : " + e.getMessage());
        }
    }
}
