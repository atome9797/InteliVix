package com.intellivix.controller;

import com.intellivix.enums.StatusEnum;
import com.intellivix.model.admin;
import com.intellivix.model.message;
import com.intellivix.model.notice;
import com.intellivix.service.noticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/api/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final noticeService noticeService;

    message msg = new message();
    HttpHeaders headers = new HttpHeaders();

    @GetMapping("/list")
    public ResponseEntity<message> readNotice (@RequestParam(required = false) String title, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @RequestParam(required = false) int page ) throws ParseException {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if ((startDate == null || endDate == null) || startDate.equals("") || endDate.equals("")) {
            Map<String, Object> notice = noticeService.readNotice(page, 10);

            msg.setStatus(StatusEnum.OK);
            msg.setMessage("성공 코드");
            msg.setData(notice);
        } else {
            Map<String, Object> notice = noticeService.readNotice(title, startDate, endDate, page, 10);

            msg.setData(notice);
            msg.setStatus(StatusEnum.OK);
            msg.setMessage("성공 코드");;
        }

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<message> deleteNotice (@RequestBody List<Long> no) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        noticeService.deleteNotice(no);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(no+" 삭제 성공");

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @PostMapping("write")
    public ResponseEntity<message> writeNotice (@RequestBody notice notice) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        noticeService.insertNotice(notice);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(notice);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<message> detailNotice (@RequestParam Long no) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        notice notice = noticeService.detailNotice(no);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(notice);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<message> detailMainNotice (@RequestParam Long no) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        noticeService.updateViews(no);
        notice notice = noticeService.detailNotice(no);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(notice);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity modifyAdmin(@RequestBody notice notice) throws Exception {
        try {
            noticeService.updateId(notice.getNo(), notice.getTitle(), notice.getContent());
            msg.setStatus(StatusEnum.OK);
            msg.setMessage("성공 코드");
            msg.setData("수정 완료");

            return new ResponseEntity<>(msg, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("");
        }
    }

}
