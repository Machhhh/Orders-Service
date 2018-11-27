package com.amach.ordersservice.utils.xmlParser;

import com.amach.ordersservice.request.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Getter
public class XmlFacade {

    private XmlService xS;

    public void saveToXml(final List<RequestDto> dtoList)
            throws JAXBException, IOException {
        xS.saveToXmlFile(xS.createXmlRequests(dtoList));
    }

    public void saveToXmlByClientId(final List<RequestDto> dtoList,
                                    final Long clientId)
            throws JAXBException, IOException {
        xS.saveToXmlByClientId(xS.createXmlRequestsByClientId(
                dtoList, clientId), clientId);
    }

    public XmlRequests parseFromXmlFile(final File myFile)
            throws JAXBException {
        return xS.loadFromXmlFile(myFile);
    }
}
