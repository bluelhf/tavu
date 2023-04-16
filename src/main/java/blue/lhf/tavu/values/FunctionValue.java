package blue.lhf.tavu.values;

import blue.lhf.tavu.structure.*;

import java.util.function.*;

public record FunctionValue(Code code) implements Value {

    public static FunctionValue ofNative(final Consumer<Context> nativeFunction) {
        return new FunctionValue(Code.of(context -> {
            nativeFunction.accept(context);
            return null;
        }));
    }

    public static FunctionValue ofNative(final Function<Context, Value> nativeFunction) {
        return new FunctionValue(Code.of(nativeFunction::apply));
    }

    @Override
    public Value add(Value other) {
        throw new UnsupportedOperationException("Cannot add functions");
    }

    @Override
    public Value multiply(Value other) {
        throw new UnsupportedOperationException("Cannot multiply functions");
    }

    @Override
    public String asString() {
        return "<function>";
    }
}
