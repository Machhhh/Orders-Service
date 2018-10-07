package com.amach.coreServices.client;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
class ClientServiceImpl implements ClientService {


    private ClientMapper clientMapper;

    private ClientRepository clientRepository;

    ClientServiceImpl(final ClientMapper clientMapper,
                      final ClientRepository clientRepository) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientDto> findAll() {
        return clientMapper.toClientDtoList(clientRepository.findAll());
    }

    @Override
    public ClientDto findOneByUuid(final String uuid) {
        return clientMapper.toClientDto(clientRepository.findByUuid(uuid));
    }

    @Override
    public Client findByUuid(final String uuid) {
        return clientRepository.findByUuid(uuid);
    }

    @Override
    public Client findById(final Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Boolean existsById(final Long id) {
        return clientRepository.exists(id);
    }

    @Override
    public ClientDto findByName(final String name) {
        return clientMapper.toClientDto(clientRepository.findByName(name));
    }

    @Override
    public ClientCreateDto create(final ClientCreateDto dto) {
        Client client = Client.create()
                .name(dto.getName())
                .build();
        return clientMapper.toClientCreateDto(clientRepository.save(client));
    }

    @Override
    public Client create(final ClientDto dto) {
        return clientRepository.save(clientMapper.toClient(dto));
    }

    @Override
    public void removeByUuid(final String uuid) {
        clientRepository.removeByUuid(uuid);
    }
}
