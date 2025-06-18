package com.management_asset.api.service;

import com.management_asset.api.model.dto.EmailDTO;

public interface IEmailService {

    String sendMail(EmailDTO emailDTO);

    // String sendMailWithAttachment(EmailDTO emailDTO);

    String requestEmail(EmailDTO emailDTO);

    String sendApproveOrDecline(EmailDTO emailDTO);
}
