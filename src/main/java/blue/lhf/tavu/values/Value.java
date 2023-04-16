package blue.lhf.tavu.values;

public interface Value {
    Value add(Value other);
    Value multiply(Value other);

    String asString();
}
