public class AutomatePHP_Echo {

    // Colonnes :
    // 0:e , 1:c , 2:h , 3:o , 4:autre
    private int getCol(char c) {
        if (c == 'e')
            return 0;
        if (c == 'c')
            return 1;
        if (c == 'h')
            return 2;
        if (c == 'o')
            return 3;
        return 4;
    }

    private final int[][] M = {
            // e c h o autre
            { 1, -1, -1, -1, -1 }, // Q0
            { -1, 2, -1, -1, -1 }, // Q1
            { -1, -1, 3, -1, -1 }, // Q2
            { -1, -1, -1, 4, -1 }, // Q3
            { -1, -1, -1, -1, -1 }, // Q4 (final)
    };

    private final int[] finals = { 4 };

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
