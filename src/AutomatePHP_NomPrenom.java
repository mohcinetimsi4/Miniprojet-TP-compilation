public class AutomatePHP_NomPrenom {

    // Colonnes : t,i,m,o,s,h,c,n,e,autre
    private int getCol(char c) {
        if (c == 't')
            return 0;
        if (c == 'i')
            return 1;
        if (c == 'm')
            return 2;
        if (c == 'o')
            return 3;
        if (c == 's')
            return 4;
        if (c == 'h')
            return 5;
        if (c == 'c')
            return 6;
        if (c == 'n')
            return 7;
        if (c == 'e')
            return 8;
        return 9;
    }

    private final int[][] M = {
            // t i m o s h c n e autre
            { 1, -1, -6, -1, -1, -1, -1, -1, -1, -1 }, // 0 = Q0

            // --- "timsi" ---
            { -1, 2, -1, -1, -1, -1, -1, -1, -1, -1 }, // 1 = Q1 (t)
            { -1, -1, 3, -1, -1, -1, -1, -1, -1, -1 }, // 2 = Q2 (ti)
            { -1, -1, -1, -1, 4, -1, -1, -1, -1, -1 }, // 3 = Q3 (tim)
            { -1, 5, -1, -1, -1, -1, -1, -1, -1, -1 }, // 4 = Q4 (tims)
            { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 5 = Q5 (timsi)

            // --- "mohcine" ---
            { -1, -1, -1, 7, -1, -1, -1, -1, -1, -1 }, // 6 = S1 (m)
            { -1, -1, -1, -1, -1, 8, -1, -1, -1, -1 }, // 7 = S2 (mo)
            { -1, -1, -1, -1, -1, -1, 9, -1, -1, -1 }, // 8 = S3 (moh)
            { -1, 10, -1, -1, -1, -1, -1, -1, -1, -1 }, // 9 = S4 (mohc)
            { -1, -1, -1, -1, -1, -1, -1, 11, -1, -1 }, // 10 = S5 (mohci)
            { -1, -1, -1, -1, -1, -1, -1, -1, 12, -1 }, // 11 = S6 (mohcin)
            { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 12 = S7 FINAL (mohcine)
    };

    // États finaux
    private final int[] finals = { 5, 12 };

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

        while (c != '#' && etat >= 0 && getCol(c) <= 9 && M[etat][getCol(c)] != -1) {
            etat = M[etat][getCol(c)];
            i++;
            c = input.charAt(i);
        }

        return (c == '#' && estFinal(etat));
    }
}
