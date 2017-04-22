package util;

public class USD {

    private int numericalValue;
    private String stringValue;

    public USD(int numericalValue) {
        this.numericalValue = numericalValue;
        this.stringValue = Formatter.USDFormatter(numericalValue);
    }

    public int getNumericalValue() {
        return numericalValue;
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
