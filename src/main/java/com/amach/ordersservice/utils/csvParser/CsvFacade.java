package com.amach.ordersservice.utils.csvParser;

import com.amach.ordersservice.request.RequestDto;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Getter
public class CsvFacade {

    private CsvService cS;

    public List<RequestDto> parseFromCsvFile(final File myFile)
            throws IOException {
        return cS.parseFromCsvFile(myFile);
    }

    public void saveToCsv(final List<RequestDto> dtoList) throws IOException,
            CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        cS.saveToCsv(dtoList);
    }

    public void saveToCsvByClientId(final List<RequestDto> dtoList,
                                    final Long clientId) throws IOException,
            CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        cS.saveToCsvByClientId(dtoList, clientId);
    }
}
