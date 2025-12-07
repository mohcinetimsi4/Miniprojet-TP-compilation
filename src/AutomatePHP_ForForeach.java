public class AutomatePHP_ForForeach {

    // Colonnes :
    // 0:f , 1:o , 2:r , 3:e , 4:a , 5:c , 6:h , 7:autre
    private int getCol(char c) {
        if (c == 'f')
            return 0;
        if (c == 'o')
            return 1;
        if (c == 'r')
            return 2;
        if (c == 'e')
            return 3;
        if (c == 'a')
            return 4;
        if (c == 'c')
            return 5;
        if (c == 'h')
            return 6;
        return 7;
    }

    private final int[][] M = {
            // f o r e a c h autre
            { 1, -1, -1, -1, -1, -1, -1, -1 }, // Q0
            { -1, 2, -1, -1, -1, -1, -1, -1 }, // Q1
            { -1, -1, 3, -1, -1, -1, -1, -1 }, // Q2
            { -1, -1, -1, 4, -1, -1, -1, -1 }, // Q3 (for)
            { -1, -1, -1, -1, 5, -1, -1, -1 }, // Q4
            { -1, -1, -1, -1, -1, 6, -1, -1 }, // Q5
            { -1, -1, -1, -1, -1, -1, 7, -1 }, // Q6
            { -1, -1, -1, -1, -1, -1, -1, -1 }, // Q7 (foreach)
    };

    // États finaux
    private final int[] finals = { 3, 7 };

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
