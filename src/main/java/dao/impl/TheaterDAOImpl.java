package dao.impl;

import common.Message;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.IAddressDAO;
import dao.ITheaterDAO;
import dao.IUserDAO;
import model.*;
import config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TheaterDAOImpl implements ITheaterDAO {
    IUserDAO userDAO = new UserDAOImpl();
    IAddressDAO addressDAO = new AddressDAOImpl();

    public void addTheater(Theater theater) throws ApplicationException {
        String query = "INSERT INTO " +
                "theater (theater_admin, theater_name, theater_rating, address_id, created_by, updated_by) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            // Fetch Theater Admin
            User theaterAdmin = userDAO.authenticateUser(theater.getTheaterAdmin().getEmail(), theater.getTheaterAdmin().getPassword());
            if (theaterAdmin.getRole().getRoleId() != 2) {
                throw new ApplicationException(Message.Error.THEATER_ADMIN_NOT_FOUND);
            }
            theater.setTheaterAdmin(theaterAdmin);
            // Insert theaterAddress
            int addressId = addressDAO.insertAddress(theater.getTheaterAddress(), connection);
            theater.getTheaterAddress().setAddressId(addressId);
            // Add theater
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // Get generated keys
            preparedStatement.setInt(1, theater.getTheaterAdmin().getUserId());
            preparedStatement.setString(2, theater.getTheaterName());
            preparedStatement.setObject(3, theater.getTheaterRating());
            preparedStatement.setInt(4, theater.getTheaterAddress().getAddressId());
            preparedStatement.setInt(5, theater.getCreatedBy());
            preparedStatement.setInt(6, theater.getUpdatedBy());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException error) {
                throw new DBException(Message.Error.INTERNAL_ERROR, error);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }

    @Override
    public Theater getTheaterById(int theaterId) throws DBException {
        String query = "SELECT " +
                "t.theater_id, t.theater_name, t.theater_rating," +
                "u.user_id, u.username, u.email, u.first_name, u.last_name, u.role_id, u.phone_number, " +
                "a.address_id, a.address_line, a.address_line2, a.pincode, " +
                "c.city_id, c.city_name, c.state_code, " +
                "s.state_name " +
                "FROM theater t " +
                "LEFT JOIN users u ON t.theater_admin = u.user_id " +
                "LEFT JOIN address a ON t.address_id = a.address_id " +
                "LEFT JOIN city c ON a.city_id = c.city_id " +
                "LEFT JOIN state s ON c.state_code = s.state_code " +
                "WHERE t.theater_id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, theaterId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Theater theater = new Theater();
                theater.setTheaterId(resultSet.getInt("theater_id"));
                theater.setTheaterName(resultSet.getString("theater_name"));
                theater.setTheaterRating(resultSet.getDouble("theater_rating"));

                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setRole(Role.getRole(resultSet.getInt("role_id")));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                theater.setTheaterAdmin(user);

                Address address = new Address();
                address.setAddressId(resultSet.getInt("address_id"));
                address.setAddressLine1(resultSet.getString("address_line"));
                address.setAddressLine2(resultSet.getString("address_line2"));
                address.setPincode(resultSet.getInt("pincode"));

                City city = new City();
                city.setCityId(resultSet.getInt("city_id"));
                city.setCityName(resultSet.getString("city_name"));

                State state = new State();
                state.setStateCode(resultSet.getString("state_code"));
                state.setStateName(resultSet.getString("state_name"));

                city.setState(state);
                address.setCity(city);
                theater.setTheaterAddress(address);

                return theater;
            }
            return null;

        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public List<Theater> getAllTheaters() throws DBException {
        String query = "SELECT " +
                "t.theater_id, t.theater_name, t.theater_rating," +
                "u.user_id, u.username, u.email, u.first_name, u.last_name, u.role_id, u.phone_number, " +
                "a.address_id, a.address_line, a.address_line2, a.pincode, " +
                "c.city_id, c.city_name, c.state_code, " +
                "s.state_name " +
                "FROM theater t " +
                "LEFT JOIN users u ON t.theater_admin = u.user_id " +
                "LEFT JOIN address a ON t.address_id = a.address_id " +
                "LEFT JOIN city c ON a.city_id = c.city_id " +
                "LEFT JOIN state s ON c.state_code = s.state_code ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<Theater> theaterList = new ArrayList<>();
            while (resultSet.next()) {
                Theater theater = new Theater();
                theater.setTheaterId(resultSet.getInt("theater_id"));
                theater.setTheaterName(resultSet.getString("theater_name"));
                theater.setTheaterRating(resultSet.getDouble("theater_rating"));

                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setRole(Role.getRole(resultSet.getInt("role_id")));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                theater.setTheaterAdmin(user);

                Address address = new Address();
                address.setAddressId(resultSet.getInt("address_id"));
                address.setAddressLine1(resultSet.getString("address_line"));
                address.setAddressLine2(resultSet.getString("address_line2"));
                address.setPincode(resultSet.getInt("pincode"));

                City city = new City();
                city.setCityId(resultSet.getInt("city_id"));
                city.setCityName(resultSet.getString("city_name"));

                State state = new State();
                state.setStateCode(resultSet.getString("state_code"));
                state.setStateName(resultSet.getString("state_name"));

                city.setState(state);
                address.setCity(city);
                theater.setTheaterAddress(address);
                theaterList.add(theater);
            }
            return theaterList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void updateTheater(Theater theater) throws DBException {
        String query = "UPDATE theater " +
                "SET theater_name = ?, theater_rating = ?, updated_by = ? " +
                "WHERE theater_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            addressDAO.updateAddress(theater.getTheaterAddress(), connection);
            preparedStatement.setString(1, theater.getTheaterName());
            preparedStatement.setObject(2, theater.getTheaterRating());
            preparedStatement.setInt(3, theater.getUpdatedBy());
            preparedStatement.setInt(4, theater.getTheaterId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException error) {
                throw new DBException(Message.Error.INTERNAL_ERROR, error);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }

    @Override
    public void deleteTheater(int theaterId) throws DBException {
        String query = "DELETE FROM theater WHERE theater_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, theaterId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }
}