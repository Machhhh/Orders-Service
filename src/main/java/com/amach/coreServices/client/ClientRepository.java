package com.amach.coreServices.client;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface ClientRepository extends CrudRepository<Client, Long> {

    Set<Client> findAll();

    Client findByUuid(String uuid);

    Client findByName(String name);

    Client findById(Long id);

    void removeByUuid(String uuid);
}
