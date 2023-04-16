package blue.lhf.tavu;

import blue.lhf.tavu.lexing.*;
import blue.lhf.tavu.parsing.*;
import blue.lhf.tavu.structure.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Tavu {
    public static void eval(final String input) {
        System.err.println("Source:");
        System.err.println(input.indent(2));

        final List<Token<TokenType, ?>> tokens = new Tokeniser<>(TokenType.class).tokenise(input);
        final Expression tree = new Parser(tokens).parse();


        final Code code = tree.generate();
        final Context context = Context.empty();
        System.err.println("Code:");
        System.err.println(code.log().indent(2));
        System.err.println(context.log());
        code.evaluate(context);
    }
    private static String getFile() throws IOException {
        final InputStream stream = Tavu.class.getResourceAsStream("/test.tavu");
        if (stream == null) throw new NoSuchFileException("Resource not found");
        final String content = new String(stream.readAllBytes());
        stream.close();
        return content;
    }

    public static void main(String[] args) throws IOException {
        eval(getFile());
    }
}
