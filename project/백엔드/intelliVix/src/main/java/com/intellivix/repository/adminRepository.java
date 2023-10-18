package com.intellivix.repository;


import com.intellivix.model.notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intellivix.model.admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface adminRepository extends JpaRepository<admin, Long> {
    //Optional<List<admin>> findTop10ByNoGreaterThanEqualAndNameContainingOrderByNoAsc(Long no, String name);
    Optional<admin> findByIdEquals(String id);
    Optional<admin> findByNoEquals(Long no);

    @Transactional
    long deleteAllByNoIn(List<Long> no);
    //Optional<List<admin>> findTop10ByOrderByNoDesc();

//    Page<admin> findByNameOrPhoneOrderByNoAsc(@Nullable String name, @Nullable String phone, Pageable pageable);



    @Query("SELECT a FROM admin a WHERE (a.name LIKE %:value% OR a.phone LIKE %:value%)")
    Page<admin> searchByNameAndPhone(@Param("value") String value, Pageable pageable);

    Page<admin> findAll(Pageable pageable);

    @Query("SELECT COUNT(a.id) FROM admin a WHERE a.id = :id")
    Long checkDup(@Param("id") String id);

}
