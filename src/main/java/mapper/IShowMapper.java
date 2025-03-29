package mapper;

import dto.show.ShowRequestDTO;
import dto.show.ShowResponseDTO;
import model.Show;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IShowMapper {
    @Mapping(source = "movieId", target = "movie.movieId")
    @Mapping(source = "screenId", target = "screen.screenId")
    @Mapping(source = "showPriceCategoryDTOS", target = "showPriceCategoryList")
    Show toModel(ShowRequestDTO showRequestDTO);

    ShowResponseDTO toDTO(Show show);
}
