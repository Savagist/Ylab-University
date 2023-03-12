package homework.second.complexnumbers;

public class ComplexNumbers {
    private double realPart;
    private double imaginaryPart;

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
        return Math.sqrt((this.realPart * this.realPart) + (this.imaginaryPart * this.imaginaryPart));
    }

    public String toString() {
        if (this.imaginaryPart == 0) {
            return this.realPart + "";
        } else if (this.realPart == 0) {
            return this.imaginaryPart + "i";
        } else if (this.imaginaryPart < 0) {
            return this.realPart + " - " + -this.imaginaryPart + "i";
        } else {
            return this.realPart + " + " + this.imaginaryPart + "i";
        }
    }
}