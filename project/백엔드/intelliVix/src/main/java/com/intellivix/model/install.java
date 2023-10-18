package com.intellivix.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name="install")
public class install {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp create_date;
    private String create_admin;
    private String model_name;
    private String version;
    private String type;
    private String hash;

}
