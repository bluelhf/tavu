# Tavu
Tavu is a basic interpreted programming language in Java.

It's lexed with regular expressions, and its AST and code generation are done
by hand.

The application entry point at `blue.lhf.tavu.Tavu` contains example driver code.

## Syntax

### Literals

Tavu supports numeric and string literals. Any valid input to `java.lang.Double#parseDouble` is also a valid Tavu literal.
This means `NaN`, `Infinity`, and `0XCAFE.BABEp-3f` are all valid numeric literals. As for strings, Tavu supports basic escape
sequences like `\\`, `\n`, `\t`, `\r`, `\b`, and so on, as well as unicode characters with `\uCAFE`. 

### Structure

Tavu files consist of a sequence of expressions separated by semicolons. Function calls, declarations, blocks, and block-local
variable assignment are supported. Functions are declared with `() { <code here> }` and invoked with `<function>()`. User-defined
functions do not yet support arguments, though up to one argument can be accepted by built-in functions.

#### Blocks

Blocks, like Tavu files, consist of a sequence of expressions separated by semicolons. The last expression in the block is used as the
return value, and does not end in a semicolon. For example, this is a valid assignment:
```
foo = {
    println("hello, world!");
    sleep(100);
    "foo"
};
```
Here, the `foo` identifier is assigned the value of `"foo"`, but not before `"hello, world!"` has been printed to standard output and the program has slept for 100 milliseconds.

#### Functions

Functions are defined identically to blocks, except with a preceding oval `()`:
```
foo = () {
    println("hello, world!");
    sleep(100);
    "foo"
};
```

Here, the `foo` identifier is a function that, when invoked with `foo();`, prints `"hello, world!"` to the standard output, sleeps for 100 milliseconds, and returns `"foo"`.

### Operations

| Symbol | Left type | Right type | Use |
| - | - | - | - |
| + | Number | Number | Adds the numbers. |
| * | Number | Number | Multiplies the numbers. |
| * | String | Number | Repeats the string `Number` times. |
| = | Identifier | Anything | Variable assignment: assigns the value of `Anything` to `Identifier`. |

### Standard Library

| Function | Argument | Action |
| - | - | - |
| `println`  | String | Prints the `String` and a new line to the standard output. |
| `print`    | String | Prints the `String` to the standard output. |
| `sleep`    | Number | Pauses the program for `Number` milliseconds. |
| `readline` | None   | Reads one line of input from the standard input. |



Example:
```py
print("yay it works\n" * 10);
println("Hello there, " + "General Kenobi");
```
