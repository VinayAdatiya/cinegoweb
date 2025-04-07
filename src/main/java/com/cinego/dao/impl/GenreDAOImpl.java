package com.cinego.dao.impl;

import com.cinego.config.DBConnection;
import com.cinego.model.Genre;
import com.cinego.common.Message;
import com.cinego.common.exception.DBException;
import com.cinego.dao.IGenreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements IGenreDAO {
    @Override
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

    @Override
    public List<Genre> getGenresByMovieId(int movieId, Connection connection) throws DBException {
        List<Genre> genres = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet movieGenresSet = null;
        String query = "SELECT g.genre_id, g.genre_name " +
                "FROM genres g " +
                "JOIN movie_genres mg " +
                "ON g.genre_id = mg.genre_id " +
                "WHERE mg.movie_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, movieId);
            movieGenresSet = preparedStatement.executeQuery();
            while (movieGenresSet.next()) {
                Genre genre = new Genre();
                genre.setGenreId(movieGenresSet.getInt("genre_id"));
                genre.setGenreName(movieGenresSet.getString("genre_name"));
                genres.add(genre);
            }
            return genres;
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(movieGenresSet, preparedStatement, null);
        }
    }
}
