package com.amach.coreServices.utils.xmlParser;

import com.amach.coreServices.request.RequestDto;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

interface XmlService {

    XmlRequests loadFromXmlFile(File myFile) throws JAXBException;

    void saveToXmlFile(XmlRequests xmlRequests)
            throws JAXBException, IOException;

    void saveToXmlByClientId(XmlRequests xmlRequests, Long clientId)
            throws JAXBException, IOException;

    XmlRequests createXmlRequests(List<RequestDto> dtoList);

    XmlRequests createXmlRequestsByClientId(
            List<RequestDto> dtoList, Long clientId);
}
