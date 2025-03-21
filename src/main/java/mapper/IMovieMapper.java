package mapper;

import dto.movie.MovieRequestDTO;
import dto.movie.MovieDTO;
import model.Movie;
import org.mapstruct.Mapper;

@Mapper
public interface IMovieMapper {
    Movie toMovieModel(MovieRequestDTO movieRequestDTO);

    MovieDTO toMovieResponseDTO(Movie movie);
}
