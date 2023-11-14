package com.kingmang.bpp.parser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;

import com.kingmang.bpp.ast.*;
import com.kingmang.bpp.Context;
import com.kingmang.bpp.Function;
import com.kingmang.bpp.Tag;
import com.kingmang.bpp.Token;


public class Parser {

	public static final Hashtable<String, KEYWORDS> reservedWords;
	private Expression makeOperation(char op, Expression var1, Expression var2) {
		switch (op) {
			case '=':
				return new EqExpression(var1, var2);
			case '!':
				return new NeExpression(var1, var2);
			case '>':
				return new GtExpression(var1, var2);
			case '<':
				return new LtExpression(var1, var2);
			case '*':
				return new MulExpression(var1, var2);
			case '/':
				return new DivExpression(var1, var2);
			case '^':
				return new PowExpression(var1, var2);
			case '%':
				return new ModExpression(var1, var2);
			case '+':
				return new AddExpression(var1, var2);
			case '-':
				return new SubExpression(var1, var2);
		}
		return null;
	}

	private Evaluator getInstruction() {
		switch (token.getTag()) {

			case Tag.tagSystem:
				KEYWORDS sys = (KEYWORDS) token.value;
				if (sys == KEYWORDS.НОВЬ)
				{
					return parseName(null);
				}

				getNextToken();
				switch (sys) {
					case ДЛЯПОКУДА:
						return parseFor();
					case ПОКУДА:
						return parseWhile();
					case ВОИН:
						return parseFunction();
					case ВОЗДАТЬ:
						return parseReturn();
					case МОЛВИТЬ:
						return parsePrint();
					case УСЛОВКА:
						return parseIf();
					default:
						break;
				}
				break;


			case Tag.tagSymbol:
				if (!token.isOperator()) {
					break;
				}
				char op = token.charValue();
				getNextToken();
				return parsePreOp(op);

			case Tag.tagName:
				return parseName(null);

			case Tag.tagEnd:
				getNextToken();
				return null;

			default:
				break;
		}
		if (token.getTag() != Tag.tagSymbol || token.charValue() != ';') {
			System.out
					.println("unused token(tag=" + token.getTag() + "): " + token);
		}
		getNextToken();
		return null;
	}

	static {
		reservedWords = new Hashtable<String, KEYWORDS>();
		for (KEYWORDS reserved : KEYWORDS.values()) {
			reservedWords.put(reserved.toString(), reserved);
		}
	}


	private Queue<Token> tokens;
	private Token token;

	//Depth of the current parsing context.
	private int depth;

	//Root context.
	private Context context;

	//Anonymous function name identifier.
	private static int anonymousFunction = 0;

	public Context parse(Queue<Token> tokens) {
		this.context = new Context();
		this.depth = 0;
		this.tokens = tokens;

		getNextToken();
		context.code.addAll(parseInternal());
		return context;
	}

	private ArrayList<Evaluator> parseInternal() {
		final ArrayList<Evaluator> code = new ArrayList<Evaluator>();
		depth ++;

		while (true) {
			if (token == null)
				break;
			else if (token.equals('}')) {
				if (depth > 1)
					getNextToken();
				else
					;
				break;
			}
			final Evaluator instruction = getInstruction();
			if (instruction != null)
			{
				code.add(instruction);
			}
		}
		depth --;
		return code;
	}

	private Token getNextToken() {
		token = tokens.poll();
		return token;
	}

	private Token checkNextToken() {
		return tokens.peek();
	}



	private Evaluator parseIf() {
		if (!token.equals('('))
			return null;

		getNextToken();
		Expression expr = parseExpression();

		if (!token.equals(')'))
			return null;

		List<Evaluator> instructions = new ArrayList<Evaluator>();
		if (getNextToken().equals('{')) {
			getNextToken();
			instructions = parseInternal();
		} else {
			instructions.add(getInstruction());
		}

		if ((token.getTag() != Tag.tagSystem)
				|| (KEYWORDS) token.value != KEYWORDS.ОТНЮДЬ) {
			getNextToken();
		}
		if (token != null
				&& (token.getTag() == Tag.tagSystem)
				&& (KEYWORDS) token.value == KEYWORDS.ОТНЮДЬ) {
			List<Evaluator> instructionsElse = new ArrayList<Evaluator>();
			if (getNextToken().equals('{')) {
				getNextToken();
				instructionsElse = parseInternal();
			} else
				instructionsElse.add(getInstruction());
			return new IfStatement(expr, instructions, instructionsElse);
		}
		return new IfStatement(expr, instructions);
	}


	private Evaluator parseWhile() {
		if (!token.equals('('))
			return null;

		getNextToken();
		Expression expr = parseExpression();

		if (!token.equals(')'))
			return null;

		List<Evaluator> instructions = new ArrayList<Evaluator>();
		if (getNextToken().equals('{')) {
			getNextToken();
			instructions = parseInternal();
		} else
			instructions.add(getInstruction());
		return new WhileStatement(expr, instructions);
	}

