package com.amach.ordersservice.request;

import com.amach.ordersservice.client.Client;
import com.amach.ordersservice.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder(builderMethodName = "create")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Requests")
public class Request extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;
    @NotNull
    @Size(min = 2, max = 128, message = "Name should have atleast 2 characters, and max 128.")
    private String name;
    @NotNull
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    private Client client;
}
