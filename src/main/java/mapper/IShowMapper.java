package mapper;

import dto.show.ShowPriceCategoryDTO;
import dto.show.ShowRequestDTO;
import dto.show.ShowResponseDTO;
import model.Show;
import model.ShowPriceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IShowMapper {
    @Mapping(source = "movieId", target = "movie.movieId")
    @Mapping(source = "screenId", target = "screen.screenId")
    @Mapping(source = "showPriceCategoryDTOS", target = "showPriceCategoryList")
    Show toModel(ShowRequestDTO showRequestDTO);

    @Mapping(source = "seatCategoryId", target = "seatCategory.seatCategoryId")
    @Mapping(source = "price", target = "basePrice")
    ShowPriceCategory toShowPriceCategory(ShowPriceCategoryDTO showPriceCategoryDTO);

    ShowResponseDTO toDTO(Show show);
}
