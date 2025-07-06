public class Case {
    private final int x;
    private final int y;
    private Etat etat;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.etat = Etat.INTACT;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Case [x=" + x + ", y=" + y + ", etat=" + etat + "]";
    }

}
