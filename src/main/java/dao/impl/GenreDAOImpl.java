package dao.impl;

import common.Message;
import common.exception.DBException;
import dao.IGenreDAO;
import model.Genre;
import config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements IGenreDAO {
    public List<Genre> getAllGenres() throws DBException {
        List<Genre> genres = new ArrayList<>();
        String query = "SELECT genre_id , genre_name FROM genres";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                genres.add(new Genre(rs.getInt("genre_id"), rs.getString("genre_name")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR);
        } finally {
            DBConnection.closeResources(rs, preparedStatement, connection);
        }
        return genres;
    }
}
