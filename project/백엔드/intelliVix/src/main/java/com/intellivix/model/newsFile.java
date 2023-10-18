package com.intellivix.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class newsFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;
    private Long newsNo;
    private String origFileName;
    private String saveFileName;
    private int fileSize;
    private String fileExt;
    private String filePath;
    private String deleteYn;

    @CreatedDate
    private LocalDateTime regTime;

    @Builder
    public newsFile(Long no, Long newsNo, String origFileName,
    String saveFileName,
    int fileSize,
    String fileExt,
    String filePath,
    String deleteYn) {
        this.no = no;
        this.newsNo = newsNo;
        this.origFileName = origFileName;
        this.saveFileName = saveFileName;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
        this.filePath = filePath;
        this.deleteYn = deleteYn;
    }

}
