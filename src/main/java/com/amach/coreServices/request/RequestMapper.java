package com.amach.coreServices.request;

import com.amach.coreServices.client.ClientMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ClientMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RequestMapper {

    @Mappings({
            @Mapping(source = "client.id", target = "clientId"),
            @Mapping(source = "client.name", target = "clientName")
    })
    RequestDto toRequestDto(Request request);

    @Mapping(source = "client.id", target = "clientId")
    RequestCreateDto toRequestCreateDto(Request request);

    List<RequestDto> toRequestDtoList(Collection<Request> requestCollection);

    @Mappings({
            @Mapping(source = "clientId", target = "client.id"),
            @Mapping(source = "clientName", target = "client.name")
    })
    Request toRequest(RequestDto requestDto);

    List<Request> toRequestList(Collection<RequestDto> requestDtoCollection);
}
