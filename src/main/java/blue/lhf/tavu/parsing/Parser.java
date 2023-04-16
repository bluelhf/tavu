package blue.lhf.tavu.parsing;

import blue.lhf.tavu.lexing.*;
import blue.lhf.tavu.structure.*;

import java.util.*;

import static blue.lhf.tavu.lexing.TokenType.*;

public class Parser {
    private final List<Token<TokenType, ?>> stream;
    private int index;

    public Parser(final List<Token<TokenType, ?>> stream) {
        this.stream = stream;
    }

    public Expression parse() {
        return parseBlock(false);
    }

    public Expression parseBlock(boolean expectCurly) {
        if (expectCurly) {
            index -= 1;
            if (index < stream.size() && stream.get(index).is(LEFT_CURLY)) {
                index++;
            } else {
                throw new TavuException("Expected {");
            }
        }
        final List<Expression> subExpressions = new ArrayList<>();
        while (index < stream.size()) {
            final Token<TokenType, ?> subToken = stream.get(index);
            if (expectCurly && subToken.is(RIGHT_CURLY)) {
                index++;
                break;
            } else {
                subExpressions.add(parseExpression());
                if (index < stream.size() && stream.get(index).is(SEMICOLON)) {
                    index++;
                } else {
                    break;
                }
            }
        }
        return new Expression.BlockExpression(subExpressions);
    }

    private Expression parseExpression() {
        Expression expression = parseTerm();
        if (index < stream.size()) {
            final Token<TokenType, ?> token = stream.get(index);
            if (token.is(EQUALS)) {
                index++;
                expression = new Expression.BinaryOperation(expression, token, parseExpression());
            }
        }

        return expression;
    }

    private Expression parseTerm() {
        Expression expression = parseFactor();
        if (index < stream.size()) {
            final Token<TokenType, ?> token = stream.get(index);
            if (token.is(PLUS)) {
                index++;
                expression = new Expression.BinaryOperation(expression, token, parseTerm());
            }
        }

        return expression;
    }

    private Expression parseFactor() {
        Expression expression = parsePrimary();
        while (index < stream.size()) {
            final Token<TokenType, ?> token = stream.get(index);
            if (token.is(STAR)) {
                index++;
                expression = new Expression.BinaryOperation(expression, token, parseFactor());
            } else {
                break;
            }
        }
        return expression;
    }

    public boolean read(final TokenType expected) {
        if (index < stream.size() && stream.get(index).is(expected)) {
            index++;
            return true;
        } else {
            return false;
        }
    }

    private Expression parsePrimary() {
        final Token<TokenType, ?> token = stream.get(index++);
        return switch (token.type()) {
            case STRING -> new Expression.StringExpression((String) token.value());
            case NUMBER -> new Expression.NumberExpression((Number) token.value());
            case IDENTIFIER -> {
                final String identifier = (String) token.value();

                if (read(LEFT_PAREN)) {
                    if (read(RIGHT_PAREN)) {
                        yield new Expression.FunctionCall(identifier, null);
                    }

                    final Expression argument = parseExpression();
                    if (read(RIGHT_PAREN)) {
                        yield new Expression.FunctionCall(identifier, argument);
                    }

                    throw new TavuException("Expected right parenthesis to match left parenthesis at index " + index);
                }

                yield new Expression.Identifier(identifier);
            }
            case LEFT_PAREN -> {
                if (read(RIGHT_PAREN)) {
                    if (read(LEFT_CURLY)) {
                        yield new Expression.FunctionExpression(parseBlock(true));
                    }

                    index--;
                }

                final Expression expression = parseExpression();
                if (read(RIGHT_PAREN)) yield expression;
                throw new TavuException("Expected right parenthesis to match left parenthesis");
            }
            case LEFT_CURLY -> parseBlock(true);
            default -> throw new TavuException("Unexpected value: " + token);
        };
    }
}
