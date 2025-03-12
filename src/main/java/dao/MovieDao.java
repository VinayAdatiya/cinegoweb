package dao;

import common.Message;
import common.Role;
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
        String query = "INSERT INTO movie (movie_title, movie_rating, movie_duration, movie_release_date, movie_description, created_by) " + "VALUES (?, ?, ?, ?, ?, ?)";
        int generatedId = -1;
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet generatedKeys;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, movie.getMovieTitle());
            preparedStatement.setFloat(2, movie.getMovieRating());
            preparedStatement.setTime(3, movie.getSqlMovieDuration());
            preparedStatement.setDate(4, movie.getSqlMovieReleaseDate());
            preparedStatement.setString(5, movie.getMovieDescription());
            preparedStatement.setInt(6, Role.ROLE_SUPER_ADMIN.getRoleId());
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.out.println("Key Generated Successfully");
                generatedId = generatedKeys.getInt(1);
            } else {
                System.out.println("Error is here");
                throw new SQLException("Failed to retrieve generated movie ID.");
            }
            if (generatedId > 0) {
                if (movie.getLanguageIds() != null && !movie.getLanguageIds().isEmpty()) {
                    addMovieLanguages(generatedId, movie.getLanguageIds(), connection);
                }
                if (movie.getGenreIds() != null && !movie.getGenreIds().isEmpty()) {
                    addMovieGenres(generatedId, movie.getGenreIds(), connection);
                }
                if (movie.getFormatIds() != null && !movie.getFormatIds().isEmpty()) {
                    addMovieFormats(generatedId, movie.getFormatIds(), connection);
                }
                if (movie.getMovieCrewEntries() != null && !movie.getMovieCrewEntries().isEmpty()) {
                    addMovieCrew(generatedId, movie.getMovieCrewEntries(), connection);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
        return generatedId;
    }

    public static void addMovieFormats(int movieId, List<Integer> formatIds, Connection connection) throws DBException {
        String query = "INSERT INTO movie_formats (movie_id, format_id) VALUES (?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (int formatId : formatIds) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, formatId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }

    public static void addMovieLanguages(int movieId, List<Integer> languageIds, Connection connection) throws DBException {
        String query = "INSERT INTO movie_languages (movie_id, language_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int languageId : languageIds) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, languageId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }

    public static void addMovieGenres(int movieId, List<Integer> genreIds, Connection connection) throws DBException {
        String query = "INSERT INTO movie_genres (movie_id, genre_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int genreId : genreIds) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, genreId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }

    public static void addMovieCrew(int movieId, List<MovieCrewEntry> crewEntries, Connection connection) throws DBException {
        String query = "INSERT INTO movie_crew (movie_id, crew_id, designation_id, character_name) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (MovieCrewEntry entry : crewEntries) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, entry.getCrewId());
                preparedStatement.setInt(3, entry.getDesignationId());
                preparedStatement.setString(4, entry.getCharacterName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }

}