package com.amach.coreServices.client;

import com.amach.coreServices.configuration.RepositoryConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
@DataJpaTest
@DirtiesContext
@Transactional
public class ClientRepositoryIntegrationTest {

    private Client client1;
    private Client client2;

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ClientRepository clientRepository;

    @Before
    public void setUpClient() {
        client1 = Client.create()
                .name("Wolf")
                .build();
        client2 = Client.create()
                .name("Rabbit")
                .build();
    }

    @Test
    public void saveClientTest() {
        //null before update
        assertNull(client1.getId());
        clientRepository.save(client1);
        //not null after update
        assertNotNull(client1.getId());
        //fetch from DB
        Client fetchedClient = clientRepository.findById(client1.getId());
        //should not be null
        assertNotNull(fetchedClient);
        //should equal
        assertEquals(client1.getId(), fetchedClient.getId());
        assertEquals(client1.getName(), fetchedClient.getName());
        //update description and update
        fetchedClient.setName("Pinokio");
        clientRepository.save(fetchedClient);
        //get from DB, should be updated
        Client fetchedUpdatedClient = clientRepository
                .findById(fetchedClient.getId());
        assertEquals(fetchedClient.getName(),
                fetchedUpdatedClient.getName());
        //verify count of clients in DB
        long clientCount = clientRepository.count();
        assertEquals(clientCount, 1);
        //get all clients, list should only have one
        Iterable<Client> clients = clientRepository.findAll();
        int count = 0;
        for (Client iter : clients) {
            count++;
        }
        assertEquals(count, 1);
    }

    @Test
    public void whenFindByName_thenReturnClient() {
        // given
        entityManager.persistAndFlush(client2);
        // when
        Client found = clientRepository.findByName(client2.getName());
        // then
        assertThat(found.getName())
                .isEqualTo(client2.getName());
    }

    @Test
    public void whenFindAll_thenReturnClients() {
        // given
        entityManager.persist(client1);
        entityManager.persistAndFlush(client2);
        // when
        Set<Client> foundedClients = clientRepository.findAll();
        // then
        assertEquals(2, foundedClients.size());

    }

    @Test
    public void whenFindByUuid_thenReturnClient() {
        // given
        entityManager.persistAndFlush(client1);
        // when
        Client found = clientRepository.findByUuid(client1.getUuid());
        // then
        assertThat(found.getUuid())
                .isEqualTo(client1.getUuid());
    }

    @Test
    public void whenRemoveByUuid_thenClientNotExists() {
        // given
        entityManager.persistAndFlush(client2);
        // when
        Client found = clientRepository.findById(client2.getId());
        // then
        assertEquals(client2.getId(),
                found.getId());
        //remove from DB
        clientRepository.removeByUuid(found.getUuid());
        //should be empty after remove
        assertThat(clientRepository.findAll()).isEmpty();
    }
}
