package com.amach.ordersservice.client;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Set<Client> findAll();

    Client findByUuid(String uuid);

    Client findByName(String name);

    Client findById(Long id);

    Client findByLogin(String login);

    Boolean existsByLogin(String login);

    void removeByUuid(String uuid);
}
