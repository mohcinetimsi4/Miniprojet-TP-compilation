public class AutomatePHP_If {

    // Colonnes : i, f, autre
    private int getCol(char c) {
        if (c == 'i')
            return 0;
        if (c == 'f')
            return 1;
        return 2;
    }

    private final int[][] M = {
            // i f autre
            { 1, -1, -1 }, // Q0
            { -1, 2, -1 }, // Q1
            { -1, -1, -1 } // Q2 (final)
    };

    // États finaux
    private final int[] finals = { 2 };

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
