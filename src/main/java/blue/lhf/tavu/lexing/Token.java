package blue.lhf.tavu.lexing;

public abstract class Token<E extends Enum<E>, T> {
    private final String matched;

    protected Token(final String matched) {
        this.matched = matched;
    }

    public String matched() {
        return matched;
    }

    public abstract E type();

    public T value() {
        return null;
    }

    @Override
    public String toString() {
        return matched();
    }

    public boolean is(final E type) {
        return type() == type;
    }

    public static <E extends Enum<E>, T> Token<E, T> token(final E type, final String matched, final T valued) {
        return new Token<>(matched) {
            @Override
            public E type() {
                return type;
            }

            @Override
            public T value() {
                return valued;
            }
        };
    }

    public static <E extends Enum<E>, T> Token<E, T> literal(final E type, final String matched) {
        return new Token<>(matched) {
            @Override
            public E type() {
                return type;
            }
        };
    }
}
