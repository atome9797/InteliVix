package com.intellivix.model.response;

import com.intellivix.model.newsFile;
import lombok.Getter;

@Getter
public class newsFileResponseDto {

    private String origFileName;
    private String saveFileName;
    private String filePath;

    public newsFileResponseDto(newsFile entity) {
        this.origFileName = entity.getOrigFileName();
        this.saveFileName = entity.getSaveFileName();
        this.filePath = entity.getFilePath();
    }

    @Override
    public String toString() {
        return "FileMstRespDto [origFileName=" +origFileName + ", saveFileName=" + saveFileName + ", filePath="+filePath +"]";
    }
}
