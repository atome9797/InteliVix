package com.intellivix.service;

import com.intellivix.model.install;
import com.intellivix.model.procurement;
import com.intellivix.repository.installRepository;
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
public class installService {

    private final installRepository installRepository;

    public Map<String, Object> searchList(String searchType, String searchValue, String startDate, String endDate, int page, int size) throws ParseException {
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

        Page<install> procurement = installRepository.searchInstall(searchType, searchValue, startT, endT, sortedByNoDesc);

        Map<String, Object> resp = new HashMap<>();

        resp.put("install", procurement.getContent());
        resp.put("allPage", procurement.getTotalPages());
        resp.put("count", procurement.getTotalElements());

        return resp;
    }

    public void insertInstall (install install) {
        if (install.getNo() != null) {
            Optional<install> prevData = installRepository.findByNo(install.getNo());
            install.setCreate_date(prevData.get().getCreate_date());
            install.setCreate_admin(prevData.get().getCreate_admin());
            installRepository.save(install);
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            install.setCreate_date(timestamp);
            installRepository.save(install);
        }
    }


    public install detailInstall(Long no) {
        Optional<install> install = installRepository.findByNo(no);
        if (install.isPresent()) {
            return install.get();
        }
        throw new EntityNotFoundException("cant find detail!!");
    }

    public void deleteInstall (List<Long> no) throws Exception{
        long deleteCount = installRepository.deleteAllByNoIn(no);

        if (deleteCount == 0 ) {
            throw new Exception("delete err");
        }
    }

    public List<install> findAll() {

        return installRepository.findAll(Sort.by(Sort.Direction.DESC, "no"));
    }

}
