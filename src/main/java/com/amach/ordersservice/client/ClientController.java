package com.amach.ordersservice.client;

import com.amach.ordersservice.report.ReportFacade;
import com.amach.ordersservice.request.RequestCreateDto;
import com.amach.ordersservice.request.RequestFacade;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import scala.tools.jline_embedded.internal.Log;

import javax.servlet.http.HttpServletRequest;

@Controller
@Log4j
public class ClientController {

    private ClientFacade clientFacade;
    private ReportFacade reportFacade;
    private RequestFacade requestFacade;

    @Autowired
    public ClientController(ClientFacade clientFacade,
                            ReportFacade reportFacade,
                            RequestFacade requestFacade) {
        this.clientFacade = clientFacade;
        this.reportFacade = reportFacade;
        this.requestFacade = requestFacade;
    }

    @GetMapping("/welcome")
    public String welcome(final HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/requests";
        }
        return "redirect:/client/requests";
    }

    @GetMapping("/client/requests")
    public String list(final Model model) {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = client.getId();
        model.addAttribute("requestsById",
                reportFacade.getAllRequestByClientId(id));
        model.addAttribute("requestsCountById",
                requestFacade.getRequestCountByClientId(id));
        model.addAttribute("totalPriceById",
                requestFacade.getTotalPriceOfClientRequests(id));
        model.addAttribute("avgValueById",
                requestFacade.getAverageValueOfClientRequests(id));
        return "client";
    }

    @GetMapping("/client/requests/new")
    public String newRequest(final Model model) {
        model.addAttribute("request", new RequestCreateDto());
        return "requestNew";
    }

    @PostMapping("client/requests/new")
    public String saveNewRequest(final RequestCreateDto dto, final HttpServletRequest request) {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setClientId(client.getId());
        requestFacade.create(dto);
        Log.info("Request name: " + dto.getName() + ", for client id: "
                + dto.getClientId() + " created successfully");
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/requests";
        }
        return "redirect:/client/requests";
    }

    @PutMapping("/apply")
    public String login(@ModelAttribute final ClientCreateDto dto, final Model model) {
        if (clientFacade.isClientExists(dto.getLogin())) {
            model.addAttribute("dto", new ClientCreateDto());
            model.addAttribute("message", "Client already exists");
            return "register";
        }
        if (dto.getLogin().length() < 4) {
            model.addAttribute("dto", new ClientCreateDto());
            model.addAttribute("message", "Login must have min. 4 letters");
            return "register";
        }
        if (dto.getPassword().length() < 4) {
            model.addAttribute("dto", new ClientCreateDto());
            model.addAttribute("message", "Password must have min. 4 letters");
            return "register";
        }
        clientFacade.create(dto);
        Log.info("Client with name: " + dto.getName() + " and login: "
                + dto.getLogin() + " created successfully");
        return "login";
    }

    @GetMapping("/clients/register")
    public String register(final Model model) {
        model.addAttribute("dto", new ClientCreateDto());
        return "register";
    }

    @GetMapping({"/login", "/clients/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/clients/login/error")
    public String error(final Model model) {
        model.addAttribute("msg", "Wrong login or password");
        return "login";
    }

    @GetMapping("/clients/access/error")
    public String access(final Model model) {
        model.addAttribute("access", "Access denied, need Admin privileges");
        return "login";
    }

    @GetMapping("/clients/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "index";
    }
}
