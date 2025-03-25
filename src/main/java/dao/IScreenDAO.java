package dao;

import common.exception.DBException;
import dto.screen.ScreenRequestDTO;
import dto.screen.ScreenResponseDTO;
import model.Screen;

import java.util.List;

public interface IScreenDAO {
    void addScreen(Screen screen) throws DBException;

    Screen getScreenById(int screenId) throws DBException;

    List<Screen> getAllScreens() throws DBException;

    void updateScreen(Screen screen) throws DBException;

    void deleteScreen(int screenId) throws DBException;
}
