package com.intellivix.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "procurement")
public class procurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String division;
    private String subject;
    private String ino;
    private String product_name;
    private String model_name;
    private String product_standard;
    private String equipment;
    private String price;
    @Column(name = "create_admin")
    private String createAdmin;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "create_date")
    private Timestamp createDate;
}
