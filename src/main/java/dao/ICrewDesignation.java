package dao;

import common.exception.DBException;
import model.CrewDesignation;

import java.util.List;

public interface ICrewDesignation {
    List<CrewDesignation> getAllCrewDesignation() throws DBException;
}
