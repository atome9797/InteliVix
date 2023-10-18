package com.intellivix.service;


import com.intellivix.model.admin;
import com.intellivix.repository.adminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class adminService {

    private final adminRepository adminRepository;

//    public admin loginAdmin(String id) throws UsernameNotFoundException {
//        Optional<admin> admin = adminRepository.findByIdEquals(id);
//        if (admin.isPresent()) {
//            return admin.get();
//        }
//        throw new UsernameNotFoundException("cant find LoginAdmin!!");
//    }

    public Long dupCheck(String id) {
        Long cnt = adminRepository.checkDup(id);
        return cnt;
    }

    public admin adminInfo(String id) {
        Optional<admin> admin = adminRepository.findByIdEquals(id);
        if (admin.isPresent()) {
            return admin.get();
        }
        throw new EntityNotFoundException("cant find adminInfo!!");
    }

    public admin detailAdmin(Long no) {
        Optional<admin> admin = adminRepository.findByNoEquals(no);
        if (admin.isPresent()) {
            return admin.get();
        }
        throw new EntityNotFoundException("cant find adminInfo!!");
    }

    public Map<String, Object> readAdmin(String searchValue, int page, int size) {
        Pageable sortedByNoDesc = PageRequest.of(page, size, Sort.by("no").descending());
       Page<admin> admin = adminRepository.searchByNameAndPhone(searchValue, sortedByNoDesc);
        Map<String, Object> resp = new HashMap<>();

        resp.put("admin", admin.getContent());
        resp.put("allPage", admin.getTotalPages());
        resp.put("count", admin.getTotalElements());

        return resp;
    }

    public Map<String, Object> readAdmins(int page, int size) {

        Pageable sortedByNoDesc = PageRequest.of(page, size, Sort.by("no").descending());

        Page<admin> admin = adminRepository.findAll(sortedByNoDesc);
        Map<String, Object> resp = new HashMap<>();

        resp.put("admin", admin.getContent());
        resp.put("allPage", admin.getTotalPages());
        resp.put("count", admin.getTotalElements());

        return resp;
    }

    public void signAdmin(admin admin) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        admin.setCreate_date(timestamp);
        adminRepository.save(admin);
    }

    public void updateId(Long no, Long grade, String password) {

        Optional<admin> selectId = adminRepository.findById(no);

        if (selectId.isPresent()) {
            if (grade != null) {
                selectId.get().setGrade(grade);
            }
            if (password != null) {
                selectId.get().setPassword(password);
            }

            adminRepository.save(selectId.get());
        }

    }

    public void deleteAdmin (List<Long> no) throws Exception{
        long deleteCount = adminRepository.deleteAllByNoIn(no);

        if (deleteCount == 0 ) {
            throw new Exception("delete err");
        }
    }
}
