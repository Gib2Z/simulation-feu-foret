import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        // --- 1. Lecture de la configuration ---
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            System.err.println("Erreur: Le fichier config.properties est introuvable.");
            return;
        }

        int hauteur = Integer.parseInt(prop.getProperty("foret.hauteur"));
        int largeur = Integer.parseInt(prop.getProperty("foret.largeur"));
        double proba = Double.parseDouble(prop.getProperty("simulation.probabilite"));
        String feuxInitiauxStr = prop.getProperty("feux.initiaux");

        // --- 2. Initialisation ---
        Foret foret = new Foret(hauteur, largeur);


        //on verifie que la ligne des feux initiaux n'est pas vide.
        if (feuxInitiauxStr != null && !feuxInitiauxStr.isEmpty()) {
            // On separe la chaine en plusieurs couples "x,y" en utilisant le ";" comme separateur.
            String[] couplesDeCoordonnees = feuxInitiauxStr.split(";");

            // On boucle sur chaque couple "x,y" obtenu.
            for (String couple : couplesDeCoordonnees) {
                // On separe le couple "x,y" en deux parties en utilisant la "," comme separateur.
                String[] coords = couple.split(",");

                // On s'attend à avoir exactement deux parties (x et y).
                if (coords.length == 2) {
                    try {
                        // On convertit les chaines de caracteres en nombres entiers.
                        int x = Integer.parseInt(coords[0]);
                        int y = Integer.parseInt(coords[1]);

                        // On recupere la case correspondante dans la foret.
                        Case caseAAllumer = foret.getCase(x, y);

                        // Si la case existe bien (coordonnées valides), on l'allume.
                        if (caseAAllumer != null) {
                            caseAAllumer.setEtat(Etat.EN_FEU);
                        } else {
                            System.err.println("Attention: Coordonnée initiale invalide (hors grille) : " + x + "," + y);
                        }
                    } catch (NumberFormatException e) {
                        // Cette erreur se produit si les coordonnées ne sont pas des nombres.
                        System.err.println("Attention: Coordonnée initiale mal formée : " + couple);
                    }
                }
            }
        }

        // --- 3. Lancement de la simulation ---
        Simulation simulation = new Simulation(foret, proba);
        simulation.lancer();
    }
}