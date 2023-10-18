package com.intellivix.repository;

import com.intellivix.model.news;
import com.intellivix.model.notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface newsRepository extends JpaRepository<news, Long> {

    @Nullable
//    Optional<List<news>> findTop10ByNoGreaterThanEqualAndTitleContainingAndCreateDateBetweenOrderByNoAsc(@Nullable Long no, @Nullable String title, @Nullable Timestamp startDate, @Nullable Timestamp endDate);
    Page<news> findByTitleContainingAndCreateDateBetween(@Nullable String title, @Nullable Timestamp startDate, @Nullable Timestamp endDate, Pageable pageable);
    Page<news> findAll(Pageable pageable);
    Optional<news> findByNo(Long no);


    Optional<List<news>> findTop10ByNoGreaterThanEqualOrderByNoAsc(@Nullable Long no);
    @Transactional
    Long deleteAllByNoIn(List<Long> no);


}
