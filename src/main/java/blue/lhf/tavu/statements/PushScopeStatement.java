package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.*;
import blue.lhf.tavu.values.*;

public class PushScopeStatement implements Statement {
    @Override
    public Value execute(Context context) {
        context.pushScope();
        return null;
    }

    public String toString() {
        return "PUSH_SCOPE";
    }
}
