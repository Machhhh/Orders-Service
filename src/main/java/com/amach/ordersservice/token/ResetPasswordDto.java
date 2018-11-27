package com.amach.ordersservice.token;

import lombok.*;

@Builder(builderMethodName = "create")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ResetPasswordDto {

    private String token;
    private String password;
    private String repeatPassword;
}