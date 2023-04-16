package blue.lhf.tavu.structure;

import blue.lhf.tavu.values.*;

import java.util.*;

public record Scope(Scope parent, Map<String, Value> identifiers) {
    public static Scope root(final Map<String, Value> identifiers) {
        return new Scope(null, new HashMap<>(identifiers));
    }

    public Value get(String name) {
        Scope current = this;
        while (current != null) {
            final Value value = current.identifiers().get(name);
            if (value != null) {
                return value;
            }
            current = current.parent();
        }

        return null;
    }

    public String log() {
        final List<Scope> tree = new ArrayList<>();
        Scope current = this;
        while (current != null) {
            tree.add(0, current);
            current = current.parent();
        }

        final StringBuilder builder = new StringBuilder();
        int indent = 0;
        for (final Scope scope : tree) {
            builder.append(" ".repeat(indent)).append(scope.identifiers()).append("\n");
            indent += 2;
        }

        return builder.toString();
    }

    public void put(final String name, final Value value) {
        final List<Scope> tree = new ArrayList<>();
        Scope current = this;
        while (current != null) {
            tree.add(0, current);
            current = current.parent();
        }

        for (final Scope scope : tree) {
            if (scope.identifiers().containsKey(name)) {
                scope.identifiers().put(name, value);
                return;
            }
        }

        identifiers().put(name, value);
    }
}
