public class AutomatePHP_Else {

    private int getCol(char c) {
        if (c == 'e') return 0;
        if (c == 'l') return 1;
        if (c == 's') return 2;
        if (c == 'e') return 3;
        return 4;
    }

    private final int[][] M = {
        // e   l   s   e  autre
        {  1, -1, -1, -1, -1 }, // Q0
        { -1,  2, -1, -1, -1 }, // Q1  (e)
        { -1, -1,  3, -1, -1 }, // Q2  (el)
        {  4, -1, -1, -1, -1 }, // Q3  (els)
        { -1, -1, -1, -1, -1 }, // Q4 final (else)
    };

    private final int[] finals = {4};

    private boolean estFinal(int e) {
        for (int f : finals) if (f == e) return true;
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
