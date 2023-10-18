package com.intellivix.controller;

import com.intellivix.enums.StatusEnum;
import com.intellivix.model.message;
import com.intellivix.model.news;
import com.intellivix.model.notice;
import com.intellivix.service.newsService;
import com.intellivix.service.noticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final newsService newsService;

    message msg = new message();
    HttpHeaders headers = new HttpHeaders();

    @GetMapping("/list")
    public ResponseEntity<message> readNews (@RequestParam(required = false) String title, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @RequestParam(required = false) int page, @RequestParam(defaultValue = "10") int size ) throws ParseException {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Map<String, Object> news;
        if ((startDate == null || endDate == null) || startDate.equals("") || endDate.equals("")) {
            news = newsService.readNews(page, size);
        } else {
            news = newsService.readNews(title, startDate, endDate, page, 10);
        }
        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(news);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<message> detailNews (@RequestParam Long no) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        news news = newsService.detailNews(no);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(news);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<message> deleteNews (@RequestBody List<Long> no) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        newsService.deleteNews(no);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(no+" 삭제 성공");

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @PostMapping("/write")
    public ResponseEntity<message> writeNews (news news, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        try {
            if (newsService.save(news, multipartHttpServletRequest) == null) {
                throw new Exception("Fail to wrte News");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(news);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }
//    public ResponseEntity<message> writeNews (@RequestParam String title, @RequestParam String url, @RequestParam List<MultipartFile> file) throws Exception {
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        news emptyNews = new news();
//
//        emptyNews.setNo(1L);
//        emptyNews.setTitle(title);
//        emptyNews.setUrl(url);
//       newsService.insertNews(emptyNews, file);
//
//        msg.setStatus(StatusEnum.OK);
//        msg.setMessage("성공 코드");
//       // msg.setData(news);
//
//        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
//    }

    @PutMapping("/modify")
    public ResponseEntity modifyAdmin(news news, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        try {
            newsService.updateId(news, multipartHttpServletRequest);
            msg.setStatus(StatusEnum.OK);
            msg.setMessage("성공 코드");
            msg.setData(news);

            return new ResponseEntity<>(msg, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getCause());
        }
    }

}
