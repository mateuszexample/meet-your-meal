package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
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

    FoodDTO mapToDTO(Food food);

    FoodRequest mapFromDTO(FoodRequestDTO foodRequestDTO);
}
