package blue.lhf.tavu.statements;

import blue.lhf.tavu.structure.Context;
import blue.lhf.tavu.values.Value;

@FunctionalInterface
public interface Statement {
    Value execute(final Context context);
}
