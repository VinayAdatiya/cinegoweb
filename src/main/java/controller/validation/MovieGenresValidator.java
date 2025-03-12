// servlets/validation/MovieGenresValidator.java
package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import java.util.List;

public class MovieGenresValidator {

    public static class MovieGenreList {
        private int movieId;
        private List<Integer> genreIds;

        public int getMovieId() {
            return movieId;
        }

        public List<Integer> getGenreIds() {
            return genreIds;
        }
    }

    public static void validateMovieGenreList(MovieGenreList movieGenreList) throws ApplicationException {
        if (movieGenreList == null) {
            throw new ApplicationException(Message.Error.MOVIE_GENRES_LIST_NULL);
        }
        if (movieGenreList.getMovieId() <= 0) {
            throw new ApplicationException(Message.Error.MOVIE_ID_INVALID);
        }
        if (movieGenreList.getGenreIds() == null || movieGenreList.getGenreIds().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_GENRES_EMPTY);
        }
        for (Integer genreId : movieGenreList.getGenreIds()) {
            if (genreId == null || genreId <= 0) {
                throw new ApplicationException(Message.Error.GENRE_ID_INVALID);
            }
        }
    }
}