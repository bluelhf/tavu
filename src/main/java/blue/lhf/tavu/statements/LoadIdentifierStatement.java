package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.Context;
import blue.lhf.tavu.values.Value;

public record LoadIdentifierStatement(String name) implements Statement {
    @Override
    public Value execute(Context context) {
        return context.scope().get(name());
    }

    @Override
    public String toString() {
        return "LOAD_IDENT " + name();
    }
}
