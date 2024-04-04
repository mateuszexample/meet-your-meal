package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.molsztyn.meetyourmeal.api.dto.DeliveryAreaAddRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.DeliveryAreaDTO;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;
import pl.molsztyn.meetyourmeal.domain.DeliveryAreaAddRequest;

@Mapper(componentModel = "spring")
public interface DeliveryAreaMapper {

    DeliveryAreaDTO mapToDTO(DeliveryArea deliveryArea);

    DeliveryAreaAddRequest mapFromDTO(DeliveryAreaAddRequestDTO deliveryAreaAddRequestDTO);
}