	private Evaluator parseFor() {
		if (!token.equals('('))
			return null;

		getNextToken();
		Expression a = parseExpression();
		if (!token.equals(';'))
			return null;

		getNextToken();
		Expression b = parseExpression();
		if (!token.equals(';'))
			return null;

		getNextToken();
		Expression c = parseExpression();
		if (token.equals(';'))
			getNextToken();

		if (!token.equals(')'))
			return null;

		List<Evaluator> instructions = new ArrayList<Evaluator>();
		if (getNextToken().equals('{')) {
			getNextToken();
			instructions = parseInternal();
		} else
			instructions.add(getInstruction());
		return new ForStatement(a, b, c, instructions);
	}



	private Evaluator parseFunction() {
		List<String> arguments = new ArrayList<String>();
		List<Evaluator> instructions = new ArrayList<Evaluator>();

		String functionName = token.value.toString();


		if (!getNextToken().equals('('))
			return null;

		getNextToken();
		if (!token.equals(')'))
			while (true) {
				String name = token.value.toString();
				arguments.add(name);
				getNextToken();
				if (token.equals(')'))
					break;
				if (!token.equals(','))
					return null;
				getNextToken();
			}


		if (!getNextToken().equals('{'))
			return null;

		getNextToken();
		instructions = parseInternal();

		context.addFunction(new Function(functionName, arguments,
				instructions));
		return null;
	}


	private Evaluator parseReturn() {
		Expression expression = parseExpression();
		return new ReturnStatement(expression);
	}

	private Evaluator parsePrint() {
		Expression expression = parseExpression();
		return new PrintStatement(expression);
	}

	private List<Expression> parseExpressions() {
		boolean expectsDelim = false;
		boolean expectsElem = false;
		List<Expression> expressions = new ArrayList<Expression>();
		while (true) {
			if (token.equals(',')) {
				if (expectsDelim)
					expectsDelim = false;
				else
					expressions.add(null);
				expectsElem = true;
				getNextToken();
				continue;
			} else if (token.equals(')')) {
				if (expectsElem && !expectsDelim)
					expressions.add(null);
				getNextToken();
				return expressions;
			}

			if (expectsDelim) {
				return expressions;
			}

			expressions.add(parseExpression());
			expectsDelim = true;
		}
	}

	private Expression parseName(Expression leftExpression) {
		Expression expr = null;

		if (token.getTag() == Tag.tagSystem &&
				(KEYWORDS) token.value == KEYWORDS.НОВЬ) {

			getNextToken();
			if (token.getTag() == Tag.tagName)
			{
				final String className = token.value.toString();
				getNextToken();
				if (token.equals('('))
				{
					getNextToken();
					expr = new NewExpression(className, parseExpressions());
				}
			}
		}
		else
		{
			final String name = token.value.toString();
			getNextToken();

			if (token.equals('.')) {
				getNextToken();
				if (leftExpression == null)
					expr = parseName(new VariableAccess(name));
				else
					expr = parseName(new FieldAccess(leftExpression, name));
			}

			else if (token.equals('(')) {
				getNextToken();
				if (leftExpression == null)
					expr = new FunctionCall(name, parseExpressions());
				else
					expr = new MethodCall(leftExpression, name, parseExpressions());
			}

			else if (token.equals('=') && !checkNextToken().equals('=')) {
				getNextToken();
				Expression assignExpr;

				if (token.getTag() == Tag.tagSystem &&
						((KEYWORDS) token.value) == KEYWORDS.ВОИН)
				{
					final String functionName = "_anon" + (anonymousFunction ++);
					token.value = functionName;
					parseFunction();
					assignExpr = new VariableAccess(functionName);
				}
				else
				{
					assignExpr = parseExpression();
				}
				if (leftExpression == null)
					expr = new VariableAssign(name, assignExpr);
				else
					expr = new FieldAssign(leftExpression, name, assignExpr);
			}

			// Access to a field or a variable
			else if (token.getTag() == Tag.tagSymbol) {
				if (leftExpression == null)
					expr = new VariableAccess(name);
				else
					expr = new FieldAccess(leftExpression, name);
			}
		}

		if (expr != null) {
			if (token.equals('.')) {
				getNextToken();
				if (token.getTag() == Tag.tagName) {
					return parseName(expr);
				}
			}
			return expr;
		}

		return null;
	}

