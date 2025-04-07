package com.cinego.dao;

import com.cinego.common.exception.DBException;
import com.cinego.model.Format;

import java.sql.Connection;
import java.util.List;

public interface IFormatDAO {
    List<Format> getAllFormats() throws DBException;

    List<Format> getFormatsByMovieId(int movieId, Connection connection) throws DBException;
}
