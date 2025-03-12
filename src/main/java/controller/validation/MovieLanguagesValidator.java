// servlets/validation/MovieLanguagesValidator.java
package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import java.util.List;

public class MovieLanguagesValidator {

    public static class MovieLanguageList {
        private int movieId;
        private List<Integer> languageIds;

        public int getMovieId() {
            return movieId;
        }

        public List<Integer> getLanguageIds() {
            return languageIds;
        }
    }

    public static void validateMovieLanguageList(MovieLanguageList movieLanguageList) throws ApplicationException {
        if (movieLanguageList == null) {
            throw new ApplicationException(Message.Error.MOVIE_LANGUAGES_LIST_NULL);
        }
        if (movieLanguageList.getMovieId() <= 0) {
            throw new ApplicationException(Message.Error.MOVIE_ID_INVALID);
        }
        if (movieLanguageList.getLanguageIds() == null || movieLanguageList.getLanguageIds().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_LANGUAGES_EMPTY);
        }
        for (Integer languageId : movieLanguageList.getLanguageIds()) {
            if (languageId == null || languageId <= 0) {
                throw new ApplicationException(Message.Error.LANGUAGE_ID_INVALID);
            }
        }
    }
}