package com.amach.ordersservice.utils.csvParser;

import com.amach.ordersservice.request.RequestDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsvService.class)
@DirtiesContext
@Log4j
public class CsvServiceTest {

    private static final String
            SAMPLE_CSV_FILE_PATH_READ = "./testSampleRead.csv";
    private static final String
            SAMPLE_CSV_FILE_PATH_SAVE = "./testSampleSave.csv";
    private static final String
            SAMPLE_CSV_FILE_PATH_READ_SAVED = "./testSampleSave.csv";
    private List<RequestDto> expectedData;

    @Before
    public void setUpCsv() {
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
    public void beanSaveToCsvAndThenReadFromCsvTest()
            throws IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException {
        //given
        Writer writer = new FileWriter(SAMPLE_CSV_FILE_PATH_SAVE);
        StatefulBeanToCsvBuilder<RequestDto> builder =
                new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<RequestDto> beanWriter = builder
                .withSeparator(',')
                .build();
        beanWriter.write(expectedData);
        writer.close();
        log.info("CSV file: " + "\"" + "testSampleSave.csv" + " created");
        List<RequestDto> parsedData = getRequestDtosFromCsv(
                SAMPLE_CSV_FILE_PATH_READ_SAVED);
        log.info("CSV file: " + "\"" + "testSampleSave.csv"
                + "\"" + " parsed to bean");
        //when
        String expectedName = expectedData.get(0).getName();
        String actualName = parsedData.get(0).getName();
        //then
        assertEquals(expectedName, actualName);
        AssertionsData(parsedData);
    }

    @Test
    public void readFromCsvAndThenParseToBeanTest() throws IOException {
        //given
        List<RequestDto> parsedData = getRequestDtosFromCsv(
                SAMPLE_CSV_FILE_PATH_READ);
        log.info("CSV file parsed to list of RequestDto beans");
        //when
        String expectedName = expectedData.get(0).getName();
        String actualName = parsedData.get(0).getName();
        //then
        assertEquals(expectedName, actualName);
        AssertionsData(parsedData);
    }


    private void AssertionsData(final List<RequestDto> parsedData) {
        assertEquals(expectedData.get(0).getUuid(),
                parsedData.get(0).getUuid());
        assertEquals(expectedData.get(0).getPrice(),
                parsedData.get(0).getPrice());
        assertEquals(expectedData.get(0).getClientName(),
                parsedData.get(0).getClientName());
        assertEquals(expectedData.get(0).getClientId(),
                parsedData.get(0).getClientId());
        assertEquals(expectedData.get(0).getRequestId(),
                parsedData.get(0).getRequestId());
        assertEquals(expectedData.get(0).getQuantity(),
                parsedData.get(0).getQuantity());
    }

    private List<RequestDto> getRequestDtosFromCsv(
            final String sampleCsvFilePathReadSaved) throws IOException {
        Reader reader = Files.newBufferedReader(
                Paths.get(sampleCsvFilePathReadSaved));
        CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                .withType(RequestDto.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreQuotations(true)
                .withThrowExceptions(true)
                .build();
        return (List<RequestDto>) csvToBean.parse();
    }
}