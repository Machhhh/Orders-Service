package com.amach.coreServices.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
class ClientRestController {

    private ClientService clientService;

    @Autowired
    ClientRestController(final ClientService cS) {
        this.clientService = cS;
    }

    @PostMapping
    ClientCreateDto create(@RequestBody final ClientCreateDto dto) {
        return clientService.create(dto);
    }

    @GetMapping
    List<ClientDto> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/{uuid}")
    ClientDto findByUuid(@PathVariable final String uuid) {
        return clientService.findOneByUuid(uuid);
    }

    @DeleteMapping("/{uuid}")
    void delete(@PathVariable final String uuid) {
        clientService.removeByUuid(uuid);
    }
}
