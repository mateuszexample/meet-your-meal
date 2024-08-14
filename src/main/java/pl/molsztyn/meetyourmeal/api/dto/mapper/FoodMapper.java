package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.molsztyn.meetyourmeal.api.dto.FoodCategoryDTO;
import pl.molsztyn.meetyourmeal.api.dto.FoodCategoryRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.FoodDTO;
import pl.molsztyn.meetyourmeal.api.dto.FoodRequestDTO;
import pl.molsztyn.meetyourmeal.domain.Food;
import pl.molsztyn.meetyourmeal.domain.FoodCategory;
import pl.molsztyn.meetyourmeal.domain.FoodCategoryRequest;
import pl.molsztyn.meetyourmeal.domain.FoodRequest;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    FoodCategoryDTO mapToDTO(FoodCategory foodCategory);

    FoodCategoryRequest mapFromDTO(FoodCategoryRequestDTO foodCategoryRequestDTO);

    @Mapping(target = "hasImage", expression = "java(food.getImageData() != null && food.getImageData().length > 0)")
    FoodDTO mapToDTO(Food food);

    @Mapping(target = "imageData", ignore = true)
    FoodRequest mapFromDTO(FoodRequestDTO foodRequestDTO);
}
