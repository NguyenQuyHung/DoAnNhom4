package com.demo.file.service;

import com.demo.file.entity.UserEntity;
import com.demo.file.payload.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface UserService extends UserDetailsService {

    UserEntity saveUser(UserEntity user);

    Optional<UserEntity> findByEmail(String email);

    int recoverPassword(String password, String email);
    UserDto getProfile(String email);
    void updateProfile(UserDto userDto, String email);
    boolean validateCredentials(String username, String password);
    List<UserDto> getAll();

    void updateUserStatus(Long id, Integer status);
    Long findIdByEmail(String email);

    List<UserDto> getAllAdmins();
    UserEntity findById(Long id);
    UserDto findUserDtoById(Long id);

}

