package com.cinego.mapper;

import com.cinego.dto.show.ShowSeatResponseDTO;
import com.cinego.model.ShowSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IShowSeatMapper {
    @Mapping(source = "seatCategory.seatCategoryId", target = "seatCategoryId")
    ShowSeatResponseDTO toDTO(ShowSeat showSeat);
}
