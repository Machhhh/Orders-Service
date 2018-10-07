package com.amach.coreServices.client;

import com.amach.coreServices.common.BaseEntity;
import com.amach.coreServices.request.Request;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL})
    private List<Request> requests;
}