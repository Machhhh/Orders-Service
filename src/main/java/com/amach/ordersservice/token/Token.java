package com.amach.ordersservice.token;

import com.amach.ordersservice.client.Client;
import com.amach.ordersservice.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder(builderMethodName = "create")
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
class Token extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean expired;
    @ManyToOne
    private Client client;
}
