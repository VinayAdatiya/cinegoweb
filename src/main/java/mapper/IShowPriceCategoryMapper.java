package mapper;

import dto.show.ShowPriceCategoryDTO;
import model.ShowPriceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IShowPriceCategoryMapper {
    @Mapping(source = "seatCategoryId", target = "seatCategory.seatCategoryId")
    ShowPriceCategory toModel(ShowPriceCategoryDTO showPriceCategoryDTO);

    @Mapping(source = "seatCategory.seatCategoryId", target = "seatCategoryId")
    ShowPriceCategoryDTO toDTO(ShowPriceCategory showPriceCategory);
}
