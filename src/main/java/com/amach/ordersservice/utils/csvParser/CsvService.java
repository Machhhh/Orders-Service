package com.amach.coreServices.utils.csvParser;

import com.amach.coreServices.request.RequestDto;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.IOException;
import java.util.List;

interface CsvService {

    List<RequestDto> parseFromCsvFile(File myFile) throws IOException;

    void saveToCsvByClientId(List<RequestDto> dtoList, Long clientId) throws
            IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException;

    void saveToCsv(List<RequestDto> dtoList) throws
            IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException;
}
