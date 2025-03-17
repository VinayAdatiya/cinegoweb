package dao;

import model.Address;
import java.sql.Connection;

public interface IAddressDAO {
    int insertAddress(Address address, Connection connection);
}
