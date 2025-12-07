import java.util.*;

class Token {
    String type;
    String lexeme;

    Token(String type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return "<" + type + ", " + lexeme + ">";
    }
}

public class Lexicale {

    // --- Automates ---
    private AutomatePHP_If ifkw = new AutomatePHP_If();
    private AutomatePHP_Else elsekw = new AutomatePHP_Else();

    private AutomatePHP_Identifiant identifiant = new AutomatePHP_Identifiant();
    private AutomatePHP_Nombre nombre = new AutomatePHP_Nombre();

    private AutomatePHP_Comparaison comparaison = new AutomatePHP_Comparaison();
    private AutomatePHP_IncDec incdec = new AutomatePHP_IncDec();
    private AutomatePHP_Arithmetique arithmetique = new AutomatePHP_Arithmetique();

    private AutomatePHP_DoWhile dowhile = new AutomatePHP_DoWhile();
    private AutomatePHP_ForForeach forforeach = new AutomatePHP_ForForeach();
    private AutomatePHP_CaseSwitch caseswitch = new AutomatePHP_CaseSwitch();
    private AutomatePHP_Echo echo = new AutomatePHP_Echo();
    private AutomatePHP_NomPrenom nomprenom = new AutomatePHP_NomPrenom();
    private AutomatePHP_Separateurs separateurs = new AutomatePHP_Separateurs();

    private List<Token> tokens = new ArrayList<>();
    private int position = 0;

    // ------------------------------
    // Charger une ligne
    // ------------------------------
    public void load(String ligne) {
        tokens.clear();
        position = 0;

        List<String> lexemes = decouper(ligne);
        for (String lex : lexemes) {
            tokens.add(reconnaitre(lex));
        }

        tokens.add(new Token("EOF", "#"));
    }

    public Token nextToken() {
        if (position < tokens.size())
            return tokens.get(position++);
        return new Token("EOF", "#");
    }

    public Token reconnaitre(String lexeme) {

        // --- mots clés ---
        if (ifkw.analyse(lexeme))
            return new Token("MOTCLE_IF", lexeme);
        if (elsekw.analyse(lexeme))
            return new Token("MOTCLE_ELSE", lexeme);
        if (dowhile.analyse(lexeme))
            return new Token("MOTCLE_DO_WHILE", lexeme);
        if (forforeach.analyse(lexeme))
            return new Token("MOTCLE_FOR_FOREACH", lexeme);
        if (caseswitch.analyse(lexeme))
            return new Token("MOTCLE_CASE_SWITCH", lexeme);
        if (echo.analyse(lexeme))
            return new Token("MOTCLE_ECHO", lexeme);

        // --- opérateurs de comparaison ---
        String op = comparaison.analyse(lexeme);
        if (op != null)
            return new Token("OPERATEUR_COMPARAISON", op);

        // --- opérateurs INC / DEC ---
        if (incdec.analyse(lexeme))
            return new Token("OPERATEUR_INC_DEC", lexeme);

        // --- opérateurs arithmétiques ---
        if (arithmetique.analyse(lexeme))
            return new Token("OPERATEUR_ARITHMETIQUE", lexeme);

        // --- identifiants / nombres ---
        if (identifiant.analyse(lexeme))
            return new Token("IDENTIFIANT", lexeme);
        if (nombre.analyse(lexeme))
            return new Token("NOMBRE", lexeme);

        // --- nom + prénom ---
        if (nomprenom.analyse(lexeme)) {
            if (lexeme.equals("timsi"))
                return new Token("NOM", lexeme);
            if (lexeme.equals("mohcine"))
                return new Token("PRENOM", lexeme);
            return new Token("NOM_PRENOM", lexeme);
        }

        // --- séparateurs ---
        if (separateurs.analyse(lexeme))
            return new Token("SEPARATEUR", lexeme);

        // --- sinon erreur ---
        return new Token("ERREUR", lexeme);
    }

    private List<String> decouper(String ligne) {
        List<String> lexemes = new ArrayList<>();
        StringBuilder courant = new StringBuilder();
        int i = 0;

        while (i < ligne.length()) {

            // --- 3 caractères ---
            if (i + 2 < ligne.length()) {
                String trois = "" + ligne.charAt(i) + ligne.charAt(i + 1) + ligne.charAt(i + 2);

                if (trois.equals("===") || trois.equals("!==")) {
                    if (courant.length() > 0) {
                        lexemes.add(courant.toString());
                        courant.setLength(0);
                    }
                    lexemes.add(trois);
                    i += 3;
                    continue;
                }
            }

            char c = ligne.charAt(i);

            // --- 2 caractères ---
            if (i + 1 < ligne.length()) {
                String deux = "" + c + ligne.charAt(i + 1);

                if (deux.equals("==") || deux.equals("!=") || deux.equals("<=") ||
                        deux.equals(">=") || deux.equals("<>") ||
                        deux.equals("++") || deux.equals("--")) {

                    if (courant.length() > 0) {
                        lexemes.add(courant.toString());
                        courant.setLength(0);
                    }
                    lexemes.add(deux);
                    i += 2;
                    continue;
                }
            }

            // --- symboles simples ---
            if ("(){};,=+-*/>!<".indexOf(c) >= 0) {
                if (courant.length() > 0) {
                    lexemes.add(courant.toString());
                    courant.setLength(0);
                }
                lexemes.add("" + c);
                i++;
                continue;
            }

            // --- espaces ---
            if (Character.isWhitespace(c)) {
                if (courant.length() > 0) {
                    lexemes.add(courant.toString());
                    courant.setLength(0);
                }
                i++;
                continue;
            }

            // --- lettres/chiffres ---
            courant.append(c);
            i++;
        }

        if (courant.length() > 0)
            lexemes.add(courant.toString());

        return lexemes;
    }
}
