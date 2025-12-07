public class AutomatePHP_Identifiant {

    // Colonnes :
    // 0:$ , 1:lettre , 2:_ , 3:chiffre , 4:autre
    private int getCol(char c) {
        if (c == '$')
            return 0;
        if (Character.isLetter(c))
            return 1;
        if (c == '_')
            return 2;
        if (Character.isDigit(c))
            return 3;
        return 4;
    }

    private final int[][] M = {
            // $ L _ N Autre
            { 1, -1, -1, -1, -1 }, // Q0
            { -1, 2, 2, -1, -1 }, // Q1
            { -1, 2, 2, 2, -1 }, // Q2
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
