package pl.java.springpetclinic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.java.springpetclinic.dto.OwnerDto;
import pl.java.springpetclinic.owner.Owner;

@Mapper
public interface OwnerMapper {
    OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);
    
    @Mapping(target = "phoneNumber")
    OwnerDto ownerToOwnerDto(Owner owner);
}
