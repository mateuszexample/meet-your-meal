package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.molsztyn.meetyourmeal.api.dto.FoodCategoryDTO;
import pl.molsztyn.meetyourmeal.api.dto.FoodCategoryRequestDTO;
import pl.molsztyn.meetyourmeal.domain.FoodCategory;
import pl.molsztyn.meetyourmeal.domain.FoodCategoryRequest;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    FoodCategoryDTO mapToDTO(FoodCategory foodCategory);

    FoodCategoryRequest mapFromDTO(FoodCategoryRequestDTO foodCategoryRequestDTO);
}
