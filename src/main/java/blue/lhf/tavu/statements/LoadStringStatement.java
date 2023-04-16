package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.Context;
import blue.lhf.tavu.values.*;

public record LoadStringStatement(String value) implements Statement {
    @Override
    public Value execute(Context context) {
        return new StringValue(value);
    }

    @Override
    public String toString() {
        return "LOAD_STR " + escape(value);
    }

    private static String escape(final String value) {
        return value.replace("\t", "\\t")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
    }
}
