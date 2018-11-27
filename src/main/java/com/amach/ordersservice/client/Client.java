package com.amach.ordersservice.client;

import com.amach.ordersservice.common.BaseEntity;
import com.amach.ordersservice.request.Request;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Size(min = 2, max = 64, message = "Name should have atleast 2 characters, and max 64.")
    private String name;
    @Column(unique = true)
    @NotNull
    @Size(min = 4, max = 64, message = "Login should have atleast 4 characters, and max 64.")
    private String login;
    @NotNull
    @Size(min = 4, max = 64, message = "Password should have atleast 4 characters, and max 64.")
    private String password;
    @NotNull
    @Email(message = "Please provide a valid email address")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;
    private String role;
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL})
    private List<Request> requests;
}
