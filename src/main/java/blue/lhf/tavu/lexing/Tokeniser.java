package blue.lhf.tavu.lexing;

import blue.lhf.tavu.structure.*;

import java.util.*;

public class Tokeniser<E extends Enum<E> & ITokenType<E>> {
    private final Class<E> tokenTypes;

    public Tokeniser(final Class<E> tokenTypes) {
        this.tokenTypes = tokenTypes;
    }

    public List<Token<E, ?>> tokenise(final String input) {
        int index = 0;
        final List<Token<E, ?>> tokens = new ArrayList<>();
        while (index < input.length()) {
            index = skipWhitespace(input, index);
            if (index >= input.length()) break;

            final Token<E, ?> token = longestMatch(input, index);
            if (token == null) throw new TavuException("Invalid token at index " + index);

            index += token.matched().length();
            tokens.add(token);
        }

        return Collections.unmodifiableList(tokens);
    }

    private Token<E, ?> longestMatch(final String input, final int index) {
        final String target = input.substring(index);

        Token<E, ?> longestMatch = null;
        for (final E type : tokenTypes.getEnumConstants()) {
            final var matcher = type.pattern().matcher(target);
            if (!matcher.lookingAt()) continue;

            final String value = matcher.group();
            if (longestMatch == null || value.length() > longestMatch.matched().length()) {
                longestMatch = type.create(value);
            }
        }

        return longestMatch;
    }

    private static int skipWhitespace(final String input, int index) {
        for (; index < input.length() && Character.isWhitespace(input.charAt(index)); ++index);
        return index;
    }
}
