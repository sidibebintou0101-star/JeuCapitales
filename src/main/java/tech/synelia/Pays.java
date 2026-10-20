package tech.synelia;

public class Pays {

    private int id;
    private String nom;
    private double superficie;
    private long nombresHabitants;

    public Pays(int id, String nom, double superficie, long nombresHabitants) {
        this.id = id;
        this.nom = nom;
        this.superficie = superficie;
        this.nombresHabitants = nombresHabitants;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getSuperficie() {
        return superficie;
    }

    public long getNombresHabitants() {
        return nombresHabitants;
    }
}
