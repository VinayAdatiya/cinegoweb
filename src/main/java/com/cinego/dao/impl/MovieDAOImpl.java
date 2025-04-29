package com.cinego.dao.impl;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.config.DBConnection;
import com.cinego.config.JPAConfig;
import com.cinego.dao.*;
import com.cinego.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDAOImpl implements IMovieDAO {
    private final ILanguageDAO languageDAO = new LanguageDAOImpl();
    private final IGenreDAO genreDAO = new GenreDAOImpl();
    private final IFormatDAO formatDAO = new FormatDAOImpl();
    private final ICrewDAO crewDAO = new CrewDAOImpl();
    private final ICrewDesignationDAO crewDesignationDAO = new CrewDesignationDAOImpl();

    @Override
    public void addMovie(Movie movie) throws DBException {
        EntityManager em = JPAConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            processMovieCollections(em, movie);
            List<MovieCrew> movieCrewEntries = movie.getMovieCrewEntries();
            movie.setMovieCrewEntries(new ArrayList<>());
            em.persist(movie);
            processMovieCrew(em, movie, movieCrewEntries);
            transaction.commit();
        } catch (Exception e) {
            rollbackTransaction(transaction);
            throw new DBException(Message.Error.INTERNAL_ERROR);
        } finally {
            closeEntityManager(em);
        }
    }

    private void processMovieCollections(EntityManager em, Movie movie) {
        movie.setLanguages(handleLanguages(em, movie.getLanguages()));
        movie.setGenres(handleGenres(em, movie.getGenres()));
        movie.setFormats(handleFormats(em, movie.getFormats()));
    }

    private List<Language> handleLanguages(EntityManager em, List<Language> languages) {
        if (languages != null && !languages.isEmpty()) {
            return languages.stream()
                    .map(lang -> em.getReference(Language.class, lang.getLanguageId()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private List<Genre> handleGenres(EntityManager em, List<Genre> genres) {
        if (genres != null && !genres.isEmpty()) {
            return genres.stream()
                    .map(genre -> em.getReference(Genre.class, genre.getGenreId()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private List<Format> handleFormats(EntityManager em, List<Format> formats) {
        if (formats != null && !formats.isEmpty()) {
            return formats.stream()
                    .map(format -> em.getReference(Format.class, format.getFormatId()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private void processMovieCrew(EntityManager em, Movie movie, List<MovieCrew> movieCrewEntries) {
        if (movieCrewEntries != null && !movieCrewEntries.isEmpty()) {
            for (MovieCrew movieCrew : movieCrewEntries) {
                if (movieCrew.getCrew() != null && movieCrew.getCrew().getCrewId() != 0) {
                    movieCrew.setCrew(em.getReference(Crew.class, movieCrew.getCrew().getCrewId()));
                    movieCrew.setCrewId(movieCrew.getCrew().getCrewId());
                } else {
                    movieCrew.setCrew(null);
                }
                if (movieCrew.getCrewDesignation() != null && movieCrew.getCrewDesignation().getDesignationId() != 0) {
                    movieCrew.setCrewDesignation(em.getReference(CrewDesignation.class, movieCrew.getCrewDesignation().getDesignationId()));
                    movieCrew.setDesignationId(movieCrew.getCrewDesignation().getDesignationId());
                } else {
                    movieCrew.setCrewDesignation(null);
                }
                movieCrew.setMovie(movie);
                movieCrew.setMovieId(movie.getMovieId());
                em.persist(movieCrew);
            }
        }
    }

    @Override
    public Movie getMovieById(int movieId) throws DBException {
        try (EntityManager em = JPAConfig.getEntityManagerFactory().createEntityManager()) {
            String select_movie = "SELECT DISTINCT m FROM Movie m WHERE m.movieId = :movieIdParam";
            Movie movie = em.createQuery(select_movie, Movie.class).setParameter("movieIdParam", movieId).getSingleResult();

            String fetch_languages = "SELECT l FROM Movie m JOIN m.languages l WHERE m.movieId = :movieIdParam";
            List<Language> languageList = em.createQuery(fetch_languages, Language.class).setParameter("movieIdParam", movieId).getResultList();
            movie.setLanguages(languageList);

            String fetch_genres = "SELECT g FROM Movie m JOIN m.genres g WHERE m.movieId = :movieIdParam";
            List<Genre> genreList = em.createQuery(fetch_genres, Genre.class).setParameter("movieIdParam", movieId).getResultList();
            movie.setGenres(genreList);

            String fetch_formats = "SELECT f FROM Movie m JOIN m.formats f WHERE m.movieId = :movieIdParam";
            List<Format> formatList = em.createQuery(fetch_formats, Format.class).setParameter("movieIdParam", movieId).getResultList();
            movie.setFormats(formatList);

            String fetchMovieCrew = "SELECT mc FROM Movie m " +
                    "JOIN m.movieCrewEntries mc " +
                    "JOIN mc.crew " +
                    "JOIN mc.crewDesignation " +
                    "WHERE m.movieId = :movieIdParam";
            List<MovieCrew> movieCrewList = em.createQuery(fetchMovieCrew, MovieCrew.class).setParameter("movieIdParam", movieId).getResultList();
            movie.setMovieCrewEntries(movieCrewList);
            return movie;
        } catch (NoResultException nre) {
            throw new DBException(Message.Error.NO_RECORD_FOUND);
        } catch (Exception e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }

    @Override
    public List<Movie> getAllMovies() throws DBException {
        try (EntityManager em = JPAConfig.getEntityManagerFactory().createEntityManager()) {
            String selectAllMovies = "SELECT m FROM Movie m";
            List<Movie> movies = em.createQuery(selectAllMovies, Movie.class).getResultList();
            for (Movie movie : movies) {
                String fetch_languages = "SELECT l FROM Movie m JOIN m.languages l WHERE m.movieId = :movieIdParam";
                List<Language> languageList = em.createQuery(fetch_languages, Language.class)
                        .setParameter("movieIdParam", movie.getMovieId())
                        .getResultList();
                movie.setLanguages(languageList);

                String fetch_genres = "SELECT g FROM Movie m JOIN m.genres g WHERE m.movieId = :movieIdParam";
                List<Genre> genreList = em.createQuery(fetch_genres, Genre.class)
                        .setParameter("movieIdParam", movie.getMovieId())
                        .getResultList();
                movie.setGenres(genreList);

                String fetch_formats = "SELECT f FROM Movie m JOIN m.formats f WHERE m.movieId = :movieIdParam";
                List<Format> formatList = em.createQuery(fetch_formats, Format.class)
                        .setParameter("movieIdParam", movie.getMovieId())
                        .getResultList();
                movie.setFormats(formatList);

                String fetchMovieCrew = "SELECT mc FROM Movie m " +
                        "JOIN m.movieCrewEntries mc " +
                        "JOIN mc.crew " +
                        "JOIN mc.crewDesignation " +
                        "WHERE m.movieId = :movieIdParam";
                List<MovieCrew> movieCrewList = em.createQuery(fetchMovieCrew, MovieCrew.class)
                        .setParameter("movieIdParam", movie.getMovieId())
                        .getResultList();
                movie.setMovieCrewEntries(movieCrewList);
            }
            return movies;
        } catch (Exception e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void updateMovie(Movie movie) throws DBException {
        String query = "UPDATE movie " + "SET movie_title = ?, movie_rating = ?, movie_duration = ?, movie_release_date = ?, " + "movie_description = ? , movie_poster_path = ? , updated_by = ? " + "WHERE movie_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, movie.getMovieTitle());
            preparedStatement.setBigDecimal(2, movie.getMovieRating());
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
        String[] queries = {"DELETE FROM movie_languages WHERE movie_id = ?", "DELETE FROM movie_genres WHERE movie_id = ?", "DELETE FROM movie_formats WHERE movie_id = ?", "DELETE FROM movie_crew WHERE movie_id = ?"};

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
    public void deleteMovie(int movieId) throws ApplicationException {
        EntityManager em = JPAConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Movie movie = em.find(Movie.class, movieId);
            if (movie != null) {
                List<MovieCrew> crewToDelete = new ArrayList<>(movie.getMovieCrewEntries());
                for (MovieCrew movieCrew : crewToDelete) {
                    em.remove(movieCrew);
                }
                em.remove(movie);
            } else {
                throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
            }
            transaction.commit();
        } catch (ApplicationException e) {
            rollbackTransaction(transaction);
            throw new ApplicationException(Message.Error.INTERNAL_ERROR);
        } catch (Exception e) {
            rollbackTransaction(transaction);
            throw new DBException(Message.Error.INTERNAL_ERROR);
        } finally {
            closeEntityManager(em);
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

    private void rollbackTransaction(EntityTransaction transaction) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }

    private void closeEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}