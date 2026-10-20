package tech.synelia;

public class Question {

    private int paysId;
    private String nomPays;
    private String nomCapitale;

    public Question(int paysId, String nomPays, String nomCapitale) {
        this.paysId = paysId;
        this.nomPays = nomPays;
        this.nomCapitale = nomCapitale;
    }

    public int getPaysId() {
        return paysId;
    }

    public String getNomPays() {
        return nomPays;
    }

    public String getNomCapitale() {
        return nomCapitale;
    }
}
