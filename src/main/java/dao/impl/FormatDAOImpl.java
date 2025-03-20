package dao.impl;

import common.Message;
import common.exception.DBException;
import dao.IFormatDAO;
import model.Format;
import config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormatDAOImpl implements IFormatDAO {
    public List<Format> getAllFormats() throws DBException {
        List<Format> formats = new ArrayList<>();
        String query = "SELECT format_id , format_name FROM formats";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                formats.add(new Format(rs.getInt("format_id"), rs.getString("format_name")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(rs, preparedStatement, connection);
        }
        return formats;
    }

    @Override
    public List<Format> getFormatsByMovieId(int movieId, Connection connection) throws DBException {
        List<Format> formats = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet movieFormatsSet = null;
        String query = "SELECT f.format_id, f.format_name " +
                "FROM formats f " +
                "JOIN movie_formats mf " +
                "ON f.format_id = mf.format_id " +
                "WHERE mf.movie_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, movieId);
            movieFormatsSet = preparedStatement.executeQuery();
            while (movieFormatsSet.next()) {
                Format format = new Format();
                format.setFormatId(movieFormatsSet.getInt("format_id"));
                format.setFormatName(movieFormatsSet.getString("format_name"));
                formats.add(format);
            }
            return formats;
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(movieFormatsSet, preparedStatement, null);
        }
    }
}
