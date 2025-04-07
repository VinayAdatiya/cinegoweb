package com.cinego.dao;

import com.cinego.common.exception.DBException;
import com.cinego.model.Address;

import java.sql.Connection;

public interface IAddressDAO {
    int insertAddress(Address address, Connection connection);

    void updateAddress(Address address,Connection connection) throws DBException;
}
