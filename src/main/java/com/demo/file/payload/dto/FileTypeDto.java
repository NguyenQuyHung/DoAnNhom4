package com.demo.file.payload.dto;

import lombok.Data;

@Data
public class FileTypeDto {
    private Long id;
    private String name;
    private Long numberOfFiles;
    private String createdDate;
    private Integer status;
}
