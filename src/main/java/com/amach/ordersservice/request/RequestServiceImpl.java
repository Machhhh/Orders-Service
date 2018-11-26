package com.amach.ordersservice.request;

import com.amach.ordersservice.client.Client;
import com.amach.ordersservice.client.ClientDto;
import com.amach.ordersservice.client.ClientFacade;
import com.amach.ordersservice.exception.ClientNotFoundException;
import com.amach.ordersservice.exception.RequestNotFoundException;
import com.amach.ordersservice.utils.xmlParser.XmlRequests;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j
@Transactional
class RequestServiceImpl implements RequestService {

    private RequestMapper reqMap;
    private RequestRepository reqRep;
    private ClientFacade clientF;

    @Autowired
    RequestServiceImpl(final RequestMapper reqMap,
                       final RequestRepository reqRep,
                       final ClientFacade clientF) {
        this.reqMap = reqMap;
        this.reqRep = reqRep;
        this.clientF = clientF;
    }

    @Override
    public List<RequestDto> findAll() {
        return reqMap.toRequestDtoList(reqRep.findAll());
    }

    @Override
    public List<RequestDto> findAllByClientId(final Long id) {
        return reqMap.toRequestDtoList(reqRep.findAllByClientId(id));
    }

    @Override
    public List<RequestDto> findAll(final int from, final int to) {
        Pageable pageable = new PageRequest(from,
                to, Sort.Direction.ASC, "name");
        return reqRep.findAll(pageable)
                .getContent()
                .stream()
                .map(r -> reqMap.toRequestDto(r))
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto update(final RequestDto dto) {
        Request request = reqRep.findByUuid(dto.getUuid());
        if (request == null) {
            log.info("Request for uuid:" + dto.getUuid() + " Not found");
            throw new RequestNotFoundException(dto.getRequestId());
        }
        request.setName(dto.getName());
        request.setPrice(dto.getPrice());
        request.setQuantity(dto.getQuantity());
        return reqMap.toRequestDto(reqRep.save(request));
    }

    @Override
    public RequestDto findOneByUuid(final String uuid) {
        return reqMap.toRequestDto(reqRep.findByUuid(uuid));
    }

    @Override
    public Request findByUuid(final String uuid) {
        return reqRep.findByUuid(uuid);
    }

    @Override
    public RequestDto findByRequestId(final Long id) {
        return reqMap.toRequestDto(reqRep.findByRequestId(id));
    }

    @Override
    public RequestDto findByName(final String name) {
        return reqMap.toRequestDto(reqRep.findByName(name));
    }

    @Override
    public Boolean existsById(final Long id) {
        return reqRep.exists(id);
    }

    @Override
    public List<RequestDto> findAllByClientUuid(final String uuid) {
        return reqMap.toRequestDtoList(reqRep.findAllByClientUuid(uuid));
    }

    @Override
    public RequestCreateDto create(final RequestCreateDto dto) {
        Client client = clientF.getClientById(dto.getClientId());
        if (client == null) {
            throw new ClientNotFoundException(dto.getClientId());
        }
        Request request = Request.create()
                .client(client)
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .build();
        return reqMap.toRequestCreateDto(
                reqRep.save(request));
    }

    @Override
    public synchronized void createRequestDtoFromList(
            final List<RequestDto> requestDtoList) {
        int filteredId = requestDtoList.stream()
                .map(RequestDto::getClientId)
                .distinct()
                .collect(Collectors.summarizingInt(Long::intValue)).getMax();

        if (clientF.getClientDtoList().size() < filteredId) {
            for (int i = 0; i < filteredId; i++) {
                ClientDto dto = ClientDto.create()
                        .name("anonymous")
                        .build();
                clientF.create(dto);
            }
        }
        for (RequestDto dto : requestDtoList) {
            Client client = clientF.getClientByUuid(dto.getUuid());
            if (client == null) {
                ClientDto clientDto = ClientDto.create()
                        .clientId(dto.getClientId())
                        .name(dto.getClientName())
                        .build();
                clientF.create(clientDto);
            }
            Request request = reqRep.findByUuid(dto.getUuid());
            if (request == null) {
                RequestDto requestDto = RequestDto.create()
                        .requestId(dto.getRequestId())
                        .name(dto.getName())
                        .quantity(dto.getQuantity())
                        .price(dto.getPrice())
                        .clientId(dto.getClientId())
                        .clientName(dto.getClientName())
                        .build();
                reqRep.save(reqMap.toRequest(requestDto));
            }
        }
    }

    @Override
    public Integer getRequestCountByClientId(final Long id) {
        return findAllByClientId(id).size();
    }

    @Override
    public BigDecimal getTotalPriceOfRequests() {
        return findAll().stream()
                .map(RequestDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalPriceOfClientRequests(final Long id) {
        return findAllByClientId(id).stream()
                .map(RequestDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getAverageValueFromAllRequests() {
        BigDecimal sum = getTotalPriceOfRequests();
        if (!sum.equals(new BigDecimal(0))) {
            return sum.divide(new BigDecimal(findAll()
                    .size()), RoundingMode.DOWN);
        }
        return sum;
    }

    @Override
    public BigDecimal getAverageValueOfClientRequests(final Long id) {
        BigDecimal sum = getTotalPriceOfClientRequests(id);
        if (!sum.equals(new BigDecimal(0))) {
            return sum.divide(new BigDecimal(findAllByClientId(id)
                    .size()), RoundingMode.DOWN);
        }
        return sum;
    }

    @Override
    public void removeByRequestIdAndClientId(Long reqId, Long clientId) {
        reqRep.removeByRequestIdAndAndClientId(reqId, clientId);
    }

    @Override
    public List<RequestDto> getRequestDtoListFromXmlRequests(
            final XmlRequests xmlRequests) {
        return xmlRequests.getXmlRequests();
    }

    @Override
    public void removeRequestByUuid(final String uuid) {
        reqRep.removeByUuid(uuid);
    }
}
