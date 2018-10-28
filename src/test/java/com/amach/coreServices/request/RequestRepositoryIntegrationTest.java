package com.amach.coreServices.request;

import com.amach.coreServices.client.Client;
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
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
@DataJpaTest
@DirtiesContext
@Transactional
public class RequestRepositoryIntegrationTest {

    private Request request1;
    private Request request2;

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RequestRepository requestRepository;

    @Before
    public void setUpRequest() {
        request1 = new Request.RequestBuilder()
                .name("Suzuki GSR 750")
                .price(new BigDecimal(11.20).
                        round(new MathContext(
                                3, RoundingMode.DOWN)))
                .quantity(21)
                .build();
        request2 = new Request.RequestBuilder()
                .name("Kawasaki ZX6R")
                .quantity(77)
                .build();
    }

    @Test
    public void saveRequestTest() {
        //update request, verify has ID value after update
        assertNull(request1.getRequestId()); //null before update
        requestRepository.save(request1);
        assertNotNull(request1.getRequestId()); //not null after update
        //fetch from DB
        Request fetchedRequest = requestRepository
                .findByRequestId(request1.getRequestId());
        //should not be null
        assertNotNull(fetchedRequest);
        //should equal
        assertEquals(request1.getRequestId(), fetchedRequest.getRequestId());
        assertEquals(request1.getName(), fetchedRequest.getName());
        assertEquals(request1.getQuantity(), fetchedRequest.getQuantity());
        assertEquals(request1.getPrice(), fetchedRequest.getPrice());
        //update description and update
        fetchedRequest.setName("Suzuki Hayabusa 1300");
        requestRepository.save(fetchedRequest);
        //get from DB, should be updated
        Request fetchedUpdatedRequest = requestRepository
                .findByRequestId(fetchedRequest.getRequestId());
        assertEquals(fetchedRequest.getName(),
                fetchedUpdatedRequest.getName());
        //verify count of requests in DB
        long requestsCount = requestRepository.count();
        assertEquals(requestsCount, 1);
        //get all requests, list should only have one
        Iterable<Request> requests = requestRepository.findAll();
        int count = 0;
        for (Request iter : requests) {
            count++;
        }
        assertEquals(count, 1);
    }

    @Test
    public void whenFindByName_thenReturnRequest() {
        // given
        entityManager.persistAndFlush(request2);
        // when
        Request found = requestRepository.findByName(request2.getName());
        // then
        assertThat(found.getName()).isEqualTo(request2.getName());
    }

    @Test
    public void whenFindAll_thenReturnRequests() {
        // given
        entityManager.persist(request1);
        entityManager.persistAndFlush(request2);
        // when
        Set<Request> foundedRequests = requestRepository.findAll();
        // then
        assertEquals(2, foundedRequests.size());

    }

    @Test
    public void whenFindByUuid_thenReturnRequest() {
        // given
        entityManager.persistAndFlush(request2);
        // when
        Request found = requestRepository.findByUuid(request2.getUuid());
        // then
        assertThat(found.getUuid())
                .isEqualTo(request2.getUuid());
    }

    @Test
    public void whenRemoveByUuid_thenRequestNotExists() {
        // given
        entityManager.persistAndFlush(request2);
        // when
        Request found = requestRepository.
                findByRequestId(request2.getRequestId());
        // then
        assertEquals(request2.getRequestId(),
                found.getRequestId());
        //remove from DB
        requestRepository.removeByUuid(found.getUuid());
        //should be empty after remove
        assertThat(requestRepository.findAll()).isEmpty();
    }

    @Test
    public void whenRemoveByClientUuid_thenClientRequestsNotExists() {
        //setUp client
        Client client = Client.create()
                .name("Wolf")
                .build();
        // given
        entityManager.persist(client);
        request2.setClient(client);
        entityManager.persistAndFlush(request2);
        // when
        Request found = requestRepository.
                findByRequestId(request2.getRequestId());
        // then
        assertEquals(request2.getRequestId(),
                found.getRequestId());
        assertEquals(found.getClient().getId(),
                client.getId());
        //remove from DB
        requestRepository.removeAllByClientUuid(found.getClient().getUuid());
        //should be empty after remove
        assertThat(requestRepository.findAll()).isEmpty();
    }
}
