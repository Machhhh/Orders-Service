package com.amach.ordersservice.security;

import com.amach.ordersservice.client.Client;
import com.amach.ordersservice.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserDetailsServiceImpl implements UserDetailsService {

    private ClientRepository clientRepository;

    @Autowired
    public UserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Client client = clientRepository.findByLogin(login);
        if (client == null) {
            throw new UsernameNotFoundException("Client not found");
        }

        return new UserDetailsImpl(client.getLogin(), client.getPassword(),
                client.getRole(), client.getId());
    }
}
