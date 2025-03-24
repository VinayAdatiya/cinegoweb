package dao;

import common.exception.DBException;
import model.Address;
import java.sql.Connection;

public interface IAddressDAO {
    int insertAddress(Address address, Connection connection);

    void updateAddress(Address address,Connection connection) throws DBException;
}
