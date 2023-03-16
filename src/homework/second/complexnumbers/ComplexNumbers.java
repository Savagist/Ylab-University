package homework.second.complexnumbers;

public class ComplexNumbers {
    private final double realPart;
    private final double imaginaryPart;

    public ComplexNumbers(double realPart) {
        this.realPart = realPart;
        this.imaginaryPart = 0;
    }

    public ComplexNumbers(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public ComplexNumbers sum(ComplexNumbers other) {
        double newRealPart = this.realPart + other.realPart;
        double newImaginaryPart = this.imaginaryPart + other.imaginaryPart;
        return new ComplexNumbers(newRealPart, newImaginaryPart);
    }

    public ComplexNumbers sub(ComplexNumbers other) {
        double newRealPart = this.realPart - other.realPart;
        double newImaginaryPart = this.imaginaryPart - other.imaginaryPart;
        return new ComplexNumbers(newRealPart, newImaginaryPart);
    }

    public ComplexNumbers mul(ComplexNumbers other) {
        double newRealPart = (this.realPart * other.realPart) - (this.imaginaryPart * other.imaginaryPart);
        double newImaginaryPart = (this.realPart * other.imaginaryPart) + (this.imaginaryPart * other.realPart);
        return new ComplexNumbers(newRealPart, newImaginaryPart);
    }

    public double getModule() {
        return Math.sqrt((realPart * realPart) + (imaginaryPart * imaginaryPart));
    }

    public String toString() {
        if (imaginaryPart == 0) {
            return realPart + "";
        } else if (realPart == 0) {
            return imaginaryPart + "i";
        } else if (imaginaryPart < 0) {
            return realPart + " - " + -imaginaryPart + "i";
        } else {
            return realPart + " + " + imaginaryPart + "i";
        }
    }
}