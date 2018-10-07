package com.amach.coreServices.client;

import com.amach.coreServices.request.RequestDto;
import lombok.*;

import java.util.List;

@Builder(builderMethodName = "create")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long clientId;
    private String uuid;
    private String name;
    private List<RequestDto> requestDtoList;
}
