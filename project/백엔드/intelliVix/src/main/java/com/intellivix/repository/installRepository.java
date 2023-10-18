package com.intellivix.repository;

import com.intellivix.model.install;
import com.intellivix.model.procurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface installRepository extends JpaRepository<install, Long> {

    @Query(value="SELECT * FROM install a " +
            "WHERE 1=1 " +
            "AND IF(?3 IS NOT Null, (a.create_date > ?3), 1=1)"+
            "AND IF(?4 IS NOT Null, (a.create_date < ?4), 1=1)"+
            "AND (CASE WHEN ?1 = 'ALL' THEN  (a.model_name LIKE CONCAT('%',?2,'%')" +
            "                                   OR a.version LIKE CONCAT('%',?2,'%')" +
            "                                   OR a.type LIKE CONCAT('%',?2,'%')" +
            "                                   OR a.hash LIKE CONCAT('%',?2,'%')" +
            "            ) WHEN ?1 = 'model_name' THEN a.model_name LIKE CONCAT('%',?2,'%')" +
            "              WHEN ?1 = 'version' THEN a.version LIKE CONCAT('%',?2,'%')" +
            "              WHEN ?1 = 'type' THEN a.type LIKE CONCAT('%',?2,'%')" +
            "              WHEN ?1 = 'hash' THEN a.hash LIKE CONCAT('%',?2,'%') end)", nativeQuery = true)
    Page<install> searchInstall(String searchType, @Nullable String searchValue, @Nullable Timestamp startDate, @Nullable Timestamp endDate, Pageable pageable);
    Optional<install> findByNo(Long no);

    @Transactional
    long deleteAllByNoIn(List<Long> no);


}
