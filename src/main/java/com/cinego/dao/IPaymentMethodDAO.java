package com.cinego.dao;

import com.cinego.common.exception.DBException;
import com.cinego.model.PaymentMethod;

import java.util.List;

public interface IPaymentMethodDAO {
    PaymentMethod getPaymentMethodById(int payment_method_id);

    List<PaymentMethod> getAllPaymentMethods() throws DBException;
}
