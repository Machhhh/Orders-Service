package com.amach.coreServices.report;

import com.amach.coreServices.request.RequestDto;
import com.amach.coreServices.request.RequestFacade;
import com.amach.coreServices.utils.csvParser.CsvFacade;
import com.amach.coreServices.utils.xmlParser.XmlFacade;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
class ReportServiceImpl implements ReportService {

    private RequestFacade requestFacade;
    private XmlFacade xmlFacade;
    private CsvFacade csvFacade;

    @Autowired
    ReportServiceImpl(final RequestFacade reqF, final XmlFacade xF,
                      final CsvFacade cF) {
        this.requestFacade = reqF;
        this.xmlFacade = xF;
        this.csvFacade = cF;
    }

    @Override
    public List<RequestDto> getAllRequests() {
        return requestFacade.getAllRequestDtos();
    }

    @Override
    public RequestDto getRequestById(final Long id) {
        return requestFacade.getRequestDtoById(id);
    }

    @Override
    public RequestDto save(final RequestDto dto) {
        return requestFacade.update(dto);
    }

    @Override
    public List<RequestDto> getAllRequestByClientId(final Long id) {
        return requestFacade.findAllByClientId(id);
    }

    @Override
    public void saveToXml(final List<RequestDto> dtoList)
            throws JAXBException, IOException {
        xmlFacade.saveToXml(dtoList);
    }

    @Override
    public void saveToXmlByClientId(final List<RequestDto> dtoList,
                                    final Long clientId)
            throws JAXBException, IOException {
        xmlFacade.saveToXmlByClientId(dtoList, clientId);
    }

    @Override
    public void saveToCsv(final List<RequestDto> dtoList) throws IOException,
            CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        csvFacade.saveToCsv(dtoList);
    }

    @Override
    public void saveToCsvByClientId(final List<RequestDto> dtoList,
                                    final Long clientId)
            throws IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException {
        csvFacade.saveToCsvByClientId(dtoList, clientId);
    }

    @Override
    public void loadFromXmlFile(final File myFile) throws JAXBException {
        requestFacade.createRequestDtoFromXmlFile(
                xmlFacade.parseFromXmlFile(myFile));
    }

    @Override
    public void loadFromCsvFile(final File myFile) throws IOException {
        requestFacade.createRequestDtoFromList(
                csvFacade.parseFromCsvFile(myFile));
    }
}
