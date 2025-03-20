package mapper;

import dto.movie.MovieRequestDTO;
import dto.movie.MovieResponseDTO;
import model.Movie;
import org.mapstruct.Mapper;

@Mapper
public interface IMovieMapper {
    Movie toMovieModel(MovieRequestDTO movieRequestDTO);

    MovieResponseDTO toMovieResponseDTO(Movie movie);
}
