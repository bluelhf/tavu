package blue.lhf.tavu.parsing;

import blue.lhf.tavu.lexing.*;
import blue.lhf.tavu.statements.*;
import blue.lhf.tavu.structure.*;

import java.util.*;

import static blue.lhf.tavu.lexing.TokenType.EQUALS;

public interface Expression {
    Code generate();

    record NumberExpression(Number value) implements Expression {
        @Override
        public Code generate() {
            return Code.of(new LoadNumberStatement(value));
        }
    }

    record StringExpression(String value) implements Expression {
        @Override
        public Code generate() {
            return Code.of(new LoadStringStatement(value));
        }
    }

    record BlockExpression(List<Expression> subExpressions) implements Expression {
        @Override
        public Code generate() {
            final ArrayList<Statement> statements = new ArrayList<>();
            statements.add(new PushScopeStatement());
            for (int i = 0; i < subExpressions.size(); i++) {
                Expression subExpression = subExpressions.get(i);
                statements.addAll(subExpression.generate().statements());

                // last statement is the return value
                if (i < subExpressions.size() - 1) {
                    statements.add(new StackClearStatement());
                }
            }
            statements.add(new PopScopeStatement());
            return new Code(statements);
        }
    }

    record BinaryOperation(Expression left, Token<TokenType, ?> operator, Expression right) implements Expression {
        @Override
        public Code generate() {
            if (operator.is(EQUALS) && left instanceof Identifier identifier) {
                final ArrayList<Statement> statements = new ArrayList<>(right.generate().statements());
                statements.add(new StoreIdentifierStatement(identifier.name()));
                return new Code(statements);
            }

            final ArrayList<Statement> statements = new ArrayList<>();
            statements.addAll(left.generate().statements());
            statements.addAll(right.generate().statements());

            switch (operator.type()) {
                case PLUS -> statements.add(new AddStatement());
                case STAR -> statements.add(new MultiplyStatement());
                default -> throw new TavuException("Unknown operator");
            }

            return new Code(statements);
        }
    }

    record FunctionCall(String name, Expression argument) implements Expression {
        @Override
        public Code generate() {
            final ArrayList<Statement> statements = new ArrayList<>();
            if (argument != null) {
                statements.addAll(argument.generate().statements());
            }
            statements.add(new CallStatement(name));
            return new Code(statements);
        }
    }

    record Identifier(String name) implements Expression {
        @Override
        public Code generate() {
            return Code.of(new LoadIdentifierStatement(name));
        }
    }

    record FunctionExpression(Expression expression) implements Expression {
        @Override
        public Code generate() {
            return Code.of(new LoadFunctionStatement(expression.generate()));
        }
    }
}
