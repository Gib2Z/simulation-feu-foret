import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Foret {
    private final int hauteur;
    private final int largeur;
    private final Case[][] grille;

    public Foret(int hauteur, int largeur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.grille = new Case[hauteur][largeur];
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                grille[i][j] = new Case(i, j);
            }
        }
    }

    public Case getCase(int i, int j) {
        if( i >= 0 && i<hauteur && j>=0 && j< largeur ) {
            return grille[i][j];
        }
        return null;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }



    public List<Case> getVoisins(Case c) {
        List<Case> voisins = new ArrayList<>();
        int x=c.getX();
        int y=c.getY();

        int[] dx = {-1, 1, 0, 0}; //lignes
        int[] dy = {0, 0, -1, 1};//colonnes
        for (int k = 0; k < 4; k++) {
            Case c1 = getCase(x+dx[k], y+dy[k]);
            if (c1 != null) {
                voisins.add(c1);
            }
        }
        return voisins;
    }


    @Override
    public String toString() {
        return "Foret{" +
                "hauteur=" + hauteur +
                ", largeur=" + largeur +
                ", grille=" + Arrays.toString(grille) +
                '}';
    }

    public void afficherConsole() {
        // On parcourt chaque ligne de la grille, de haut en bas.
        for (int i = 0; i < hauteur; i++) {
            // Pour chaque ligne, on parcourt chaque colonne, de gauche à droite.
            for (int j = 0; j < largeur; j++) {
                // On récupère la case aux coordonnées actuelles.
                Case c = grille[i][j];

                // Selon l'état de la case, on choisit un caractère à afficher.
                switch (c.getEtat()) {
                    case INTACT:
                        // Un point vert pour un arbre intact.
                        System.out.print("\u001B[32m. \u001B[0m");
                        break;
                    case EN_FEU:
                        // Un @ rouge vif pour le feu.
                        System.out.print("\u001B[31m@ \u001B[0m");
                        break;
                    case CENDRE:
                        // Un # gris pour les cendres.
                        System.out.print("\u001B[90m# \u001B[0m");
                        break;
                }
            }
            System.out.println();
        }
    }

}
