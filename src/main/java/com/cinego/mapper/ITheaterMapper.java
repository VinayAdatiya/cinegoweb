package com.cinego.mapper;

import com.cinego.model.Theater;
import com.cinego.dto.theater.TheaterRequestDTO;
import com.cinego.dto.theater.TheaterResponseDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ITheaterMapper {
    Theater toTheaterModel(TheaterRequestDTO theaterRequestDTO);

    TheaterResponseDTO toTheaterResponseDTO(Theater theater);
}
