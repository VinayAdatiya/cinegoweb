package dao;

import common.exception.DBException;
import model.PaymentMethod;

import java.util.List;

public interface IPaymentMethodDAO {
    PaymentMethod getPaymentMethodById(int payment_method_id);

    List<PaymentMethod> getAllPaymentMethods() throws DBException;
}
