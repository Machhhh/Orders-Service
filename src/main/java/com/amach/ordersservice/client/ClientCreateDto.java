package com.amach.coreServices.client;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder(builderMethodName = "create")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientCreateDto {

    @NotNull
    @Size(max = 255)
    private String name;
    private String login;
    private String password;
    private String email;
    private String role;
}
