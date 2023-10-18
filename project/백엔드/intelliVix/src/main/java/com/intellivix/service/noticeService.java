package com.intellivix.service;

import com.intellivix.model.admin;
import com.intellivix.model.notice;
import com.intellivix.repository.noticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class noticeService {

    private final noticeRepository noticeRepository;


    public Map<String, Object> readNotice(String title, String startDate, String endDate, int page, int size) throws ParseException {
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

        Page<notice> notice = noticeRepository.findByTitleContainingAndCreateDateBetween(
            title, startT, endT, sortedByNoDesc
        );

        Map<String, Object> resp = new HashMap<>();

        resp.put("notice", notice.getContent());
        resp.put("allPage", notice.getTotalPages());
        resp.put("count", notice.getTotalElements());

        return resp;

    }
    public Map<String, Object> readNotice(int page, int size) {
        Pageable sortedByNoDesc = PageRequest.of(page, size, Sort.by("no").descending());
        Page<notice> notice = noticeRepository.findAll(sortedByNoDesc);

        Map<String, Object> resp = new HashMap<>();

        resp.put("notice", notice.getContent());
        resp.put("allPage", notice.getTotalPages());
        resp.put("count", notice.getTotalElements());

        return resp;
    }

    public void deleteNotice (List<Long> no) throws Exception{
        long deleteCount = noticeRepository.deleteAllByNoIn(no);

        if (deleteCount == 0 ) {
            throw new Exception("delete err");
        }
    }
    public void insertNotice (notice notice) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        notice.setCreateDate(timestamp);
        notice.setUpdateDate(timestamp);
        notice.setViews(0L);
        noticeRepository.save(notice);
    }

    public notice detailNotice(Long no) {
       Optional<notice> notice = noticeRepository.findByNo(no);
        if (notice.isPresent()) {
            return notice.get();
        }
        throw new EntityNotFoundException("cant find detail!!");
    }

    public void updateViews(Long no) {
        noticeRepository.updateView(no);
    }

    public void updateId(Long no, String title, String content) {

        Optional<notice> selectId = noticeRepository.findById(no);

        if (selectId.isPresent()) {
            if (title != null) {
                selectId.get().setTitle(title);
            }
            if (content != null) {
                selectId.get().setContent(content);
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            selectId.get().setUpdateDate(timestamp);

            noticeRepository.save(selectId.get());
        }
    }


//    public Optional<List<admin>> readAdmins() {
//
//        return noticeRepository.findTop10ByOrderByNoDesc();
//    }

}
