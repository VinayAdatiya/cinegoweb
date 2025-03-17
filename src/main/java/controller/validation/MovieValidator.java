package controller.validation;

import common.Message;
import common.ValidationUtil;
import common.exception.ApplicationException;
import model.Movie;
import model.MovieCrew;
import utils.DatabaseUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class MovieValidator {

    public static void validateMovie(Movie movie) throws ApplicationException {
        if (movie.getMovieTitle() == null || movie.getMovieTitle().trim().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_TITLE_REQUIRED);
        }

        if (movie.getMovieRating() == null || movie.getMovieRating() < 0 || movie.getMovieRating() > 10) {
            throw new ApplicationException(Message.Error.MOVIE_RATING_INVALID);
        }

        if (movie.getMovieDuration() == null) {
            throw new ApplicationException(Message.Error.MOVIE_DURATION_REQUIRED);
        } else {
            String durationStr = movie.getMovieDuration().toString();
            if (ValidationUtil.isValidTime(durationStr)) {
                throw new ApplicationException(Message.Error.MOVIE_DURATION_INVALID);
            }
        }

        if (movie.getMovieReleaseDate() == null) {
            throw new ApplicationException(Message.Error.MOVIE_RELEASE_DATE_REQUIRED);
        } else {
            if (movie.getMovieReleaseDate().isAfter(LocalDate.now())) {
                throw new ApplicationException(Message.Error.MOVIE_RELEASE_DATE_FUTURE);
            }
        }

        if (movie.getLanguageIds() == null || movie.getLanguageIds().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_LANGUAGES_LIST_NULL);
        }
        DatabaseUtil.validateIdsExist("languages", "language_id", movie.getLanguageIds());

        if (movie.getGenreIds() == null || movie.getGenreIds().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_GENRES_LIST_NULL);
        }
        DatabaseUtil.validateIdsExist("genres", "genre_id", movie.getGenreIds());

        if (movie.getFormatIds() == null || movie.getFormatIds().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_FORMATS_LIST_NULL);
        }
        DatabaseUtil.validateIdsExist("formats", "format_id", movie.getFormatIds());

        if (movie.getMovieCrewEntries() == null || movie.getMovieCrewEntries().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_CREW_ENTRIES_NULL);
        }

        List<Integer> crewIds = movie.getMovieCrewEntries().stream().map(MovieCrew::getCrewId).collect(Collectors.toList());
        DatabaseUtil.validateIdsExist("crew", "crew_id", crewIds);

        List<Integer> designationIds = movie.getMovieCrewEntries().stream().map(MovieCrew::getDesignationId).collect(Collectors.toList());
        DatabaseUtil.validateIdsExist("crew_designation", "designation_id", designationIds);

        List<String> characterNames = movie.getMovieCrewEntries().stream().map(MovieCrew::getCharacterName).toList();
        for (String cn : characterNames) {
            if (cn.length() > 30) {
                throw new ApplicationException(Message.Error.CHARACTER_NAME_TOO_LONG);
            }
        }
    }
}