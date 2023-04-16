package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.Context;
import blue.lhf.tavu.values.Value;

public class MultiplyStatement implements Statement {
    @Override
    public Value execute(Context context) {
        final Value b = context.stack().pop();
        final Value a = context.stack().pop();
        return a.multiply(b);
    }

    public String toString() {
        return "MUL";
    }
}
