package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.molsztyn.meetyourmeal.api.dto.AddressDTO;
import pl.molsztyn.meetyourmeal.domain.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Address mapFromDTO(AddressDTO addressDTO);
}
