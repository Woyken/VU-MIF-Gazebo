package lt.vu.mif.Payments;

public class PaymentError {

    public String error;
    public String message;

    PaymentError(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
