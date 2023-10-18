package com.intellivix.controller;

import com.intellivix.enums.StatusEnum;
import com.intellivix.model.install;
import com.intellivix.model.message;
import com.intellivix.model.procurement;
import com.intellivix.service.installService;
import com.intellivix.service.procurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/install")
@RequiredArgsConstructor
public class installController {

    private final installService service;

    message msg = new message();
    HttpHeaders headers = new HttpHeaders();

    @GetMapping("/search/list")
    public ResponseEntity<message> searchList (@RequestParam(defaultValue = "ALL") String searchType, @RequestParam(defaultValue = "")String searchValue, @RequestParam(defaultValue = "") String startDate, @RequestParam(defaultValue = "") String endDate, @RequestParam(required = false) int page ) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> install =  service.searchList(searchType, searchValue, startDate, endDate, page ,10);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(install);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<message> getList () {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<install> install = service.findAll();

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(install);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<message> insertProcurement (@RequestBody install install) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        service.insertInstall(install);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(install);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<message> detailInstall (@RequestParam Long no) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        install install = service.detailInstall(no);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(install);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<message> deleteInstall (@RequestBody List<Long> no) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        service.deleteInstall(no);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(no+" 삭제 성공");

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }
}
