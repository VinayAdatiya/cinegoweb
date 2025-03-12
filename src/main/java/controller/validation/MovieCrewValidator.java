package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import entity.MovieCrewEntry;

public class MovieCrewValidator {

    public static void validateMovieCrewEntries(MovieCrewEntry.MovieCrewEntryList movieCrewEntries) throws ApplicationException {
        if (movieCrewEntries == null || movieCrewEntries.getMovieId() <= 0 || movieCrewEntries.getMovieCrewEntries() == null || movieCrewEntries.getMovieCrewEntries().isEmpty()) {
            throw new ApplicationException(Message.Error.MOVIE_CREW_ENTRIES_INVALID);
        }
        for (MovieCrewEntry entry : movieCrewEntries.getMovieCrewEntries()) {
            if (entry.getCrewId() <= 0 || entry.getDesignationId() <= 0) {
                throw new ApplicationException(Message.Error.MOVIE_CREW_ENTRY_INVALID);
            }
        }
    }
}