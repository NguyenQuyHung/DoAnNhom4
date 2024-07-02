package com.demo.file.payload.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private String fullName;
    private String email;
    private String dob;
    private String gender;
    private String address;
    private String phoneNumber;
    private String password;
    private String newPassword;
    private String createdDate;
    private Integer status;
    private String imageLink;
    private Long imageId;
}
