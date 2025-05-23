package com.cinego.controller.validation;

import com.cinego.model.MovieCrew;
import com.cinego.common.Message;
import com.cinego.common.utils.ValidationUtil;
import com.cinego.common.exception.ApplicationException;
import com.cinego.dto.movie.MovieRequestDTO;
import com.cinego.common.utils.DatabaseUtil;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class MovieValidator {

    public static void validateMovie(MovieRequestDTO movieRequestDTO) throws ApplicationException {
        if (movieRequestDTO.getMovieId() != 0) {
            if (!DatabaseUtil.checkRecordExists("movie", "movie_id", movieRequestDTO.getMovieId())) {
                throw new ApplicationException(Message.Error.INVALID_ID);
            }
        }

        if (movieRequestDTO.getMovieTitle() == null || movieRequestDTO.getMovieTitle().trim().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_TITLE_REQUIRED);
        }

        if (!ValidationUtil.isValidLength(movieRequestDTO.getMovieTitle(), 30)) {
            throw new ApplicationException(Message.Error.MOVIE_TITLE_TOO_LONG);
        }

        if (movieRequestDTO.getMovieRating() == null || movieRequestDTO.getMovieRating().compareTo(BigDecimal.ZERO) < 0 || movieRequestDTO.getMovieRating().compareTo(BigDecimal.TEN) > 0) {
            throw new ApplicationException(Message.Error.MOVIE_RATING_INVALID);
        }

        if (movieRequestDTO.getMovieDuration() == null) {
            throw new ApplicationException(Message.Error.MOVIE_DURATION_REQUIRED);
        } else {
            String durationStr = movieRequestDTO.getMovieDuration().toString();
            if (!ValidationUtil.isValidTime(durationStr)) {
                throw new ApplicationException(Message.Error.MOVIE_DURATION_INVALID);
            }
        }

        if (movieRequestDTO.getMovieReleaseDate() == null) {
            throw new ApplicationException(Message.Error.MOVIE_RELEASE_DATE_REQUIRED);
        }

        if (movieRequestDTO.getLanguages() == null || movieRequestDTO.getLanguages().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_LANGUAGES_LIST_NULL);
        }
        DatabaseUtil.validateIdsExist("languages", "language_id", movieRequestDTO.getLanguageIds());

        if (movieRequestDTO.getGenres() == null || movieRequestDTO.getGenres().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_GENRES_LIST_NULL);
        }
        DatabaseUtil.validateIdsExist("genres", "genre_id", movieRequestDTO.getGenreIds());

        if (movieRequestDTO.getFormats() == null || movieRequestDTO.getFormats().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_FORMATS_LIST_NULL);
        }
        DatabaseUtil.validateIdsExist("formats", "format_id", movieRequestDTO.getFormatIds());

        if (movieRequestDTO.getMovieCrewEntries() == null || movieRequestDTO.getMovieCrewEntries().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_CREW_ENTRIES_NULL);
        }

        List<Integer> crewIds = movieRequestDTO
                .getMovieCrewEntries()
                .stream()
                .map(MovieCrew -> MovieCrew.getCrew().getCrewId())
                .collect(Collectors.toList());
        DatabaseUtil.validateIdsExist("crew", "crew_id", crewIds);

        List<Integer> designationIds = movieRequestDTO
                .getMovieCrewEntries()
                .stream()
                .map(MovieCrew -> MovieCrew.getCrewDesignation().getDesignationId())
                .collect(Collectors.toList());
        DatabaseUtil.validateIdsExist("crew_designation", "designation_id", designationIds);

        List<String> characterNames = movieRequestDTO.getMovieCrewEntries().stream().map(MovieCrew::getCharacterName).toList();
//        System.out.println(characterNames);
        for (String cn : characterNames) {
            if (cn != null) {
                if (cn.length() > 30) {
                    throw new ApplicationException(Message.Error.CHARACTER_NAME_TOO_LONG);
                }
            }
        }
    }
}