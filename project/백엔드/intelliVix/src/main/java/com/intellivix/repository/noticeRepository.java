package com.intellivix.repository;

import com.intellivix.model.notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface noticeRepository extends JpaRepository<notice, Long> {

    @Nullable
  //  Optional<List<notice>> findTop10ByNoGreaterThanEqualAndTitleContainingAndCreateDateBetweenOrderByNoAsc(@Nullable Long no, @Nullable String title, @Nullable Timestamp startDate, @Nullable Timestamp endDate);
    Page<notice> findByTitleContainingAndCreateDateBetween(@Nullable String title, @Nullable Timestamp startDate, @Nullable Timestamp endDate, Pageable pageable);

    Page<notice> findAll(Pageable pageable);
    Optional<notice> findByNo(Long no);
    @Transactional
    long deleteAllByNoIn(List<Long> no);
    long countAllByTitleContainingAndCreateDateBetween(@Nullable String title, @Nullable Timestamp startDate, @Nullable Timestamp endDate);


    @Transactional
    @Modifying
    @Query("UPDATE notice n set n.views = n.views + 1 where n.no = :no")
    int updateView(Long no);
}
