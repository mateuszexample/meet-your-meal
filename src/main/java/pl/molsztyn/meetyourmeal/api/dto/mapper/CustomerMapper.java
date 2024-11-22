package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.molsztyn.meetyourmeal.api.dto.CustomerDTO;
import pl.molsztyn.meetyourmeal.domain.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO mapToDTO(Customer customer);
}
