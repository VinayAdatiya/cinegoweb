package com.cinego.dao.impl;

import com.cinego.common.Message;
import com.cinego.common.exception.DBException;
import com.cinego.config.DBConnection;
import com.cinego.dao.*;
import com.cinego.model.Crew;
import com.cinego.model.CrewDesignation;
import com.cinego.model.Movie;
import com.cinego.model.MovieCrew;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDAOImpl implements IMovieDAO {
    private final ILanguageDAO languageDAO = new LanguageDAOImpl();
    private final IGenreDAO genreDAO = new GenreDAOImpl();
    private final IFormatDAO formatDAO = new FormatDAOImpl();
    private final ICrewDAO crewDAO = new CrewDAOImpl();
    private final ICrewDesignationDAO crewDesignationDAO = new CrewDesignationDAOImpl();

    @Override
    public void addMovie(Movie movie) throws DBException {
        String query = "INSERT INTO movie " +
                "(movie_title, movie_rating, movie_duration, movie_release_date, " +
                "movie_description, movie_poster_path , created_by) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            preparedStatement.setString(6, movie.getMoviePosterPath());
            preparedStatement.setInt(7, movie.getCreatedBy());
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

    @Override
    public Movie getMovieById(int movieId) throws DBException {
        String query = "SELECT * FROM movie WHERE movie_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet movieResult = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, movieId);
            movieResult = preparedStatement.executeQuery();
            if (movieResult.next()) {
                Movie movie = new Movie();
                movie.setMovieId(movieResult.getInt("movie_id"));
                movie.setMovieTitle(movieResult.getString("movie_title"));
                movie.setMovieRating(movieResult.getFloat("movie_rating"));
                movie.setMovieDuration(movieResult.getTime("movie_duration").toLocalTime());
                movie.setMovieReleaseDate(movieResult.getDate("movie_release_date").toLocalDate());
                movie.setMovieDescription(movieResult.getString("movie_description"));
                movie.setMoviePosterPath(movieResult.getString("movie_poster_path"));
                movie.setLanguages(languageDAO.getLanguagesByMovieId(movieId, connection));
                movie.setGenres(genreDAO.getGenresByMovieId(movieId, connection));
                movie.setFormats(formatDAO.getFormatsByMovieId(movieId, connection));
                movie.setMovieCrewEntries(getMovieCrew(movieId, connection));
                return movie;
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(movieResult, preparedStatement, connection);
        }
    }


    @Override
    public List<Movie> getAllMovies() throws DBException {
        String query = "SELECT * FROM movie";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<Movie> movies = new ArrayList<>();
            while (resultSet.next()) {
                Movie movie = new Movie();
                int movieId = resultSet.getInt("movie_id");
                movie.setMovieId(movieId);
                movie.setMovieTitle(resultSet.getString("movie_title"));
                movie.setMovieRating(resultSet.getFloat("movie_rating"));
                movie.setMovieDuration(resultSet.getTime("movie_duration").toLocalTime());
                movie.setMovieReleaseDate(resultSet.getDate("movie_release_date").toLocalDate());
                movie.setMovieDescription(resultSet.getString("movie_description"));
                movie.setMoviePosterPath(resultSet.getString("movie_poster_path"));
                movie.setLanguages(languageDAO.getLanguagesByMovieId(movieId, connection));
                movie.setGenres(genreDAO.getGenresByMovieId(movieId, connection));
                movie.setFormats(formatDAO.getFormatsByMovieId(movieId, connection));
                movie.setMovieCrewEntries(getMovieCrew(movieId, connection));
                movies.add(movie);
            }
            return movies;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    private List<MovieCrew> getMovieCrew(int movieId, Connection connection) throws DBException {
        List<MovieCrew> movieCrewList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet movieCrewSet = null;
        String query = "SELECT crew_id, designation_id, character_name FROM movie_crew WHERE movie_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, movieId);
            movieCrewSet = preparedStatement.executeQuery();
            while (movieCrewSet.next()) {
                int crewId = movieCrewSet.getInt("crew_id");
                int designationId = movieCrewSet.getInt("designation_id");
                String characterName = movieCrewSet.getString("character_name");
                Crew crew = crewDAO.getCrewById(crewId, connection);
                CrewDesignation crewDesignation = crewDesignationDAO.getDesignationById(designationId, connection);
                MovieCrew movieCrew = new MovieCrew();
                movieCrew.setCrew(crew);
                movieCrew.setCrewDesignation(crewDesignation);
                movieCrew.setCharacterName(characterName);
                movieCrewList.add(movieCrew);
            }
            return movieCrewList;
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(movieCrewSet, preparedStatement, null);
        }
    }

    @Override
    public void updateMovie(Movie movie) throws DBException {
        String query = "UPDATE movie " +
                "SET movie_title = ?, movie_rating = ?, movie_duration = ?, movie_release_date = ?, " +
                "movie_description = ? , movie_poster_path = ? , updated_by = ? " +
                "WHERE movie_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, movie.getMovieTitle());
            preparedStatement.setFloat(2, movie.getMovieRating());
            preparedStatement.setTime(3, movie.getSqlMovieDuration());
            preparedStatement.setDate(4, movie.getSqlMovieReleaseDate());
            preparedStatement.setString(5, movie.getMovieDescription());
            preparedStatement.setString(6, movie.getMoviePosterPath());
            preparedStatement.setInt(7, movie.getUpdatedBy());
            preparedStatement.setInt(8, movie.getMovieId());
            preparedStatement.executeUpdate();
            deleteMovieMetadata(movie.getMovieId(), connection);
            addMovieLanguages(movie.getMovieId(), movie.getLanguageIds(), connection);
            addMovieGenres(movie.getMovieId(), movie.getGenreIds(), connection);
            addMovieFormats(movie.getMovieId(), movie.getFormatIds(), connection);
            addMovieCrew(movie.getMovieId(), movie.getMovieCrewEntries(), connection);
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

    private void deleteMovieMetadata(int movieId, Connection connection) throws DBException {
        String[] queries = {
                "DELETE FROM movie_languages WHERE movie_id = ?",
                "DELETE FROM movie_genres WHERE movie_id = ?",
                "DELETE FROM movie_formats WHERE movie_id = ?",
                "DELETE FROM movie_crew WHERE movie_id = ?"
        };

        PreparedStatement preparedStatement = null;
        try {
            for (String query : queries) {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, movieId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }

    @Override
    public void deleteMovie(int movieId) throws DBException {
        String query = "DELETE FROM movie WHERE movie_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, movieId);
            deleteMovieMetadata(movieId, connection);
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
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (int id : ids) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }

    private void addMovieCrew(int movieId, List<MovieCrew> crewEntries, Connection connection) {
        String query = "INSERT INTO movie_crew (movie_id, crew_id, designation_id, character_name) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (MovieCrew movieCrew : crewEntries) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, movieCrew.getCrew().getCrewId());
                preparedStatement.setInt(3, movieCrew.getCrewDesignation().getDesignationId());
                preparedStatement.setString(4, movieCrew.getCharacterName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }
}