package com.amach.ordersservice.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder(builderMethodName = "create")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateDto {

    @NotNull
    @Size(max = 255)
    private String name;
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal price;
    private Long clientId;
}
