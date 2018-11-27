package com.amach.ordersservice.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ClientFacade {

    private ClientService clientService;

    public Client getClientByUuid(final String uuid) {
        return clientService.findByUuid(uuid);
    }

    public Client getClientById(final Long id) {
        return clientService.findById(id);
    }

    public ClientCreateDto create(final ClientCreateDto dto) {
        return clientService.create(dto);
    }

    public Client create(final ClientDto dto) {
        return clientService.create(dto);
    }

    public Client getClientByLogin(final String login) {
        return clientService.findByLogin(login);
    }

    public ClientDto getClientDtoByUuid(final String uuid) {
        return clientService.findOneByUuid(uuid);
    }

    public ClientDto getClientDtoByName(final String name) {
        return clientService.findByName(name);
    }

    public Boolean isClientExists(String login) {
        return clientService.existsByLogin(login);
    }

    public List<ClientDto> getClientDtoList() {
        return clientService.findAll();
    }

    public Boolean existsById(final Long id) {
        return clientService.existsById(id);
    }
}
