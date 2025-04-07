package com.cinego.dao.impl;

import com.cinego.config.DBConnection;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.dao.IPaymentMethodDAO;
import com.cinego.model.PaymentMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDAOImpl implements IPaymentMethodDAO {
    @Override
    public PaymentMethod getPaymentMethodById(int paymentMethodId) {
        String query = "SELECT * FROM payment_methods WHERE payment_method_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet paymentMethodResult = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, paymentMethodId);
            paymentMethodResult = preparedStatement.executeQuery();
            PaymentMethod paymentMethod = new PaymentMethod();
            if (paymentMethodResult.next()) {
                paymentMethod.setPaymentMethodId(paymentMethodId);
                paymentMethod.setPaymentMethod(paymentMethodResult.getString("payment_method"));
            } else {
                throw new ApplicationException(Message.Error.PAYMENT_METHOD_NOT_FOUND);
            }
            return paymentMethod;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(paymentMethodResult, preparedStatement, connection);
        }
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() throws DBException {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        String query = "SELECT * FROM payment_methods";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setPaymentMethodId(rs.getInt("payment_method_id"));
                paymentMethod.setPaymentMethod(rs.getString("payment_method"));
                paymentMethods.add(paymentMethod);
            }
            return paymentMethods;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR);
        } finally {
            DBConnection.closeResources(rs, preparedStatement, connection);
        }
    }
}
