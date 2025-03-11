package servlets.validation;

import common.Message;
import common.ValidationUtil;
import common.exception.ApplicationException;
import entity.Movie;
import java.time.LocalDate;

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
    }
}