package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.Context;
import blue.lhf.tavu.values.Value;

public class AddStatement implements Statement {

    @Override
    public Value execute(final Context context) {
        final Value b = context.stack().pop();
        final Value a = context.stack().pop();
        return a.add(b);
    }

    public String toString() {
        return "ADD";
    }
}
