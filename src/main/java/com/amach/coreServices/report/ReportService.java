package com.amach.coreServices.report;

import com.amach.coreServices.request.RequestDto;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

interface ReportService {

    List<RequestDto> getAllRequests();

    List<RequestDto> getAllRequestByClientId(Long id);

    RequestDto getRequestById(Long id);

    RequestDto save(RequestDto dto);

    void saveToXml(List<RequestDto> dtoList) throws JAXBException, IOException;

    void saveToXmlByClientId(List<RequestDto> dtoList, Long clientId)
            throws JAXBException, IOException;

    void loadFromXmlFile(File myFile) throws JAXBException;

    void loadFromCsvFile(File myFile) throws IOException;

    void saveToCsv(List<RequestDto> dtoList)
            throws IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException;

    void saveToCsvByClientId(List<RequestDto> dtoList, Long clientId)
            throws IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException;
}
