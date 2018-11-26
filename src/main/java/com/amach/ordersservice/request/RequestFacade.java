package com.amach.ordersservice.request;

import com.amach.ordersservice.utils.xmlParser.XmlRequests;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class RequestFacade {

    private RequestService reqS;

    public Request getRequestByUuid(final String uuid) {
        return reqS.findByUuid(uuid);
    }

    public RequestDto getRequestDtoByUuid(final String uuid) {
        return reqS.findOneByUuid(uuid);
    }

    public RequestCreateDto create(final RequestCreateDto dto) {
        return reqS.create(dto);
    }

    public RequestDto update(final RequestDto dto) {
        return reqS.update(dto);
    }

    public RequestDto getRequestDtoById(final Long id) {
        return reqS.findByRequestId(id);
    }

    public RequestDto getRequestDtoByName(final String name) {
        return reqS.findByName(name);
    }

    public List<RequestDto> getAllRequestDtos() {
        return reqS.findAll();
    }

    public List<RequestDto> findAllByClientId(final Long id) {
        return reqS.findAllByClientId(id);
    }

    public List<RequestDto> getRequestDtoList(final int from, final int to) {
        return reqS.findAll(from, to);
    }

    public List<RequestDto> getRequestDtoListByClientUuid(final String uuid) {
        return reqS.findAllByClientUuid(uuid);
    }

    public Boolean existsById(final Long id) {
        return reqS.existsById(id);
    }

    public Integer getRequestCountByClientId(final Long id) {
        return reqS.getRequestCountByClientId(id);
    }

    public BigDecimal getAverageValueOfClientRequests(final Long id) {
        return reqS.getAverageValueOfClientRequests(id);
    }

    public BigDecimal getAverageValueFromAllRequests() {
        return reqS.getAverageValueFromAllRequests();
    }

    public BigDecimal getTotalPriceOfClientRequests(final Long id) {
        return reqS.getTotalPriceOfClientRequests(id);
    }

    public BigDecimal getTotalPriceOfRequests() {
        return reqS.getTotalPriceOfRequests();
    }

    public void createRequestDtoFromXmlFile(final XmlRequests xmlRequests) {
        reqS.createRequestDtoFromList(reqS
                .getRequestDtoListFromXmlRequests(xmlRequests));
    }

    public void removeByRequestIdAndClientId(final Long requestId, final Long clientId) {
        reqS.removeByRequestIdAndClientId(requestId, clientId);
    }

    public void createRequestDtoFromList(
            final List<RequestDto> requestDtoList) {
        reqS.createRequestDtoFromList(requestDtoList);
    }
}
