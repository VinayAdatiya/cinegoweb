package com.cinego.service;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.dao.IPaymentMethodDAO;
import com.cinego.dao.impl.PaymentMethodDAOImpl;
import com.cinego.model.PaymentMethod;

import java.util.List;

public class PaymentMethodService {

    private final IPaymentMethodDAO paymentMethodDAO = new PaymentMethodDAOImpl();

    public List<PaymentMethod> getAllPaymentMethods() throws ApplicationException {
        List<PaymentMethod> paymentMethods = paymentMethodDAO.getAllPaymentMethods();
        if (paymentMethods == null || paymentMethods.isEmpty()) {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        } else {
            return paymentMethods;
        }
    }
}
