package com.intellivix.repository;

import com.intellivix.model.newsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface newsFileRepository extends JpaRepository<newsFile, Long> {

    public newsFile findByNewsNo(@Param("news_no") Long NewsNo);

    @Transactional
    Long deleteByNewsNo(Long newsNo);

}
