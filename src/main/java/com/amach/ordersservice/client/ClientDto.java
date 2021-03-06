package com.amach.ordersservice.client;

import com.amach.ordersservice.request.RequestDto;
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
    private String role;
    private List<RequestDto> requestDtoList;
}
