package com.amach.ordersservice;

import com.amach.ordersservice.client.ClientFacade;
import com.amach.ordersservice.report.ReportFacade;
import com.amach.ordersservice.request.RequestDto;
import com.amach.ordersservice.request.RequestFacade;
import com.amach.ordersservice.utils.MultipartFileConverter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scala.tools.jline_embedded.internal.Log;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@Log4j
public class MainController {

    private ReportFacade reportFacade;
    private RequestFacade requestFacade;
    private ClientFacade clientFacade;
    private MultipartFileConverter mfc;

    @Autowired
    public MainController(final ReportFacade repF,
                          final RequestFacade reqF,
                          final ClientFacade clientF,
                          final MultipartFileConverter mfc) {
        this.reportFacade = repF;
        this.requestFacade = reqF;
        this.clientFacade = clientF;
        this.mfc = mfc;
    }

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about(final Model model) {
        model.addAttribute("requests", reportFacade.getAllRequests());
        model.addAttribute("clients", clientFacade.getClientDtoList());
        return "about";
    }

    @GetMapping({"/admin/requests", "/requests"})
    public String list(final Model model) {
        model.addAttribute("requests", reportFacade.getAllRequests());
        model.addAttribute("totalPrice",
                requestFacade.getTotalPriceOfRequests());
        model.addAttribute("avgValue",
                requestFacade.getAverageValueFromAllRequests());
        model.addAttribute("clients", clientFacade.getClientDtoList());
        return "requests";
    }

    @GetMapping("/requests/clients/{id}")
    public String list(@PathVariable final Long id,
                       final Model model) {
        model.addAttribute("requests",
                reportFacade.getAllRequests());
        model.addAttribute("requestsById",
                reportFacade.getAllRequestByClientId(id));
        model.addAttribute("requestsCountById",
                requestFacade.getRequestCountByClientId(id));
        model.addAttribute("totalPriceById",
                requestFacade.getTotalPriceOfClientRequests(id));
        model.addAttribute("avgValueById",
                requestFacade.getAverageValueOfClientRequests(id));
        model.addAttribute("clients",
                clientFacade.getClientDtoList());
        return "clientRequests";
    }

    @GetMapping("/requests/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("requestById", reportFacade.getRequestById(id));
        model.addAttribute("requestsById",
                reportFacade.getAllRequestByClientId(id));
        return "requestEdit";
    }

    @GetMapping("/clients/{clientId}/requests/remove/{requestId}")
    public String remove(@PathVariable final Long clientId,
                         @PathVariable final Long requestId,
                         final HttpServletRequest request) {
        requestFacade.removeByRequestIdAndClientId(requestId, clientId);
        Log.info("Client id: " + clientId + "," +
                " request id: " + requestId + " removed successfully");
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/requests/clients/" + clientId;
        }
        return "redirect:/client/requests";
    }

    @PostMapping("/request")
    public String update(final RequestDto dto, final HttpServletRequest request) {
        reportFacade.update(dto);
        Log.info("Request name: " + dto.getName() + ", for client id: "
                + dto.getClientId() + " updated successfully");
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/requests";
        }
        return "redirect:/client/requests";
    }

    @GetMapping("/requests/save-xml")
    public String saveXml() throws JAXBException, IOException {
        reportFacade.saveToXml(reportFacade.getAllRequests());
        return "redirect:/requests";
    }

    @GetMapping("/clients/{id}/requests/save-xml")
    public String saveXmlByClientId(@PathVariable final Long id)
            throws JAXBException, IOException {
        reportFacade.saveToXmlByClientId(reportFacade.
                getAllRequestByClientId(id), id);
        return "redirect:/requests/clients/{id}";
    }

    @PostMapping("/requests/upload-xml")
    public String loadFromXmlFile(
            @RequestParam("myFile") final MultipartFile myFile)
            throws JAXBException, IOException {
        reportFacade.loadFromXmlFile(mfc.convertToFile(myFile));
        return "redirect:/requests";
    }

    @PostMapping("/requests/upload-csv")
    public String loadFromCsvFile(@RequestParam(
            "myFile") final MultipartFile myFile)
            throws IOException {
        reportFacade.loadFromCsvFile(mfc.convertToFile(myFile));
        return "redirect:/requests";
    }

    @GetMapping("/requests/save-csv")
    public String saveCsv() throws IOException,
            CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        reportFacade.saveToCsv(reportFacade.getAllRequests());
        return "redirect:/requests";
    }

    @GetMapping("/clients/{id}/requests/save-csv")
    public String saveCsvByClientId(@PathVariable final Long id)
            throws IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException {
        reportFacade.saveToCsvByClientId(reportFacade.
                getAllRequestByClientId(id), id);
        return "redirect:/requests/clients/{id}";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
