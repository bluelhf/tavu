package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.*;
import blue.lhf.tavu.values.*;

public class PopScopeStatement implements Statement {
    @Override
    public Value execute(Context context) {
        context.popScope();
        return null;
    }

    public String toString() {
        return "POP_SCOPE";
    }
}
