package tech.synelia;

public class Configuration {

    private int id;
    private String cle;
    private String valeur;

    public Configuration(int id, String cle, String valeur) {
        this.id = id;
        this.cle = cle;
        this.valeur = valeur;
    }

    public int getId() {
        return id;
    }

    public String getCle() {
        return cle;
    }

    public String getValeur() {
        return valeur;
    }
}
