# Tavu
Tavu is an extremely basic interpreted programming language in Java.

It's lexed with regular expressions, and its AST and code generation are done
by hand.

The application entry point at `blue.lhf.tavu.Tavu` contains example driver code.

## Syntax

Tavu supports numbers, text, and single-argument function invocations. Its interpreter
is stack-based, and it supports a single global scope (it's also syntactically impossible
to define a new scope).

Tavu files consist of a sequence of expressions separated by semicolons.

Example:
```py
print("yay it works\n" * 10);
println("Hello there, " + "General Kenobi");
```