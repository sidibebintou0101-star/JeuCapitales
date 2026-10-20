package tech.synelia;

import tech.synelia.dao.CapitalesDAO;
import tech.synelia.dao.ConfigurationsDAO;
import tech.synelia.dao.ScoresDAO;

import java.time.LocalDateTime;
import java.text.Normalizer;
import java.util.List;
import java.util.Scanner;

public class Jeu {

    private final CapitalesDAO capitalesDAO = new CapitalesDAO();
    private final ConfigurationsDAO configurationsDAO = new ConfigurationsDAO();
    private final ScoresDAO scoresDAO = new ScoresDAO();

    public void jouer(Scanner scanner, Utilisateurs utilisateur) {

        List<Question> questions = capitalesDAO.obtenirToutesLesQuestions();

        if (questions.isEmpty()) {
            System.out.println("Impossible de récupérer les questions.");
            return;
        }

        int nombreQuestions = configurationsDAO.obtenirNombreQuestions();

        nombreQuestions = Math.min(nombreQuestions, questions.size());

        int bonnesReponses = 0;
        int mauvaisesReponses = 0;

        System.out.println("\n=== DÉBUT DE LA PARTIE ===");

        for (int i = 0; i < nombreQuestions; i++) {

            Question question = questions.get(i);

            System.out.println("\nQuestion " + (i + 1) + "/" + nombreQuestions);
            System.out.print(
                    "Quelle est la capitale de "
                            + question.getNomPays() + " ? "
            );

            String reponse = scanner.nextLine().trim();

            if (reponse.equalsIgnoreCase(question.getNomCapitale())) {

                System.out.println("Bravooooooooooo!");
                bonnesReponses++;
            } else {
                System.out.println(
                        "Pufffffff, La bonne réponse était : "
                                + question.getNomCapitale()
                );
                mauvaisesReponses++;
            }
        }

        System.out.println("\n=== FIN DE LA PARTIE ===");


        System.out.println("Ton score est : " + bonnesReponses + "/" + nombreQuestions);

        System.out.println("Bonnes réponses : " + bonnesReponses);
        System.out.println("Mauvaises réponses : " + mauvaisesReponses);

        afficherFelicitations(bonnesReponses, nombreQuestions);

        Scores score = new Scores(
                bonnesReponses,
                mauvaisesReponses,
                LocalDateTime.now(),
                utilisateur.getId()
        );

        scoresDAO.enregistrerScore(score);
    }

    private void afficherFelicitations(int bonnesReponses, int total) {

        if (bonnesReponses == total) {
            System.out.println("Excellent ! Félicitations, continuez comme ça !");
        } else if (bonnesReponses >= 7) {
            System.out.println("Très bien ! vous maîtrisez bien les capitales !");
        } else if (bonnesReponses >= 4) {
            System.out.println("Pas mal ! Vous pouvez encore faire mieux !");
        } else {
            System.out.println("Courage ! Continuez à apprendre, vous allez progressez !");
        }
    }
}
