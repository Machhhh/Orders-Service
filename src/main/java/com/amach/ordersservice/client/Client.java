package com.amach.ordersservice.client;

import com.amach.ordersservice.common.BaseEntity;
import com.amach.ordersservice.request.Request;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder(builderMethodName = "create")
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class Client extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(max = 255)
    private String name;
    @Column(unique = true)
    private String login;
    private String password;
    private String email;
    private String role;
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL})
    private List<Request> requests;
}
