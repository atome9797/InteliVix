package com.intellivix.model;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="news")
public class news implements Comparable<news> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String title;
    private String url;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "create_date")
    private Timestamp createDate;
    @Column(name = "report_date")
    private String reportDate;
    @Column(name = "create_admin")
    private String createAdmin;

    @Override
    public int compareTo(news arg0) {
        Long targetNo = arg0.getNo();
        return no.compareTo(targetNo);
    }
}
