package dao;

import common.Message;
import common.exception.DBException;
import entity.Movie;
import entity.MovieCrewEntry;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MovieDao {

    public static int addMovie(Movie movie) throws DBException {
        String query = "INSERT INTO movie (movie_title, movie_rating, movie_duration, movie_release_date, movie_description, created_by) VALUES (?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection connection = DBConnection.INSTANCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, movie.getMovieTitle());
            preparedStatement.setDouble(2, movie.getMovieRating());
            preparedStatement.setTime(3, java.sql.Time.valueOf(movie.getMovieDuration()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(movie.getMovieReleaseDate()));
            preparedStatement.setString(5, movie.getMovieDescription());
            preparedStatement.setInt(6, movie.getCreatedBy());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve generated movie ID.");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
        return generatedId;
    }

    public static void addMovieFormats(int movieId, List<Integer> formatIds, Connection connection) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO movie_formats (movie_id, format_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int formatId : formatIds) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, formatId);
                preparedStatement.executeUpdate();
            }
        }
    }

    public static void addMovieFormats(int movieId, List<Integer> formatIds, Connection connection) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO movie_formats (movie_id, format_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int formatId : formatIds) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, formatId);
                preparedStatement.executeUpdate();
            }
        }
    }

    public static void addMovieLanguages(int movieId, List<Integer> languageIds, Connection connection) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO movie_languages (movie_id, language_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int languageId : languageIds) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, languageId);
                preparedStatement.executeUpdate();
            }
        }
    }

    public static void addMovieGenres(int movieId, List<Integer> genreIds, Connection connection) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO movie_genres (movie_id, genre_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int genreId : genreIds) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, genreId);
                preparedStatement.executeUpdate();
            }
        }
    }

    public static void addMovieCrew(int movieId, List<MovieCrewEntry> movieCrewEntries) {

    }
}