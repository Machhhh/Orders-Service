package com.amach.coreServices.client;

import com.amach.coreServices.report.ReportFacade;
import com.amach.coreServices.request.RequestFacade;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;

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
    public String welcome(HttpServletRequest request) {
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

    @PutMapping("/apply")
    public String login(@ModelAttribute ClientCreateDto dto, Model model) {
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
        log.info("Client with name: " + dto.getName() + " and login: "
                + dto.getLogin() + " created successfully");
        return "login";
    }

    @GetMapping("/clients/register")
    public String register(Model model) {
        model.addAttribute("dto", new ClientCreateDto());
        return "register";
    }

    @GetMapping({"/login", "/clients/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/clients/login/error")
    public String error(Model model) {
        model.addAttribute("msg", "Wrong login or password");
        return "login";
    }

    @GetMapping("/clients/access/error")
    public String access(Model model) {
        model.addAttribute("access", "Access denied, need Admin privileges");
        return "login";
    }

    @GetMapping("/clients/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "index";
    }
}
