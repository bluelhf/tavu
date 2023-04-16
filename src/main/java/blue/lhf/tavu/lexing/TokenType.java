package blue.lhf.tavu.lexing;

import blue.lhf.tavu.structure.*;

import java.util.regex.*;

public enum TokenType implements ITokenType<TokenType> {
    EQUALS("="),
    IDENTIFIER("[a-zA-Z_]\\w*") {
        @Override
        public Token<TokenType, String> create(final String matched) {
            return Token.token(TokenType.IDENTIFIER, matched, matched);
        }
    },
    LEFT_CURLY("\\{"),
    RIGHT_CURLY("\\}"),
    LEFT_PAREN("\\("),
    RIGHT_PAREN("\\)"),
    PLUS("\\+"),
    STAR("\\*"),
    SEMICOLON(";"),
    NUMBER("[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\d+)(\\.)?((\\d+)?)([eE][+-]?(\\d+))?)|(\\.(\\d+)([eE][+-]?(\\d+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\d+)))[fFdD]?))[\\x00-\\x20]*") {
        @Override
        public Token<TokenType, Double> create(String matched) {
            return Token.token(TokenType.NUMBER, matched, Double.parseDouble(matched));
        }
    },
    STRING("\"(?>[^\\\\\"]+|\\\\.)*\"") {
        private String parseContent(final String matched) {
            final String content = matched.substring(1, matched.length() - 1);
            final StringBuilder sb = new StringBuilder();
            int index = 0;
            while (index < content.length()) {
                final char c = content.charAt(index);
                if (c == '\\') {
                    final char next = content.charAt(index + 1);
                    switch (next) {
                        case 'n' -> sb.append('\n');
                        case 't' -> sb.append('\t');
                        case 'r' -> sb.append('\r');
                        case 'b' -> sb.append('\b');
                        case 'f' -> sb.append('\f');
                        case '\'' -> sb.append('\'');
                        case '"' -> sb.append('"');
                        case '\\' -> sb.append('\\');
                        case 'u' -> {
                            final String hex = content.substring(index + 2, index + 6);
                            sb.append((char) Integer.parseInt(hex, 16));
                            index += 5;
                        }
                        default -> throw new TavuException("Invalid escape sequence: \\" + next);
                    }
                    index++;
                } else {
                    sb.append(c);
                }

                index++;
            }

            return sb.toString();
        }

        @Override
        public Token<TokenType, String> create(String matched) {
            return Token.token(TokenType.STRING, matched, parseContent(matched));
        }
    }
    ;

    private final Pattern pattern;

    TokenType(final String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public Token<TokenType, ?> create(final String matched) {
        return Token.literal(this, matched);
    }

    public Pattern pattern() {
        return pattern;
    }
}
