package com.cinego.mapper;

import com.cinego.dto.screen.ScreenRequestDTO;
import com.cinego.dto.screen.ScreenResponseDTO;
import com.cinego.model.Screen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IScreenMapper {
    Screen toScreenModel(ScreenRequestDTO screenRequestDTO);

    @Mapping(source = "theater.theaterId" , target = "theaterId")
    @Mapping(source = "theater.theaterName" , target = "theaterName")
    ScreenResponseDTO toScreenResponseDTO(Screen screen);
}
