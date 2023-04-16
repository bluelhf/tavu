package blue.lhf.tavu.structure;

import blue.lhf.tavu.*;
import blue.lhf.tavu.values.*;

import java.util.*;

import static blue.lhf.tavu.values.FunctionValue.ofNative;

public final class Context {
    public static final Map<String, Value> STANDARD_LIBRARY = Map.of(
        "println", ofNative(StandardLibrary::println),
        "print", ofNative(StandardLibrary::print),
        "sleep", ofNative(StandardLibrary::sleep),
        "readline", ofNative(StandardLibrary::readline)
    );

    private final Deque<Value> stack;
    private Scope scope;

    public Context(Deque<Value> stack, Scope scope) {
        this.stack = stack;
        this.scope = scope;
    }

    public static Context empty() {
        return new Context(new ArrayDeque<>(), Scope.root(STANDARD_LIBRARY));
    }

    public String log() {
        return scope().log();
    }

    public Deque<Value> stack() {
        return stack;
    }

    public Scope scope() {
        return scope;
    }

    public void pushScope() {
        scope = new Scope(scope, new HashMap<>());
    }

    public void popScope() {
        scope = scope.parent();
    }
}
