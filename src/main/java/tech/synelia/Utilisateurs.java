package tech.synelia;

public class Utilisateurs{

    private int id;
    private String nom;
    private String prenom;
    private String email;

    public Utilisateurs(int id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Utilisateurs(String nom, String prenom, String email) {
        this(0, nom, prenom, email);
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }
}
