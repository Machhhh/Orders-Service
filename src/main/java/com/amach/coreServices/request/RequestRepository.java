package com.amach.coreServices.request;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

    Set<Request> findAll();

    Set<Request> findAllByClientId(Long id);

    Request findByUuid(String uuid);

    Request findByName(String name);

    Request findByRequestId(Long id);

    Set<Request> findAllByClientUuid(String uuid);

    void removeByUuid(String uuid);

    void removeAllByClientUuid(String uuid);

    void removeByRequestIdAndAndClientId(Long reqId, Long clientId);
}
