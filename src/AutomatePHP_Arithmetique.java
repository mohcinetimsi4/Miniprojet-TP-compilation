public class AutomatePHP_Arithmetique {

    // Colonnes :
    // 0:- , 1:+ , 2:* , 3:% , 4:/ , 5:autre
    private int getCol(char c) {
        if (c == '-')
            return 0;
        if (c == '+')
            return 1;
        if (c == '*')
            return 2;
        if (c == '%')
            return 3;
        if (c == '/')
            return 4;
        return 5;
    }

    private final int[][] M = {
            // - + * % / Autre
            { 1, 1, 1, 1, 1, -1 }, // Q0
            { -1, -1, -1, -1, -1, -1 }, // Q1 (final)
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
        String input = s + "#"; // marqueur de fin
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
