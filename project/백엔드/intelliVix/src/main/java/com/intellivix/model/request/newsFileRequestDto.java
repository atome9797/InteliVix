package com.intellivix.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class newsFileRequestDto {
    private Long id;
    private Long[] idArr;
    private String fileId;
}
