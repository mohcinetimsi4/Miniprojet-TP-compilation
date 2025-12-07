import javax.swing.*;

public class Final {

    public static void main(String[] args) {

        while (true) {

            String codePHP = JOptionPane.showInputDialog(
                    null,
                    "-- PHP IF/ELSE --\nEntrez votre code PHP :\n");

            if (codePHP == null) {
                JOptionPane.showMessageDialog(null, "Fin du programme.");
                break;
            }

            if (codePHP.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Aucun code entré.");
                continue;
            }

            // --- Analyse lexicale ---
            Lexicale lexer = new Lexicale();
            lexer.load(codePHP);

            StringBuilder resultLex = new StringBuilder();
            resultLex.append("--- Analyse lexicale ---\n");

            Token t;
            boolean erreurLexicale = false;

            while (!(t = lexer.nextToken()).type.equals("EOF")) {
                resultLex.append(t).append("\n");
                if (t.type.equals("ERREUR")) {
                    erreurLexicale = true;
                }
            }

            if (erreurLexicale) {
                resultLex.append("\n>> Erreur détectée dans l’analyse lexicale !");
            } else {
                resultLex.append("\n>> Analyse lexicale correcte !");
            }

            JOptionPane.showMessageDialog(null, resultLex.toString());

            // --- Analyse syntaxique ---
            lexer.load(codePHP);
            Syntaxique syn = new Syntaxique(lexer);

            String resultSyn;

            try {
                syn.S();
                resultSyn = "--- Analyse syntaxique ---\nSyntaxe correcte !";
            } catch (RuntimeException e) {
                resultSyn = "--- Analyse syntaxique ---\nErreur : " + e.getMessage();
            }

            JOptionPane.showMessageDialog(null, resultSyn);
        }
    }
}
