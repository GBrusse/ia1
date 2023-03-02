// This class is a recursive-descent parser,
// modeled after the programming language's grammar.
// It constructs and has-a Scanner for the program
// being parsed.

public class Parser {

	private Scanner scanner;

	private void match(String s) throws SyntaxException {	//Matches the given string token with the current token from the scanner
		scanner.match(new Token(s));
	}

	private Token curr() throws SyntaxException {	//Returns the current token from the scanner
		return scanner.curr();
	}

	private int pos() {	//Returns the current position in the program being parsed
		return scanner.pos();
	}

	private NodeMulop parseMulop() throws SyntaxException {	//Parses and returns a multiplication or division operator
		if (curr().equals(new Token("*"))) {		//Matches the '*' token and returns a new NodeMulop with the position and operator
			match("*");
			return new NodeMulop(pos(), "*");
		}
		if (curr().equals(new Token("/"))) {		//Matches the '/' token and returns a new NodeMulop with the position and operator
			match("/");
			return new NodeMulop(pos(), "/");
		}
		return null;	//Returns null if current token is not a multiplication or division operator
	}

	private NodeAddop parseAddop() throws SyntaxException {		//Parses and returns an addition or subtraction operator
		if (curr().equals(new Token("+"))) {		//Matches the '+' token and returns a new NodeAddop with the position and operator
			match("+");
			return new NodeAddop(pos(), "+");
		}
		if (curr().equals(new Token("-"))) {		//Matches the '-' token and returns a new NodeAddop with the position and operator
			match("-");
			return new NodeAddop(pos(), "-");
		}
		return null;	//Returns null if the current token is not an addition or subtraction operator
	}

	private NodeFact parseFact() throws SyntaxException {	//Parses and returns a factor node
		if (curr().equals(new Token("-"))) {	//If the current token is a negative sign, creates a new NodeFactNeg with the factor node
			match("-");
			NodeFact fact = parseFact();
			return new NodeFactNeg(fact);
		}
		if (curr().equals(new Token("("))) {	//If the current token is an opening parenthesis, parses the expression inside and creates a new NodeFactExpr
			match("(");
			NodeExpr expr = parseExpr();
			match(")");
			return new NodeFactExpr(expr);
		}
		if (curr().equals(new Token("id"))) {	//If the current token is an identifier, creates a new NodeFactId with the position and identifier
			Token id = curr();
			match("id");
			return new NodeFactId(pos(), id.lex());
		}
		Token num = curr();	//If the current token is a number, creates a new NodeFactNum with the number
		match("num");
		return new NodeFactNum(num.lex());
	}

	private NodeTerm parseTerm() throws SyntaxException {	//Parses and returns a term node
		NodeFact fact = parseFact();
		NodeMulop mulop = parseMulop();
		if (mulop == null)	//If there is no multiplication or division operator, returns a new NodeTerm with the factor node and no operators
			return new NodeTerm(fact, null, null);
		NodeTerm term = parseTerm();
		term.append(new NodeTerm(fact, mulop, null));	//Appends a new NodeTerm with the factor node and the multiplication or division operator to the term
		return term;
	}

	private NodeExpr parseExpr() throws SyntaxException {	//Parses and returns an expression node
		NodeTerm term = parseTerm();
		NodeAddop addop = parseAddop();
		if (addop == null)	//If there is no addition or subtraction operator, returns a new NodeExpr with the term node and no operators
			return new NodeExpr(term, null, null);
		NodeExpr expr = parseExpr();
		expr.append(new NodeExpr(term, addop, null));	//Appends a new NodeExpr with the term node and the addition or subtraction operator to the expression
		return expr;
	}

	private NodeAssn parseAssn() throws SyntaxException {	//Parses and returns an assignment node
		Token id = curr();	//Matches an identifier token
		match("id");
		match("=");	//Matches an assignment operator token
		NodeExpr expr = parseExpr();	//Parses the right-hand side expression of the assignment and creates a new NodeAssn with the identifier and the expression
		NodeAssn assn = new NodeAssn(id.lex(), expr);
		return assn;
	}

	private NodeStmt parseStmt() throws SyntaxException {	//Parses and returns a statement node
		NodeAssn assn = parseAssn();	//Parses an assignment and matches a semicolon token
		match(";");
		NodeStmt stmt = new NodeStmt(assn);	//Creates a new NodeStmt with the assignment node
		return stmt;
	}

	public Node parse(String program) throws SyntaxException {	//Parses the program string and returns the root node of the parse tree
		scanner = new Scanner(program);	//Initializes the scanner with the program string and gets the first token
		scanner.next();
		NodeStmt stmt = parseStmt();	//Parses a statement and matches an end-of-file token
		match("EOF");
		return stmt;	//Returns the root node of the parse tree, which is the statement node
	}
}
