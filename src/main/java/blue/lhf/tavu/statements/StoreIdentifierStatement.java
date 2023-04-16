package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.*;
import blue.lhf.tavu.values.*;

public record StoreIdentifierStatement(String name) implements Statement {
    @Override
    public Value execute(Context context) {
        final Value value = context.stack().pop();
        context.scope().put(name, value);
        return value;
    }

    @Override
    public String toString() {
        return "STORE_IDENT " + name;
    }
}
