package blue.lhf.tavu.values;

public record NumberValue(double val) implements Value {
    @Override
    public Value add(Value other) {
        if (other instanceof NumberValue num) {
            return new NumberValue(val + num.val);
        } else {
            throw new IllegalArgumentException("Cannot add " + other + " to a number");
        }
    }

    @Override
    public Value multiply(Value other) {
        if (other instanceof NumberValue num) {
            return new NumberValue(val * num.val);
        } else if (other instanceof StringValue str) {
            return new StringValue(str.val().repeat((int) val));
        } else {
            throw new IllegalArgumentException("Cannot multiply " + other + " by a number");
        }
    }

    @Override
    public String asString() {
        return Double.toString(val);
    }
}
