package com.amach.coreServices.client;

import java.util.List;

interface ClientService {

    List<ClientDto> findAll();

    ClientDto findOneByUuid(String uuid);

    Client findByUuid(String uuid);

    Client findById(Long id);

    Client findByLogin(String login);

    ClientDto findByName(String name);

    ClientCreateDto create(ClientCreateDto dto);

    Client create(ClientDto dto);

    Boolean existsById(Long id);

    Boolean existsByLogin(String login);

    void removeByUuid(String uuid);
}
