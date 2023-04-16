package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.*;
import blue.lhf.tavu.values.*;

public class StackClearStatement implements Statement {
    @Override
    public Value execute(Context context) {
        context.stack().clear();
        return null;
    }

    @Override
    public String toString() {
        return "STACK_CLEAR";
    }
}
