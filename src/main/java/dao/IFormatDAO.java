package dao;

import common.exception.DBException;
import model.Format;

import java.sql.Connection;
import java.util.List;

public interface IFormatDAO {
    List<Format> getAllFormats() throws DBException;

    List<Format> getFormatsByMovieId(int movieId, Connection connection) throws DBException;
}
