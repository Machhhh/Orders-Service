package com.amach.ordersservice.request;

import com.amach.ordersservice.utils.MultipartFileConverter;
import com.amach.ordersservice.utils.csvParser.CsvFacade;
import com.amach.ordersservice.utils.xmlParser.XmlFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
class RequestRestController {

    private RequestService reqServ;
    private CsvFacade csvF;
    private XmlFacade xmlF;
    private MultipartFileConverter mfc;

    @Autowired
    RequestRestController(final RequestService reqServ,
                          final CsvFacade csvF,
                          final XmlFacade xmlF,
                          final MultipartFileConverter mfc) {
        this.reqServ = reqServ;
        this.csvF = csvF;
        this.xmlF = xmlF;
        this.mfc = mfc;
    }

    @PostMapping
    RequestCreateDto create(@RequestBody final RequestCreateDto createDto) {
        return reqServ.create(createDto);
    }

    @GetMapping
    List<RequestDto> getAllRequests(@RequestParam(defaultValue = "0"
    ) final int from, @RequestParam(defaultValue = "2"
    ) final int to) {
        return reqServ.findAll(from, to);
    }

    @GetMapping("/find-by-name")
    RequestDto findByName(@RequestParam final String name) {
        return reqServ.findByName(name);
    }

    @GetMapping("/{uuid}")
    RequestDto findByUuid(@PathVariable final String uuid) {
        return reqServ.findOneByUuid(uuid);
    }

    @DeleteMapping("/{uuid}")
    void delete(@PathVariable final String uuid) {
        reqServ.removeRequestByUuid(uuid);
    }

    @PostMapping(value = "/upload-csv",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadCsvFile(@RequestPart(
            value = "data") final MultipartFile file)
            throws IOException {
        reqServ.createRequestDtoFromList(csvF
                .parseFromCsvFile(mfc.convertToFile(file)));
    }

    @PostMapping(value = "/upload-xml",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadXmlFile(@RequestPart(
            value = "data") final MultipartFile file)
            throws JAXBException, IOException {
        reqServ.createRequestDtoFromList(reqServ
                .getRequestDtoListFromXmlRequests(
                        xmlF.parseFromXmlFile(mfc.convertToFile(file))));
    }
}
