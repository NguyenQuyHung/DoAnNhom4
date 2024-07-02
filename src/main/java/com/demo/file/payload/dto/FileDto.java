package com.demo.file.payload.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FileDto {
    private Long id;
    private String link;
    private Long fileTypeId;
    private String fileTypeName;
    private String description;
    private String originalFilename;
    private Long userId;
    private String email;
    private String createdDate;
    private Integer status;
}
