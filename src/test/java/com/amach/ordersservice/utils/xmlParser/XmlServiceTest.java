package com.amach.ordersservice.utils.xmlParser;

import com.amach.ordersservice.request.RequestDto;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XmlService.class)
@DirtiesContext
@Log4j
public class XmlServiceTest {

    private static final String
            SAMPLE_XML_FILE_PATH_SAVE_AND_READ = "./testSampleXml.xml";
    private List<RequestDto> expectedData;

    @Before
    public void setUpXml() {
        RequestDto requestDto = RequestDto.create()
                .clientId(1L)
                .clientName("Batman")
                .requestId(1L)
                .name("Pizza")
                .price(new BigDecimal(5.99).
                        round(new MathContext(
                                3, RoundingMode.DOWN)))
                .quantity(55)
                .uuid("534426f2-5531-4e97-92ae-68f4bd20ec2b")
                .build();
        RequestDto requestDto1 = RequestDto.create()
                .clientId(1L)
                .clientName("Lolek")
                .requestId(2L)
                .name("Beer")
                .uuid("467g36f2-5531-4e97-92ae-68dghyrfeswa")
                .price(new BigDecimal(7.11).
                        round(new MathContext(
                                3, RoundingMode.DOWN)))
                .quantity(91)
                .build();
        expectedData = Arrays.asList(requestDto, requestDto1);
    }

    @Test
    public void saveToXmlFile_thenLoadFromXmlFile()
            throws IOException, JAXBException {
        //create new file
        File file = new File(String.valueOf(
                SAMPLE_XML_FILE_PATH_SAVE_AND_READ));
        if (!file.exists()) {
            file.createNewFile();
        }
        log.info("Xml file: " + "\"" + "testSampleXml.xml" + " created");
        //convert and update RequestDto list to file
        XmlRequests xmlRequests = XmlRequests.create()
                .xmlRequests(expectedData)
                .build();
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlRequests.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(xmlRequests, file);
        log.info("Xml file: " + "\"" + "testSampleXml.xml" + " saved");
        //load xml file
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        XmlRequests xmlParsedData = (XmlRequests) unmarshaller
                .unmarshal(new FileReader(file));
        //parse xml from file to RequestsDto list
        log.info("Xml file: " + "\"" + "testSampleXml.xml" + " loaded");
        List<RequestDto> actualData = xmlParsedData.getXmlRequests();
        //compare actual and expected RequestDto list
        log.info("Xml file: " + "\"" + "testSampleXml.xml" + " parsed");
        System.out.println("Parsed file content: " + actualData);
        assertEquals(expectedData.get(0).getClientName(),
                actualData.get(0).getClientName());
        assertEquals(expectedData.get(0).getPrice(),
                actualData.get(0).getPrice());
        assertEquals(expectedData.get(0).getUuid(),
                actualData.get(0).getUuid());
        assertEquals(expectedData.get(1).getName(),
                actualData.get(1).getName());
        assertEquals(expectedData.get(1).getRequestId(),
                actualData.get(1).getRequestId());
        assertEquals(expectedData.get(1).getPrice(),
                actualData.get(1).getPrice());
    }
}
