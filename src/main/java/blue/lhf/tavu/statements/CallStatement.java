package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.*;
import blue.lhf.tavu.values.*;

public record CallStatement(String function) implements Statement {
    @Override
    public Value execute(Context context) {
        final Value function = context.scope().get(function());
        if (!(function instanceof FunctionValue func)) {
            throw new TavuException("Cannot call non-function");
        }

        func.code().evaluate(context);
        return null;
    }

    @Override
    public String toString() {
        return "CALL " + function();
    }
}