	private Expression parseExpression() {
		final Expression expr = parseExprAddSub();

		if (token.isOperator())
			switch (token.charValue())
			{

			case '!':
				getNextToken();
				if (token.equals('=')) {
					getNextToken();
					if (token.equals('='))
						getNextToken();
				}
				if (expr == null)
					return makeOperation('!', new BooleanLiteral(true),
							parseExpression());
				return makeOperation('!', expr, parseExpression());

			case '=':
				getNextToken();
				if (token.equals('=')) {
					getNextToken();
					if (token.equals('='))
						getNextToken();
				} else if (token.equals('>')) {
				} else if (token.equals('<')) {
				}
				return makeOperation('=', expr, parseExpression());

			case '>':
				getNextToken();
				if (token.equals('=')) {
				}
				return makeOperation('>', expr, parseExpression());

			case '<':
				getNextToken();
				if (token.equals('=')) {
				}
				return makeOperation('<', expr, parseExpression());
			}

		else if (token.equals('.')) {
			getNextToken();
			return parseName(expr);
		}

		return expr;
	}

	private Expression parseExprAddSub() {
		Expression expr = parseExprMulDiv();

		while (token.equals('+') || token.equals('-')) {
			char op = token.charValue();
			getNextToken();
			expr = makeOperation(op, expr, parseExprMulDiv());
		}
		return expr;
	}

	private Expression parseExprMulDiv() {
		Expression expr = parseExprPower();

		while (token.equals('*') || token.equals('/') || token.equals('%')) {
			char op = token.charValue();
			getNextToken();
			expr = makeOperation(op, expr, parseExprPower());
		}
		return expr;
	}

	private Expression parseExprPower() {
		Expression expr = parseExprUnary();

		if (token.equals('^')) {
			char op = token.charValue();
			getNextToken();
			expr = makeOperation(op, expr, parseExprPower());
		}
		return expr;
	}

	private Expression parseExprUnary() {
		char op;
		if (token.equals('+') || token.equals('-'))
		{
			op = token.charValue();

			if (getNextToken().equals(op))
				return parsePreOp(op);
		}

		else op = 0;

		Expression expr = parseExprBracket();
		if (op == '-')
			return makeOperation(op, new NumberLiteral(0), expr);
		return expr;
	}

	private Expression parseExprBracket() {

		if (token.equals('(')) {
			getNextToken();
			Expression expr = parseExpression();
			if (!token.equals(')')) {

				return null;
			}
			getNextToken();
			return expr;
		}

		if (token.equals('[')) {
			getNextToken();
			List<Expression> expr = parseExpressions();
			if (!token.equals(']')) {
				return null;
			}
			getNextToken();
			return new MatrixLiteral(expr);
		}

		Token tokLiteral = token;
		if (token.getTag() == Tag.tagNumber) {
			getNextToken();
			return new NumberLiteral(tokLiteral.value);
		} else if (token.getTag() == Tag.tagString) {
			getNextToken();
			return new StringLiteral(tokLiteral.value);
		} else if (token.getTag() == Tag.tagSystem) {
			if ((KEYWORDS) tokLiteral.value == KEYWORDS.НОВЬ)
			{
				return parseName(null);
			}
			getNextToken();
			switch ((KEYWORDS) tokLiteral.value) {
			case ПРАВДА:
				return new BooleanLiteral(true);
			case КРИВДА:
				return new BooleanLiteral(false);
			default:
				return null;
			}
		}

		if (token.getTag() == Tag.tagName) {
			Expression expr = parseName(null);
			if (token.equals('+') || token.equals('-')) {
				final char op = token.charValue();

				if (checkNextToken().equals(op)) {
					getNextToken();
					getNextToken();
					return makeShortOp(op, expr, false);
				}
			}
			return expr;
		}

		if (token.getTag() == Tag.tagSymbol)
			return null;

		System.out.println("parse expression error: " + token);
		return null;
	}

	private Expression parsePreOp(char op) {
		if (!token.equals(op)) {
			return null;
		}
		getNextToken();
		if (token.getTag() != Tag.tagName) {
			return null;
		}
		Expression accessor = parseName(null);
		return makeShortOp(op, accessor, true);
	}


	private Expression makeShortOp(char op, Expression accessor, boolean pre) {
		if (accessor == null)
			return null;

		if (accessor instanceof VariableAccess) {
			String name = (String) accessor.getOperand(0);
			Expression expr = new VariableAssign(name, makeOperation(op,
					accessor, new NumberLiteral(1)));
			if (pre)
				return expr;
			return makeOperation(op, expr, new NumberLiteral(-1));
		} else if (accessor instanceof FieldAccess) {
			Expression var = (Expression) accessor.getOperand(0);
			String name = (String) accessor.getOperand(1);
			Expression expr = new FieldAssign(accessor, name, makeOperation(
					op, var, new NumberLiteral(1)));
			if (pre)
				return expr;
			return makeOperation(op, expr, new NumberLiteral(-1));
		}

		return null;
	}


}
