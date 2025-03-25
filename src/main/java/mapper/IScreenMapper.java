package mapper;

import dto.screen.ScreenRequestDTO;
import dto.screen.ScreenResponseDTO;
import model.Screen;
import org.mapstruct.Mapper;

@Mapper
public interface IScreenMapper {
    Screen toScreenModel(ScreenRequestDTO screenRequestDTO);

    ScreenResponseDTO toScreenResponseDTO(Screen screen);
}
