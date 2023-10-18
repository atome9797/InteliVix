package com.intellivix.repository;

import com.intellivix.model.notice;
import com.intellivix.model.procurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface procurementRepository extends JpaRepository<procurement, Object> {

    @Query(value = "SELECT * FROM procurement a WHERE 1=1 " +
            "AND IF(?3 IS NOT Null, (a.create_date > ?3), 1=1)"+
            "AND IF(?4 IS NOT Null, (a.create_date < ?4), 1=1)"+
            "AND a.division LIKE CONCAT('%',?2,'%')" +
            "AND (a.ino LIKE CONCAT('%',?1,'%') " +
            "OR a.product_name LIKE CONCAT('%',?1,'%') " +
            "OR a.model_name LIKE CONCAT('%',?1,'%') " +
            "OR a.product_name LIKE CONCAT('%',?1,'%') " +
            "OR a.product_standard LIKE CONCAT('%',?1,'%') " +
            "OR a.equipment LIKE CONCAT('%',?1,'%') " +
            "OR a.price LIKE CONCAT('%',?1,'%') " +
            "OR a.create_admin LIKE CONCAT('%',?1,'%'))",
            nativeQuery = true
                )
    Page<procurement> searchProcurement(String value, @Nullable String division, @Nullable Timestamp startDate, @Nullable Timestamp endDate, Pageable pageable);

    Optional<procurement> findByNo(Long no);

//    Page<procurement> findAll(Pageable pageable);
    Optional<List<procurement>> findAllByDivisionEqualsAndSubjectEquals(String division, String subject);

    @Transactional
    long deleteAllByNoIn(List<Long> no);
}
