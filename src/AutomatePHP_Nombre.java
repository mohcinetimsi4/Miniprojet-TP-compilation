public class AutomatePHP_Nombre {

    // Colonnes :
    // 0: chiffre , 1: autre
    private int getCol(char c) {
        if (Character.isDigit(c))
            return 0;
        return 1;
    }

    private final int[][] M = {
            // N Autre
            { 1, -1 }, // Q0
            { 1, -1 }, // Q1
    };

    // États finaux
    private final int[] finals = { 1 };

    private boolean estFinal(int e) {
        for (int f : finals)
            if (e == f)
                return true;
        return false;
    }

    public boolean analyse(String s) {
        String input = s + "#";
        int etat = 0;
        int i = 0;

        char c = input.charAt(i);

        while (c != '#' && M[etat][getCol(c)] != -1) {
            etat = M[etat][getCol(c)];
            i++;
            c = input.charAt(i);
        }

        return (c == '#' && estFinal(etat));
    }
}
