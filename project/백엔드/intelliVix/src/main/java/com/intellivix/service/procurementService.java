package com.intellivix.service;

import com.intellivix.model.notice;
import com.intellivix.model.procurement;
import com.intellivix.repository.procurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class procurementService {

    private final procurementRepository procurementRepository;

    public Map<String, Object> searchList(String searchValue, String division, String startDate, String endDate, int page, int size) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp startT = null;
        Timestamp endT = null;

        if (!Objects.equals(startDate, "")) {
            Date startD = df.parse(startDate);
            long time = startD.getTime();

            startT = new Timestamp(time);
        }

        if (!Objects.equals(endDate, "")) {
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

        Page<procurement> procurement = procurementRepository.searchProcurement(searchValue, division, startT, endT, sortedByNoDesc);

        Map<String, Object> resp = new HashMap<>();

        resp.put("procurement", procurement.getContent());
        resp.put("allPage", procurement.getTotalPages());
        resp.put("count", procurement.getTotalElements());

        return resp;

    }

    public void insertProcurement (procurement procurement) {
        if (procurement.getNo() != null) {
            Optional<procurement> prevData = procurementRepository.findByNo(procurement.getNo());
            procurement.setCreateDate(prevData.get().getCreateDate());
            procurement.setCreateAdmin(prevData.get().getCreateAdmin());
            procurementRepository.save(procurement);
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            procurement.setCreateDate(timestamp);
            procurementRepository.save(procurement);
        }
    }

    public procurement detailProcurement(Long no) {
        Optional<procurement> procurement = procurementRepository.findByNo(no);
        if (procurement.isPresent()) {
            return procurement.get();
        }
        throw new EntityNotFoundException("cant find detail!!");
    }

    public void deleteProcurement (List<Long> no) throws Exception{
        long deleteCount = procurementRepository.deleteAllByNoIn(no);

        if (deleteCount == 0 ) {
            throw new Exception("delete err");
        }
    }

    public Map<String, Object> ListProcurement () {

        Optional<List<procurement>> procurement_main = procurementRepository.findAllByDivisionEqualsAndSubjectEquals("PRO", "main");
       Optional<List<procurement>> procurement_sub = procurementRepository.findAllByDivisionEqualsAndSubjectEquals("PRO", "sub");

       Optional<List<procurement>> MAS_main = procurementRepository.findAllByDivisionEqualsAndSubjectEquals("MAS", "main");
       Optional<List<procurement>> MAS_sub = procurementRepository.findAllByDivisionEqualsAndSubjectEquals("MAS", "sub");

       Optional<List<procurement>> SW_main = procurementRepository.findAllByDivisionEqualsAndSubjectEquals("SW", "main");
       Optional<List<procurement>> SW_sub = procurementRepository.findAllByDivisionEqualsAndSubjectEquals("SW", "sub");


        Map<String, Object> resp = new HashMap<>();

        resp.put("procurement_main", procurement_main.orElse(null));
        resp.put("procurement_sub", procurement_sub.orElse(null));
        resp.put("MAS_main", MAS_main.orElse(null));
        resp.put("MAS_sub", MAS_sub.orElse(null));
        resp.put("SW_main", SW_main.orElse(null));
        resp.put("SW_sub", SW_sub.orElse(null));


        return resp;
    }
}
