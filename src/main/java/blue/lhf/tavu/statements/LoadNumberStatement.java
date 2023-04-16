package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.Context;
import blue.lhf.tavu.values.*;

public record LoadNumberStatement(Number value) implements Statement {
    @Override
    public Value execute(Context context) {
        return new NumberValue(value.doubleValue());
    }

    @Override
    public String toString() {
        return "LOAD_NUM " + value;
    }
}
