package blue.lhf.tavu.lexing;

import java.util.regex.*;

public interface ITokenType<Self extends Enum<Self> & ITokenType<Self>> {
    Pattern pattern();
    Token<Self, ?> create(final String matched);
}
