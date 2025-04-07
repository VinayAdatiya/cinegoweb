package com.cinego.dao;

import com.cinego.model.Screen;
import com.cinego.common.exception.DBException;

import java.util.List;

public interface IScreenDAO {
    void addScreen(Screen screen) throws DBException;

    Screen getScreenById(int screenId) throws DBException;

    List<Screen> getAllScreens() throws DBException;

    List<Screen> getAllScreensByTheater(int theaterId) throws DBException;

    void updateScreen(Screen screen) throws DBException;

    void deleteScreen(int screenId) throws DBException;
}
