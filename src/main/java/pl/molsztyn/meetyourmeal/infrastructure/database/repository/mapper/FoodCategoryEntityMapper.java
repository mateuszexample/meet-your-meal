package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.FoodCategory;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.FoodCategoryEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FoodCategoryEntityMapper {
    FoodCategory mapFromEntity(FoodCategoryEntity foodCategoryEntity);
}
