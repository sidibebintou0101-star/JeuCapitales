package tech.synelia;

import java.time.LocalDateTime;

public class Scores {

    private int id;
    private int bonnesReponses;
    private int mauvaisesReponses;
    private LocalDateTime datePartie;
    private int utilisateurId;

    public Scores(int id, int bonnesReponses, int mauvaisesReponses,
                  LocalDateTime datePartie, int utilisateurId) {
        this.id = id;
        this.bonnesReponses = bonnesReponses;
        this.mauvaisesReponses = mauvaisesReponses;
        this.datePartie = datePartie;
        this.utilisateurId = utilisateurId;
    }

    public Scores(int bonnesReponses, int mauvaisesReponses,
                  LocalDateTime datePartie, int utilisateurId) {
        this(0, bonnesReponses, mauvaisesReponses, datePartie, utilisateurId);
    }

    public int getId() {
        return id;
    }

    public int getBonnesReponses() {
        return bonnesReponses;
    }

    public int getMauvaisesReponses() {
        return mauvaisesReponses;
    }

    public LocalDateTime getDatePartie() {
        return datePartie;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }
}
