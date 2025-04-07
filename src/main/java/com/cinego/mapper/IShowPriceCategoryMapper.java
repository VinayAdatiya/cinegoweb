package com.cinego.mapper;

import com.cinego.dto.show.ShowPriceCategoryDTO;
import com.cinego.model.ShowPriceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IShowPriceCategoryMapper {
    @Mapping(source = "seatCategoryId", target = "seatCategory.seatCategoryId")
    @Mapping(source = "price", target = "basePrice")
    ShowPriceCategory toModel(ShowPriceCategoryDTO showPriceCategoryDTO);

    @Mapping(source = "seatCategory.seatCategoryId", target = "seatCategoryId")
    @Mapping(source = "basePrice", target = "price")
    ShowPriceCategoryDTO toDTO(ShowPriceCategory showPriceCategory);
}
