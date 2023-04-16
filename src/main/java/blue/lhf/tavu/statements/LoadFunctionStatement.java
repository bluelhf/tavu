package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.*;
import blue.lhf.tavu.values.*;

public record LoadFunctionStatement(Code code) implements Statement {
    @Override
    public Value execute(Context context) {
        return new FunctionValue(code);
    }

    @Override
    public String toString() {
        return "LOAD_FUNCTION\n" + code.log().indent(2).stripTrailing();
    }
}
