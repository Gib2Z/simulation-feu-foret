import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Simulation {
    private Foret foret;
    private double propagation;
    private int etape;

    public Simulation(Foret foret, double propagation) {
        this.foret = foret;
        this.propagation = propagation;
        this.etape = 0;
    }

    /**
     * Lance la simulation et la fait tourner jusqu'à ce qu'il n'y ait plus de feu.
     */
    public void lancer() {
        // La condition de la boucle est maintenant "tant qu'il y a un feu",
        while (yaUnFeu()) {
            // 1. Affiche la forêt et le numéro de l'étape.
            System.out.println("\n--- Étape " + etape + " ---");
            foret.afficherConsole(); // On utilise la méthode d'affichage, pas toString()

            // 2. Calcule l'état suivant de la simulation.
            prochaineEtape();

            // 3. Incrémente le compteur d'étapes.
            etape++;

            // 4. Met une petite pause pour mieux voir l'évolution.
            try {
                // Pause de 500 millisecondes (0.5 secondes)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // En cas d'erreur avec la pause, on arrête le thread courant.
                Thread.currentThread().interrupt();
            }
        }

        // Ce message ne s'affiche qu'une fois la boucle terminée.
        System.out.println("\n--- Simulation terminée à l'étape " + etape + " ---");
        System.out.println("État final de la forêt :");
        foret.afficherConsole();
    }

    /**
     * Calcule l'etat de la foret à l'étape t+1 à partir de l'état t.
     */
    public void prochaineEtape() {
        List<Case> feuxActuels = new ArrayList<>();
        Set<Case> prochainsFeux = new HashSet<>();

        // ÉTAPE 1: Identifier les feux actuels et les futurs feux

        // On parcourt toute la forêt pour trouver les cases en feu.
        for (int i = 0; i < foret.getHauteur(); i++) {
            for (int j = 0; j < foret.getLargeur(); j++) {
                Case c = foret.getCase(i, j);
                if (c.getEtat() == Etat.EN_FEU) {
                    feuxActuels.add(c);
                }
            }
        }

        // Pour chaque feu actuel, on regarde s'il propage le feu à ses voisins.
        for (Case feu : feuxActuels) {
            List<Case> voisins = foret.getVoisins(feu);
            for (Case voisin : voisins) {
                // Le feu ne se propage qu'aux arbres encore intacts.
                if (voisin.getEtat() == Etat.INTACT) {
                    // Test de probabilite
                    if (Math.random() < this.propagation) {
                        // Si le test réussit, le voisin prendra feu à la prochaine étape.
                        prochainsFeux.add(voisin);
                    }
                }
            }
        }

        // ÉTAPE 2: Appliquer les changements d'état (la mise à jour effective)

        // Les arbres qui étaient en feu deviennent des cendres.
        for (Case feu : feuxActuels) {
            feu.setEtat(Etat.CENDRE);
        }

        // Les arbres qui ont été touches prennent feu.
        for (Case nouveauFeu : prochainsFeux) {
            nouveauFeu.setEtat(Etat.EN_FEU);
        }
    }

    /**
     * Vérifie s'il reste au moins une case en feu dans la foret.
     * @return true s'il y a un feu, false sinon.
     */
    private boolean yaUnFeu() {
        // On parcourt toute la grille.
        for (int i = 0; i < foret.getHauteur(); i++) {
            for (int j = 0; j < foret.getLargeur(); j++) {
                // Dès qu'on trouve une seule case en feu, on peut s'arrêter et retourner true.
                if (foret.getCase(i, j).getEtat() == Etat.EN_FEU) {
                    return true;
                }
            }
        }
        // Si on a parcouru toute la foret sans trouver de feu, on retourne false.
        return false;
    }
}