package com.amach.coreServices.utils.xmlParser;

import com.amach.coreServices.request.RequestDto;
import com.amach.coreServices.request.RequestFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
class XmlServiceImpl implements XmlService {

    private RequestFacade reqF;

    @Autowired
    XmlServiceImpl(final RequestFacade reqF) {
        this.reqF = reqF;
    }

    @Override
    public XmlRequests createXmlRequests(final List<RequestDto> dtoList) {
        return XmlRequests.create()
                .xmlRequests(dtoList)
                .totalCounter(dtoList.size())
                .totalPrice(reqF.getTotalPriceOfRequests())
                .avgPrice(reqF.getAverageValueFromAllRequests())
                .build();
    }

    @Override
    public XmlRequests createXmlRequestsByClientId(
            final List<RequestDto> dtoList, final Long clientId) {
        return XmlRequests.create()
                .xmlRequests(dtoList)
                .totalCounter(reqF.getRequestCountByClientId(clientId))
                .totalPrice(reqF.getTotalPriceOfClientRequests(clientId))
                .avgPrice(reqF.getAverageValueOfClientRequests(clientId))
                .build();
    }

    @Override
    public void saveToXmlFile(final XmlRequests xmlRequests)
            throws JAXBException, IOException {
        Path path = Paths.get(System.getProperty("user.home"),
                "savedXml.xml");
        createFile(xmlRequests, path);
    }

    @Override
    public void saveToXmlByClientId(final XmlRequests xmlRequests,
                                    final Long clientId)
            throws JAXBException, IOException {
        Path path = Paths.get(System.getProperty("user.home"),
                "savedXmlClientId" + clientId + ".xml");
        createFile(xmlRequests, path);
    }

    private void createFile(final XmlRequests xmlRequests,
                            final Path path)
            throws IOException, JAXBException {
        File file = new File(String.valueOf(path));
        if (!file.exists()) {
            file.createNewFile();
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlRequests.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(xmlRequests, file);
    }

    @Override
    public XmlRequests loadFromXmlFile(final File myFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlRequests.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (XmlRequests) unmarshaller.unmarshal(myFile);
    }
}
