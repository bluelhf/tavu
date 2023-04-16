package blue.lhf.tavu.structure;

import blue.lhf.tavu.statements.Statement;
import blue.lhf.tavu.values.Value;

import java.util.*;
import java.util.stream.Collectors;

public record Code(List<Statement> statements) {

    public static Code of(final Statement... statements) {
        return new Code(Arrays.asList(statements));
    }

    public Deque<Value> evaluate(final Context context) {
        for (final Statement statement : statements()) {
            final Value result = statement.execute(context);
            if (result != null) {
                context.stack().push(result);
            }
        }

        return context.stack();
    }

    public String log() {
        return statements.stream()
                .map(Statement::toString)
                .collect(Collectors.joining("\n"));
    }
}
