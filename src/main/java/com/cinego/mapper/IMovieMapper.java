package com.cinego.mapper;

import com.cinego.dto.movie.MovieDTO;
import com.cinego.dto.movie.MovieRequestDTO;
import com.cinego.model.Movie;
import org.mapstruct.Mapper;

@Mapper
public interface IMovieMapper {
    Movie toMovieModel(MovieRequestDTO movieRequestDTO);

    MovieDTO toMovieResponseDTO(Movie movie);
}
