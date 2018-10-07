package com.amach.coreServices.client;

import com.amach.coreServices.request.RequestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {RequestMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    @Mappings({
            @Mapping(source = "requests", target = "requestDtoList"),
            @Mapping(source = "id", target = "clientId")
    })
    ClientDto toClientDto(Client client);

    Client toClient(ClientCreateDto dto);

    @Mapping(source = "clientId", target = "id")
    Client toClient(ClientDto dto);

    ClientCreateDto toClientCreateDto(Client client);

    List<ClientDto> toClientDtoList(Collection<Client> clients);
}
