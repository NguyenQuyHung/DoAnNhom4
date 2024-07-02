package com.demo.file.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String dob;
    private String gender;
    private String address;
    private String phone;
    private String password;
    private String newPassword;
    private String avatar;
    private Long avatarId;
    private Integer status;
    private LocalDateTime createdAt;
    private String createdDate;

    public UserDto(){}

    public UserDto(Long id, String email, Integer status,LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
    }
}
