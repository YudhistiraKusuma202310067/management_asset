package com.management_asset.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.model.dto.EmailDTO;
import com.management_asset.api.service.implement.EmailService;

@RestController
@RequestMapping("api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMail")
    public String sendApproveOrDecline(@RequestBody EmailDTO emailDTO) {

        try {
            emailService.sendApproveOrDecline(emailDTO);
            return "true";
        } catch (Exception e) {
            return "Error while Sending Mail: " + e.getMessage();
        }

    }

    @PostMapping("/sendMailRequest")
    public Boolean requestEmail(@RequestBody EmailDTO emailDTO) {
        emailService.requestEmail(emailDTO);
        return true;
    }

    // @PostMapping("/sendMailWithAttachment")
    // public String sendMailWithAttachment(@RequestBody EmailDTO emailDTO) {
    // return emailService.sendMailWithAttachment(emailDTO);
    // }
}
