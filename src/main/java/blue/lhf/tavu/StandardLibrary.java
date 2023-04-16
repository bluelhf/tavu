package blue.lhf.tavu;

import blue.lhf.tavu.structure.*;
import blue.lhf.tavu.values.*;

import java.util.*;
import java.util.concurrent.locks.*;

public class StandardLibrary {

    private StandardLibrary() {
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static Value readline(final Context context) {
        return new StringValue(scanner.nextLine());
    }

    public static void println(Context context) {
        print(context);
        System.out.println();
    }

    public static void print(final Context context) {
        if (context.stack().isEmpty()) {
            System.out.print("<none>");
        } else {
            System.out.print(context.stack().pop().asString());
        }

    }

    public static void sleep(final Context context) {
        if (context.stack().isEmpty()) {
            return;
        }

        final Value value = context.stack().pop();
        if (value instanceof NumberValue num) {
            LockSupport.parkNanos((long) (num.val() * 1E9));
        } else {
            throw new IllegalArgumentException("Cannot sleep for non-number amount of time");
        }
    }
}
