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
@Table(name="notice")
public class notice implements Comparable<notice> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String title;
    private String content;
    private Long views;
    @Column(name = "create_admin")
    private String createAdmin;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "create_date")
    private Timestamp createDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "update_date")
    private Timestamp updateDate;


    @Override
    public int compareTo(notice arg0) {
        Long targetNo = arg0.getNo();
        return no.compareTo(targetNo);
    }
}
