public class Syntaxique {

    private final Lexicale lexer;
    private Token lookahead;

    public Syntaxique(Lexicale lexer) {
        this.lexer = lexer;
        this.lookahead = lexer.nextToken();
    }

    private void error(String msg) {
        throw new RuntimeException("Erreur syntaxique : " + msg +
                " — trouvé : " + lookahead.lexeme);
    }

    public void S() {
        if (!lookahead.type.equals("EOF")) {
            STMT();
            if (!lookahead.type.equals("EOF"))
                error("Fin du programme attendue");
        }
    }

    private void STMT() {

        if (lookahead.lexeme.equals("if")) {
            IFSTMT();
            return;
        }

        if (lookahead.lexeme.equals("echo")) {
            ECHOSTMT();
            match(";");
            return;
        }

        if (lookahead.lexeme.equals("do")) {
            DOWHILE();
            return;
        }

        if (lookahead.type.equals("IDENTIFIANT")) {
            ASSIGN();
            match(";");
            return;
        }

        error("Instruction attendue");
    }

    private void IFSTMT() {
        match("if");
        match("(");
        EXPR();
        match(")");
        BLOCK();
        OPTELSE();
    }

    private void OPTELSE() {
        if (lookahead.lexeme.equals("else")) {
            match("else");
            BLOCK();
        }
    }

    private void ECHOSTMT() {
        match("echo");
        EXPR();
    }

    private void BLOCK() {
        match("{");
        STMTLIST();
        match("}");
    }

    private void STMTLIST() {
        while (isStatementStart()) {
            STMT();
        }
    }

    private boolean isStatementStart() {
        return lookahead.lexeme.equals("if") ||
                lookahead.lexeme.equals("echo") ||
                lookahead.lexeme.equals("do") ||
                lookahead.type.equals("IDENTIFIANT");
    }

    private void ASSIGN() {
        matchType("IDENTIFIANT");
        match("=");
        EXPR();
    }

    private void DOWHILE() {
        match("do");
        BLOCK();
        match("while");
        match("(");
        EXPR();
        match(")");
        match(";");
    }

    private void EXPR() {
        EXPR_OR();
    }

    private void EXPR_OR() {
        EXPR_AND();
        while (lookahead.lexeme.equals("||")) {
            match("||");
            EXPR_AND();
        }
    }

    private void EXPR_AND() {
        EXPR_REL();
        while (lookahead.lexeme.equals("&&")) {
            match("&&");
            EXPR_REL();
        }
    }

    private void EXPR_REL() {
        SIMPLEEXPR();

        if (isRelOp()) {
            String op = lookahead.lexeme;
            match(op);
            SIMPLEEXPR();
        }
    }

    // Liste des opérateurs de comparaison acceptés
    private boolean isRelOp() {
        return lookahead.lexeme.equals("==") ||
                lookahead.lexeme.equals("!=") ||
                lookahead.lexeme.equals("<") ||
                lookahead.lexeme.equals(">") ||
                lookahead.lexeme.equals("<=") ||
                lookahead.lexeme.equals(">=") ||
                lookahead.lexeme.equals("<>") ||
                lookahead.lexeme.equals("===") ||
                lookahead.lexeme.equals("!==");
    }

    private void SIMPLEEXPR() {
        TERM();
        while (lookahead.lexeme.equals("+") || lookahead.lexeme.equals("-")) {
            String op = lookahead.lexeme;
            match(op);
            TERM();
        }
    }

    private void TERM() {
        FACTOR();
        while (lookahead.lexeme.equals("*") ||
                lookahead.lexeme.equals("/") ||
                lookahead.lexeme.equals("%")) {
            String op = lookahead.lexeme;
            match(op);
            FACTOR();
        }
    }

    private void FACTOR() {
        if (lookahead.lexeme.equals("(")) {
            match("(");
            EXPR();
            match(")");
        } else if (lookahead.type.equals("IDENTIFIANT")) {
            matchType("IDENTIFIANT");
        } else if (lookahead.type.equals("NOMBRE")) {
            matchType("NOMBRE");
        } else {
            error("Facteur attendu");
        }
    }

    private void match(String expected) {
        if (lookahead.lexeme.equals(expected))
            lookahead = lexer.nextToken();
        else
            error("'" + expected + "' attendu");
    }

    private void matchType(String type) {
        if (lookahead.type.equals(type))
            lookahead = lexer.nextToken();
        else
            error("Token de type " + type + " attendu");
    }
}
