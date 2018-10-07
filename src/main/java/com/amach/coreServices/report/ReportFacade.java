package com.amach.coreServices.report;

import com.amach.coreServices.request.RequestDto;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Getter
public class ReportFacade {

    private ReportService repServ;

    public List<RequestDto> getAllRequests() {
        return repServ.getAllRequests();
    }

    public List<RequestDto> getAllRequestByClientId(final Long id) {
        return repServ.getAllRequestByClientId(id);
    }

    public RequestDto getRequestById(final Long id) {
        return repServ.getRequestById(id);
    }

    public RequestDto save(final RequestDto dto) {
        return repServ.save(dto);
    }

    public void saveToXml(final List<RequestDto> dtoList)
            throws JAXBException, IOException {
        repServ.saveToXml(dtoList);
    }

    public void saveToXmlByClientId(
            final List<RequestDto> dtoList, final Long clientId)
            throws JAXBException, IOException {
        repServ.saveToXmlByClientId(dtoList, clientId);
    }

    public void loadFromXmlFile(final File myFile) throws JAXBException {
        repServ.loadFromXmlFile(myFile);
    }

    public void loadFromCsvFile(final File myFile) throws IOException {
        repServ.loadFromCsvFile(myFile);
    }

    public void saveToCsv(final List<RequestDto> dtoList)
            throws IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException {
        repServ.saveToCsv(dtoList);
    }

    public void saveToCsvByClientId(final List<RequestDto> dtoList,
                                    final Long clientId)
            throws IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException {
        repServ.saveToCsvByClientId(dtoList, clientId);
    }
}
