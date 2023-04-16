package blue.lhf.tavu.values;

public record StringValue(String val) implements Value {
    @Override
    public Value add(Value other) {
        return new StringValue(val + other.asString());
    }

    @Override
    public Value multiply(Value other) {
        if (other instanceof NumberValue num) {
            return new StringValue(val.repeat((int) num.val()));
        } else {
            throw new IllegalArgumentException("Cannot multiply " + other + " by " + this);
        }
    }

    @Override
    public String asString() {
        return val;
    }
}
