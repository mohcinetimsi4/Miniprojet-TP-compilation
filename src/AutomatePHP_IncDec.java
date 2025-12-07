public class AutomatePHP_IncDec {

    // Colonnes :
    // 0:- , 1:+ , 2:autre
    private int getCol(char c) {
        if (c == '-')
            return 0;
        if (c == '+')
            return 1;
        return 2;
    }

    private final int[][] M = {
            // - + Autre
            { 1, 2, -1 }, // Q0
            { 3, -1, -1 }, // Q1
            { -1, 4, -1 }, // Q2
            { -1, -1, -1 }, // Q3 (final)
            { -1, -1, -1 }, // Q4 (final)
    };

    // États finaux
    private final int[] finals = { 3, 4 };

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
