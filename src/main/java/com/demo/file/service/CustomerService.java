package com.demo.file.service;

import com.demo.file.entity.CustomerEntity;
import com.demo.file.payload.dto.CustomerDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public interface CustomerService {
    CustomerEntity saveEntity(CustomerEntity customerEntity);
    Long countCustomers();

    List<CustomerDto> getAll();
    CustomerDto getProfile(String email);
    void updateProfile(CustomerDto customerDto, String email);
    Long getIdByEmail(String email);
    CustomerEntity findByEmail(String email);
    CustomerDto findById(Long customerId);
}

