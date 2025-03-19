package dao;

import common.exception.DBException;
import model.Format;

import java.util.List;

public interface IFormatDAO {
    List<Format> getAllFormats() throws DBException;
}
