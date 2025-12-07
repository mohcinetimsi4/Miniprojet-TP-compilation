public class AutomatePHP_DoWhile {

    // Colonnes :
    // 0:d , 1:o , 2:w , 3:h , 4:i , 5:l , 6:e , 7:autre
    private int getCol(char c) {
        if (c == 'd')
            return 0;
        if (c == 'o')
            return 1;
        if (c == 'w')
            return 2;
        if (c == 'h')
            return 3;
        if (c == 'i')
            return 4;
        if (c == 'l')
            return 5;
        if (c == 'e')
            return 6;
        return 7;
    }

    private final int[][] M = {
            // d o w h i l e autre
            { 1, -1, 3, -1, -1, -1, -1, -1 }, // Q0
            { -1, 2, -1, -1, -1, -1, -1, -1 }, // Q1
            { -1, -1, -1, -1, -1, -1, -1, -1 }, // Q2 (final "do")
            { -1, -1, -1, 4, -1, -1, -1, -1 }, // Q3
            { -1, -1, -1, -1, 5, -1, -1, -1 }, // Q4
            { -1, -1, -1, -1, -1, 6, -1, -1 }, // Q5
            { -1, -1, -1, -1, -1, -1, 7, -1 }, // Q6
            { -1, -1, -1, -1, -1, -1, -1, -1 }, // Q7 (final "while")
    };

    // États finaux
    private final int[] finals = { 2, 7 };

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
