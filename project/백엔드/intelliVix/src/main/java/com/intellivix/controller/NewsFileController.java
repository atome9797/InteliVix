package com.intellivix.controller;

import com.intellivix.model.newsFile;
import com.intellivix.service.newsFileService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Controller
@RequestMapping(value ="/api/file")
public class NewsFileController {

    private final com.intellivix.service.newsFileService newsFileService;

    @GetMapping("/{newsNo}")
    public ResponseEntity<byte[]> newsThumbNail(@PathVariable("newsNo") Long newsNo) throws IOException {

        newsFile newsFile = newsFileService.findImage(newsNo);

        InputStream imageStream = new FileInputStream(newsFile.getFilePath()+newsFile.getSaveFileName());
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> pdfDownload(HttpServletResponse resp) throws IOException {

        String path = "/intellivix/tomcat/pdf/AI_Edge_Box.pdf";

        byte[] fileByte = FileUtils.readFileToByteArray(new File(path));

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition","attachment; fileName=\""+ URLEncoder.encode("AI_Edge_Box.pdf","UTF-8")+"\";");
        resp.setHeader("Content-Transfer-Encoding","binary");

        resp.getOutputStream().write(fileByte);
        resp.getOutputStream().flush();
        resp.getOutputStream().close();

        return new ResponseEntity<>(fileByte, HttpStatus.OK);
    }


}
