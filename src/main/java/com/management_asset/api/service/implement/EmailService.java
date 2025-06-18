package com.management_asset.api.service.implement;

// import javax.mail.MessagingException;
// import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
// import java.io.File;

import com.management_asset.api.model.dto.EmailDTO;
import com.management_asset.api.service.IEmailService;

import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendMail(EmailDTO emailDTO) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(sender);
            mail.setTo(emailDTO.getEmployeeEmail());
            mail.setSubject(emailDTO.getSubject());
            mail.setText(emailDTO.getBody());
            mailSender.send(mail);
            return "Mail Sent";
        } catch (Exception e) {
            return "Error while Sending Mail: " + e.getMessage();
        }
    }

    @Override
    public String sendApproveOrDecline(EmailDTO dto) {

        if (dto.isApproved()) {
            dto.setSubject("Peminjaman Aset Disetujui");
            dto.setBody(String.format(
                    "Selamat %s,\n\nPermintaan peminjaman aset '%s' telah disetujui.",
                    dto.getEmployeeName(), dto.getAssetName()));
            sendMail(dto);
            return "approve";
        } else {
            dto.setSubject("Peminjaman Aset Ditolak");
            dto.setBody(String.format(
                    "Hai %s,\n\nMaaf, permintaan peminjaman aset '%s' ditolak. Silakan hubungi admin untuk info lebih lanjut.",
                    dto.getEmployeeName(), dto.getAssetName()));
            sendMail(dto);
            return "decline";
        }
    }

    @Override
    public String requestEmail(EmailDTO dto) {
        dto.setSubject("Pengajuan Peminjaman");
        dto.setBody(String.format(
                "Pengajuan,\n\n peminjaman aset '%s' telah dikirim.",
                dto.getAssetName()));
        sendMail(dto);
        return "Request Berhasil";
    }

    // @Override
    // public String sendMailWithAttachment(EmailDTO emailDTO) {
    // MimeMessage mime = mailSender.createMimeMessage();
    // try {
    // MimeMessageHelper helper = new MimeMessageHelper(mime, true);
    // helper.setFrom(sender);
    // helper.setTo(emailDTO.getRecipient());
    // helper.setSubject(emailDTO.getSubject());
    // helper.setText(emailDTO.getMsgBody());

    // FileSystemResource file = new FileSystemResource(new
    // File(emailDTO.getAttachment()));
    // helper.addAttachment(file.getFilename(), file);

    // mailSender.send(mime);
    // return "Mail Sent Successfully with attachment!";
    // } catch (MessagingException e) {
    // return "Error while Sending Mail with attachment: " + e.getMessage();
    // }
    // }

}
