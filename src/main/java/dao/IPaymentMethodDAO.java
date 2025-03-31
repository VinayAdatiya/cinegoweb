package dao;

import model.PaymentMethod;

public interface IPaymentMethodDAO {
    PaymentMethod getPaymentMethodById(int payment_method_id);
}
