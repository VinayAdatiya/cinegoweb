package dao.impl;

import dao.IPaymentMethodDAO;
import model.PaymentMethod;

public class PaymentMethodDAOImpl implements IPaymentMethodDAO {
    public PaymentMethod getPaymentMethodById(int payment_method_id) {
        return new PaymentMethod();
    }
}
