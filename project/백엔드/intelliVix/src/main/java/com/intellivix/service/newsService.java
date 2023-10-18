package com.intellivix.service;

import com.intellivix.model.news;
import com.intellivix.model.notice;
import com.intellivix.repository.newsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class newsService {


    private final newsRepository newsRepository;
    private final newsFileService newsFileService;

    public Map<String, Object> readNews(String title, String startDate, String endDate, int page, int size) throws ParseException {
        title = title == null ? "" : title;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp startT = null;
        Timestamp endT = null;

        if (startDate != null) {
            Date startD = df.parse(startDate);
            long time = startD.getTime();

            startT = new Timestamp(time);
        }

        if (endDate != null) {
            Date endD = df.parse(endDate);
            Calendar endC = Calendar.getInstance();
            endC.setTime(endD);
            endC.set(Calendar.HOUR_OF_DAY, 23);
            endC.set(Calendar.MINUTE, 59);
            endC.set(Calendar.SECOND, 59);

            endD = endC.getTime();
            long time = endD.getTime();

            endT = new Timestamp(time);
        }

        Pageable sortedByNoDesc = PageRequest.of(page, size, Sort.by("no").descending());

        Page<news> news = newsRepository.findByTitleContainingAndCreateDateBetween(
                title, startT, endT, sortedByNoDesc
        );

        Map<String, Object> resp = new HashMap<>();

        resp.put("news", news.getContent());
        resp.put("allPage", news.getTotalPages());
        resp.put("count", news.getTotalElements());

        return resp;

    }
    public Map<String, Object> readNews(int page, int size) {
        Pageable sortedByNoDesc = PageRequest.of(page, size, Sort.by("no").descending());
        Page<news> news = newsRepository.findAll(sortedByNoDesc);

        Map<String, Object> resp = new HashMap<>();

        resp.put("news", news.getContent());
        resp.put("allPage", news.getTotalPages());
        resp.put("count", news.getTotalElements());

        return resp;
    }

    public void deleteNews (List<Long> no) throws Exception{
        long deleteCount = newsRepository.deleteAllByNoIn(no);

        if (deleteCount == 0 ) {
            throw new Exception("delete err");
        } else {
            for (int i = 0; i<no.size(); i++) {
                newsFileService.deleteImage(no.get(i));
            }
        }
    }

    public news detailNews (Long no) throws Exception {
        Optional<news> news = newsRepository.findByNo(no);
        if (news.isPresent()) {
            return news.get();
        }
        throw new EntityNotFoundException("cant find detail!!");
    }

    public news save(news news, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        news.setCreateDate(timestamp);
        news result = newsRepository.save(news);

        newsFileService.uploadFile(multipartHttpServletRequest, result.getNo());

        return result;
    }

    public void updateId(news updateNews, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        Optional<news> prevNews = newsRepository.findByNo(updateNews.getNo());
        updateNews.setCreateDate(prevNews.get().getCreateDate());
        updateNews.setCreateAdmin(prevNews.get().getCreateAdmin());
        news result = newsRepository.save(updateNews);

        newsFileService.uploadFile(multipartHttpServletRequest, result.getNo());
        }
    }

//    public news insertNews (news news, List<MultipartFile> files) throws Exception {
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        news.setCreateDate(timestamp);
//
//        List<news> fileNews = fileHandler.parseFileInfo(news.getNo(), files);
//
//        if (fileNews.isEmpty()) {
//            ClassPathResource resource = new ClassPathResource("static/images/sample.jpg");
//            news.setFileName(resource.getFilename());
//            news.setFileUrl(resource.getPath());
//        } else {
//            List<news> newsList = new ArrayList<>();
//            for (news newsLists : newsList) {
//                newsList.add(newsRepository.save(newsLists));
//            }
//        }
//        return newsRepository.save(news);
//    }

