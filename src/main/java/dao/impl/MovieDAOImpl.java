package dao.impl;

import common.Message;
import common.exception.DBException;
import dao.IMovieDAO;
import model.Movie;
import model.MovieCrew;
import config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MovieDAOImpl implements IMovieDAO {

    public void addMovie(Movie movie) {
        String query = "INSERT INTO movie (movie_title, movie_rating, movie_duration, movie_release_date, movie_description, created_by) " + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, movie.getMovieTitle());
            preparedStatement.setFloat(2, movie.getMovieRating());
            preparedStatement.setTime(3, movie.getSqlMovieDuration());
            preparedStatement.setDate(4, movie.getSqlMovieReleaseDate());
            preparedStatement.setString(5, movie.getMovieDescription());
            preparedStatement.setInt(6, movie.getCreatedBy());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int movieId = resultSet.getInt(1);
                addMovieLanguages(movieId, movie.getLanguageIds(), connection);
                addMovieGenres(movieId, movie.getGenreIds(), connection);
                addMovieFormats(movieId, movie.getFormatIds(), connection);
                addMovieCrew(movieId, movie.getMovieCrewEntries(), connection);
            } else {
                throw new SQLException("Failed to retrieve generated movie ID.");
            }
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException error) {
                throw new DBException(Message.Error.INTERNAL_ERROR, error);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    private void addMovieFormats(int movieId, List<Integer> formatIds, Connection connection) {
        String query = "INSERT INTO movie_formats (movie_id, format_id) VALUES (?, ?)";
        addMovieMetadata(movieId, formatIds, connection, query);
    }

    private void addMovieLanguages(int movieId, List<Integer> languageIds, Connection connection) {
        String query = "INSERT INTO movie_languages (movie_id, language_id) VALUES (?, ?)";
        addMovieMetadata(movieId, languageIds, connection, query);
    }

    private void addMovieGenres(int movieId, List<Integer> genreIds, Connection connection) {
        String query = "INSERT INTO movie_genres (movie_id, genre_id) VALUES (?, ?)";
        addMovieMetadata(movieId, genreIds, connection, query);
    }

    private static void addMovieMetadata(int movieId, List<Integer> ids, Connection connection, String query) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (int id : ids) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }

    private void addMovieCrew(int movieId, List<MovieCrew> crewEntries, Connection connection) {
        String query = "INSERT INTO movie_crew (movie_id, crew_id, designation_id, character_name) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (MovieCrew crew : crewEntries) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, crew.getCrewId());
                preparedStatement.setInt(3, crew.getDesignationId());
                preparedStatement.setString(4, crew.getCharacterName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }

}