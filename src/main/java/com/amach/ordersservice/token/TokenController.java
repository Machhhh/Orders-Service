package com.amach.ordersservice.token;

import com.amach.ordersservice.client.ClientFacade;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import scala.tools.jline_embedded.internal.Log;

@Controller
@Log4j
@RequestMapping("/passwords")
class TokenController {

    private TokenService tokenService;
    private MailServiceImpl mailService;
    private ClientFacade clientFacade;

    @Autowired
    public TokenController(TokenService tokenService, MailServiceImpl mailService,
                           ClientFacade clientFacade) {
        this.tokenService = tokenService;
        this.mailService = mailService;
        this.clientFacade = clientFacade;
    }

    @GetMapping("/mail")
    public String generateNewToken(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        String token = tokenService.createToken(login);
        try {
            mailService.sendNotification(login, token);
        } catch (MailException e) {
            e.printStackTrace();
        }
        Log.info("Password reset procedure mail for " + login + " sent successfully");
        model.addAttribute("info",
                "Link to reset your password was send. Check your mailbox & follow instructions.");
        return "login";
    }

    @GetMapping("/reset/{uuid}")
    public String getResetPasswordView(@PathVariable String uuid, Model model) {
        model.addAttribute("token", uuid);
        model.addAttribute("dto", new ResetPasswordDto());
        return "reset-password";
    }

    @GetMapping("/reset/new/{uuid}")
    public String resetPassword(@PathVariable String uuid, @ModelAttribute ResetPasswordDto dto, Model model) {
        if (!dto.getPassword().equals(dto.getRepeatPassword())
                || !uuid.equals(dto.getToken()) || tokenService.checkIsTokenExpired(uuid)) {
            model.addAttribute("token", dto.getToken());
            model.addAttribute("dto", new ResetPasswordDto());
            model.addAttribute("error", "Passwords aren't equal or link has already expired");
            return "reset-password";
        }
        tokenService.resetPassword(dto.getToken(), dto.getPassword());
        model.addAttribute("info", "Password changed successfully");
        return "login";
    }

    @GetMapping("/reset/view")
    public String getResetPasswordViewForNotLoggedUser() {
        return "reset-view";
    }

    @GetMapping("/check")
    public String resetPasswordForNotLoggedUser(@RequestParam String login, Model model) {
        if (clientFacade.getClientByLogin(login) == null) {
            model.addAttribute("error", "Sorry, login does not exists");
            return "reset-view";
        }
        String token = tokenService.createToken(login);
        try {
            mailService.sendNotification(login, token);
        } catch (MailException e) {
            e.printStackTrace();
        }
        model.addAttribute("info",
                "Link to reset your password was send. Check your mailbox & follow instructions.");
        return "login";
    }
}
