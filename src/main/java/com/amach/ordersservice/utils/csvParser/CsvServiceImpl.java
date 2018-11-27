package com.amach.ordersservice.utils.csvParser;

import com.amach.ordersservice.request.RequestDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
@Log
class CsvServiceImpl implements CsvService {

    @Override
    public List<RequestDto> parseFromCsvFile(final File myFile)
            throws IOException {
        Reader reader = new BufferedReader(new FileReader(myFile));
        CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                .withType(RequestDto.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreQuotations(true)
                .withThrowExceptions(true)
                .build();
        return (List<RequestDto>) csvToBean.parse();
    }

    @Override
    public void saveToCsvByClientId(final List<RequestDto> dtoList,
                                    final Long clientId)
            throws IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException {
        Path path = Paths.get(System.getProperty("user.home"),
                String.format("savedCsvClientId%d.csv", clientId));
        Writer writer = new FileWriter(String.valueOf(path));
        StatefulBeanToCsvBuilder<RequestDto> builder =
                new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<RequestDto> beanWriter = builder.
                withSeparator(',')
                .withThrowExceptions(true)
                .build();
        log.info("Csv for client Id: " + clientId + " created");
        beanWriter.write(dtoList);
        writer.close();
    }

    @Override
    public void saveToCsv(final List<RequestDto> dtoList) throws IOException,
            CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Path path = Paths.get(System.getProperty("user.home"), "savedCsv.csv");
        Writer writer = new FileWriter(String.valueOf(path));
        StatefulBeanToCsvBuilder<RequestDto> builder =
                new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<RequestDto> beanWriter = builder
                .withSeparator(',')
                .build();
        log.info("Csv file with all requests created");
        beanWriter.write(dtoList);
        writer.close();
    }
}
