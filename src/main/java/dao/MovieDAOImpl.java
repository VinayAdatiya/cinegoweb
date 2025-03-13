package dao;

import common.Message;
import common.Role;
import common.exception.DBException;
import model.Movie;
import model.MovieCrew;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MovieDAOImpl implements IMovieDAO {

    public int addMovie(Movie movie) throws DBException, SQLException {
        String query = "INSERT INTO movie (movie_title, movie_rating, movie_duration, movie_release_date, movie_description, created_by) " + "VALUES (?, ?, ?, ?, ?, ?)";
        int generatedId = -1;
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet generatedKeys;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
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
                generatedId = generatedKeys.getInt(1);
            } else {
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
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            connection.rollback();
            e.printStackTrace();
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
        return generatedId;
    }

    private void addMovieFormats(int movieId, List<Integer> formatIds, Connection connection) throws DBException {
        String query = "INSERT INTO movie_formats (movie_id, format_id) VALUES (?, ?)";
        addMovieMetadata(movieId, formatIds, connection, query);
    }

    private void addMovieLanguages(int movieId, List<Integer> languageIds, Connection connection) throws DBException {
        String query = "INSERT INTO movie_languages (movie_id, language_id) VALUES (?, ?)";
        addMovieMetadata(movieId, languageIds, connection, query);
    }

    private void addMovieGenres(int movieId, List<Integer> genreIds, Connection connection) throws DBException {
        String query = "INSERT INTO movie_genres (movie_id, genre_id) VALUES (?, ?)";
        addMovieMetadata(movieId, genreIds, connection, query);
    }

    private static void addMovieMetadata(int movieId, List<Integer> languageIds, Connection connection, String query) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (int languageId : languageIds) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, languageId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }

    private void addMovieCrew(int movieId, List<MovieCrew> crewEntries, Connection connection) throws DBException {
        String query = "INSERT INTO movie_crew (movie_id, crew_id, designation_id, character_name) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            for (MovieCrew entry : crewEntries) {
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