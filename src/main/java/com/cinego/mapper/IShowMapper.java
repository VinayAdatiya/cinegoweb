package com.cinego.mapper;

import com.cinego.dto.show.ShowRequestDTO;
import com.cinego.dto.show.ShowResponseDTO;
import com.cinego.model.Show;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IShowMapper {
    @Mapping(source = "movieId", target = "movie.movieId")
    @Mapping(source = "screenId", target = "screen.screenId")
    @Mapping(source = "showPriceCategoryDTOS", target = "showPriceCategoryList")
    Show toModel(ShowRequestDTO showRequestDTO);

    @Mapping(source = "screen", target = "screen")
    @Mapping(source = "screen.theater.theaterId", target = "screen.theaterId")
    @Mapping(source = "screen.theater.theaterName", target = "screen.theaterName")
    ShowResponseDTO toDTO(Show show);
}
