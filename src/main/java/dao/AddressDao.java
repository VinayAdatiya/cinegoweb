package dao;

import common.Message;
import common.exception.ApplicationException;
import entity.Address;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDao {
    public static int insertAddress(Address address,Connection connection) {
        int addressId = 0;
        String query = "INSERT INTO address (address_line,address_line2,pincode,city_id) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, address.getAddressLine1());
            preparedStatement.setString(2, address.getAddressLine2());
            preparedStatement.setInt(3, address.getPincode());
            preparedStatement.setInt(4, address.getCity().getCityId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    addressId = rs.getInt(1);
                    // System.out.println(addressId);
                }
            }
        } catch (SQLException e) {
            throw new ApplicationException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(rs,preparedStatement,null);
        }
        return addressId;
    }
}
