package tech.synelia;

public class Capitales {

    private int id;
    private String nomCapitale;
    private int paysId;
    private String typeCapitale;

    public Capitales(int id, String nomCapitale, int paysId, String typeCapitale) {
        this.id = id;
        this.nomCapitale = nomCapitale;
        this.paysId = paysId;
        this.typeCapitale = typeCapitale;
    }

    public int getId() {
        return id;
    }

    public String getNomCapitale() {
        return nomCapitale;
    }

    public int getPaysId() {
        return paysId;
    }

    public String getTypeCapitale() {
        return typeCapitale;
    }
}
