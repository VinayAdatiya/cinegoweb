package com.cinego.mapper;

import com.cinego.dto.screen.ScreenRequestDTO;
import com.cinego.dto.screen.ScreenResponseDTO;
import com.cinego.model.Screen;
import org.mapstruct.Mapper;

@Mapper
public interface IScreenMapper {
    Screen toScreenModel(ScreenRequestDTO screenRequestDTO);

    ScreenResponseDTO toScreenResponseDTO(Screen screen);
}
