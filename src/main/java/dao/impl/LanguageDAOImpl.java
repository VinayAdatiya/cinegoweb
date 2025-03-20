package dao.impl;

import common.Message;
import common.exception.DBException;
import dao.ILanguageDAO;
import model.Language;
import config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAOImpl implements ILanguageDAO {

    public List<Language> getAllLanguages() throws DBException {
        List<Language> languages = new ArrayList<>();
        String query = "SELECT language_id , language_name FROM languages";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                languages.add(new Language(rs.getInt("language_id"), rs.getString("language_name")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR);
        } finally {
            DBConnection.closeResources(rs, preparedStatement, connection);
        }
        return languages;
    }

    @Override
    public List<Language> getLanguagesByMovieId(int movieId, Connection connection) throws DBException {
        List<Language> languages = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet movieLanguagesSet = null;
        String query = "SELECT l.language_id, l.language_name " +
                "FROM languages l " +
                "JOIN movie_languages ml " +
                "ON l.language_id = ml.language_id " +
                "WHERE ml.movie_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, movieId);
            movieLanguagesSet = preparedStatement.executeQuery();
            while (movieLanguagesSet.next()) {
                Language language = new Language();
                language.setLanguageId(movieLanguagesSet.getInt("language_id"));
                language.setLanguageName(movieLanguagesSet.getString("language_name"));
                languages.add(language);
            }
            return languages;
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(movieLanguagesSet, preparedStatement, null);
        }
    }
}
