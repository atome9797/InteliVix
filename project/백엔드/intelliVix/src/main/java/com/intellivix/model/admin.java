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
@Table(name="admin")
public class admin implements Comparable<admin> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private Long grade;
    private String id;
    private String password;
    private String name;
    private String phone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp create_date;

    @Override
    public int compareTo(admin arg0) {
        Long targetNo = arg0.getNo();
        return no.compareTo(targetNo);
    }
}
