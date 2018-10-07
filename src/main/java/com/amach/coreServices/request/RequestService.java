package com.amach.coreServices.request;

import com.amach.coreServices.utils.xmlParser.XmlRequests;

import java.math.BigDecimal;
import java.util.List;

interface RequestService {

    List<RequestDto> findAll(int page, int pageSize);

    List<RequestDto> findAll();

    List<RequestDto> findAllByClientId(Long id);

    RequestDto findOneByUuid(String uuid);

    RequestDto findByRequestId(Long id);

    Request findByUuid(String uuid);

    RequestDto update(RequestDto dto);

    RequestDto findByName(String name);

    List<RequestDto> findAllByClientUuid(String uuid);

    RequestCreateDto create(RequestCreateDto dto);

    void createRequestDtoFromList(List<RequestDto> requestDtoList);

    void removeRequestByUuid(String uuid);

    List<RequestDto> getRequestDtoListFromXmlRequests(XmlRequests xmlRequests);

    Boolean existsById(Long id);

    Integer getRequestCountByClientId(Long id);

    BigDecimal getTotalPriceOfRequests();

    BigDecimal getTotalPriceOfClientRequests(Long id);

    BigDecimal getAverageValueFromAllRequests();

    BigDecimal getAverageValueOfClientRequests(Long id);
}