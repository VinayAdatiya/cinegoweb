// servlets/validation/MovieFormatsValidator.java
package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import java.util.List;

public class MovieFormatsValidator {

    public static class MovieFormatList {
        private int movieId;
        private List<Integer> formatIds;

        public int getMovieId() {
            return movieId;
        }

        public List<Integer> getFormatIds() {
            return formatIds;
        }
    }

    public static void validateMovieFormatList(MovieFormatList movieFormatList) throws ApplicationException {
        if (movieFormatList == null) {
            throw new ApplicationException(Message.Error.MOVIE_FORMATS_LIST_NULL);
        }
        if (movieFormatList.getMovieId() <= 0) {
            throw new ApplicationException(Message.Error.MOVIE_ID_INVALID);
        }
        if (movieFormatList.getFormatIds() == null || movieFormatList.getFormatIds().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_FORMATS_EMPTY);
        }
        for (Integer formatId : movieFormatList.getFormatIds()) {
            if (formatId == null || formatId <= 0) {
                throw new ApplicationException(Message.Error.FORMAT_ID_INVALID);
            }
        }
    }
}