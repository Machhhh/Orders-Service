package com.amach.coreServices.request;

import com.amach.coreServices.client.Client;
import com.amach.coreServices.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
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
    @Size(max = 255)
    private String name;
    @NotNull
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    private Client client;
}
