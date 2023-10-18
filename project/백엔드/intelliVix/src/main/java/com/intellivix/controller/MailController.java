package com.intellivix.controller;

import com.intellivix.model.message;
import com.intellivix.service.mailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/mail")
public class MailController {

    private final mailService mailService;

    @PostMapping(value="/")
    public ResponseEntity<message> sendMail(HttpServletRequest req) throws MessagingException {
        String to = req.getParameter("eMail");
        String name = req.getParameter("name");
        String manager = req.getParameter("manager");
        String tel = req.getParameter("tel");
        String contents = req.getParameter("contents");
        contents = contents.replace("<br>","\n\r");

        manager = manager == null ? "" : manager;

        String text = "이름(기업명): "+name+"\n담당자명: "+manager+"\nE-mail: "+to+"\n전화번호: "+tel+"\n\n ====문의내용===\n"+contents;

        mailService.sendSimpleMessage(to,text);
        return null;
    }

}
