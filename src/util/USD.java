package util;

public class USD {

    private double doubleValue;
    private String stringValue;

    public USD(double doubleValue) {
        this.doubleValue = doubleValue;
        this.stringValue = Formatter.USDFormatter(doubleValue);
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public USD setStringValue(String stringValue) {
        this.stringValue = stringValue;
        return this;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
