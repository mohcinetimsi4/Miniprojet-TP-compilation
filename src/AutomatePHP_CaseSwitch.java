public class AutomatePHP_CaseSwitch {

    // Colonnes :
    // 0:c , 1:a , 2:s , 3:e , 4:w , 5:i , 6:t , 7:h , 8:autre
    private int getCol(char c) {
        if (c == 'c')
            return 0;
        if (c == 'a')
            return 1;
        if (c == 's')
            return 2;
        if (c == 'e')
            return 3;
        if (c == 'w')
            return 4;
        if (c == 'i')
            return 5;
        if (c == 't')
            return 6;
        if (c == 'h')
            return 7;
        return 8;
    }

    private final int[][] M = {
            // c a s e w i t h autre
            { 2, -1, 1, -1, -1, -1, -1, -1, -1 }, // Q0
            { -1, -1, -1, -1, 3, -1, -1, -1, -1 }, // Q1
            { -1, 8, -1, -1, -1, -1, -1, -1, -1 }, // Q2
            { -1, -1, -1, -1, -1, 4, -1, -1, -1 }, // Q3
            { -1, -1, -1, -1, -1, -1, 5, -1, -1 }, // Q4
            { -1, -1, -1, -1, -1, -1, -1, 6, -1 }, // Q5
            { -1, -1, -1, -1, -1, -1, -1, 7, -1 }, // Q6
            { -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // Q7 (final "switch")
            { -1, -1, 9, -1, -1, -1, -1, -1, -1 }, // Q8
            { -1, -1, -1, 10, -1, -1, -1, -1, -1 }, // Q9
            { -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // Q10 (final "case")
    };

    // États finaux
    private final int[] finals = { 7, 10 };

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
