public class AutomatePHP_Comparaison {

    // Colonnes :
    // 0:'<' , 1:'>' , 2:'=' , 3:'!' , 4:'&' , 5:'|' , 6:autre
    private int getCol(char c) {
        return switch (c) {
            case '<' -> 0;
            case '>' -> 1;
            case '=' -> 2;
            case '!' -> 3;
            case '&' -> 4;
            case '|' -> 5;
            default -> 6;
        };
    }

    // États finaux
    private String getFinalOperator(int s) {
        return switch (s) {
            case 1 -> "<";
            case 2 -> ">";
            case 3 -> "=";
            case 5 -> "<=";
            case 6 -> ">=";
            case 7 -> "==";
            case 8 -> "!=";
            case 9 -> "===";
            case 10 -> "<>";
            case 11 -> "!==";
            case 14 -> "&&";
            case 15 -> "||";
            default -> null;
        };
    }

    public String analyse(String s) {

        int[][] M = {
            //   <    >    =    !    &    |   autre
            {   1,   2,   3,   4,  12,  13,  -1 }, // 0 : début

            {  -1,  10,   5,  -1,  -1,  -1,  -2 }, // 1 : '<'
            {  -1,  -1,   6,  -1,  -1,  -1,  -2 }, // 2 : '>'
            {  -2,  -1,   7,  -1,  -1,  -1,  -2 }, // 3 : '='
            {  -1,  -1,   8,  -1,  -1,  -1,  -2 }, // 4 : '!'

            {  -2,  -1,  -1,  -1,  -1,  -1,  -2 }, // 5 : <=
            {  -2,  -1,  -1,  -1,  -1,  -1,  -2 }, // 6 : >=
            {  -2,  -1,   9,  -1,  -1,  -1,  -2 }, // 7 : ==
            {  -2,  -1,  -1,  11, -1,  -1,  -2 }, // 8 : !=
            {  -2,  -1,  -1,  -1, -1,  -1,  -2 }, // 9 : ===
            {  -2,  -1,  -1,  -1, -1,  -1,  -2 }, // 10 : <>

            {  -2,  -1,  -1,  -1, -1,  -1,  -2 }, // 11 : !==

            {  -1,  -1,  -1,  -1,  14, -1,  -1 }, // 12 : '&' must receive another '&'
            {  -1,  -1,  -1,  -1,  -1,  15, -1 }, // 13 : '|' must receive another '|'

            {  -2,  -1,  -1,  -1,  -1,  -1,  -2 }, // 14 : &&
            {  -2,  -1,  -1,  -1,  -1,  -1,  -2 }, // 15 : ||
        };

        int state = 0;
        int i = 0;

        while (i < s.length()) {

            int col = getCol(s.charAt(i));
            state = M[state][col];

            if (state == -1) return null; // erreur
            if (state == -2) break;       // final

            i++;
        }

        return getFinalOperator(state);
    }
}
