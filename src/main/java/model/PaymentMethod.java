package model;

public class PaymentMethod {
    private int paymentMethodId;
    private String paymentMethod;

    public PaymentMethod() {
    }

    public PaymentMethod(int paymentMethodId, String paymentMethod) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
