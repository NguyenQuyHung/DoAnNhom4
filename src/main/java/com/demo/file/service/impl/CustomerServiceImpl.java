package com.demo.file.service.impl;

import com.demo.file.entity.CustomerEntity;
import com.demo.file.entity.UserEntity;
import com.demo.file.payload.dto.CustomerDto;
import com.demo.file.repository.*;
import com.demo.file.service.CustomerService;
import com.demo.file.utils.DateUtils;
import com.demo.file.utils.ImageUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public CustomerEntity saveEntity(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    @Override
    public Long countCustomers() {
        return customerRepository.countByStatus(1);
    }

    @Override
    public List<CustomerDto> getAll() {
        List<CustomerEntity> customerEntities = customerRepository.getAll();
        return customerEntities.stream().map(customerEntity -> {
            CustomerDto customerDto = modelMapper.map(customerEntity, CustomerDto.class);
            if(customerEntity.getUserEntity() == null) {
                customerDto.setEmail("");
            }
            else {
                customerDto.setEmail(customerEntity.getUserEntity().getEmail());
            }
            return customerDto;
        }).collect(Collectors.toList());
    }

    @Override
    public CustomerDto getProfile(String email) {
        UserEntity userEntity = userRepository.findByEmailAndStatus(email, 1).get();
        CustomerEntity customerEntity = userEntity.getCustomerEntity();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerEntity.getId());
        customerDto.setFullName(customerEntity.getFullName());
        customerDto.setAddress(customerEntity.getAddress());
        if(customerEntity.getDob() != null) {
            customerDto.setDob(customerEntity.getDob().toString());
        } else customerDto.setDob("");

        if(customerEntity.getGender() != null) {
            customerDto.setGender(customerEntity.getGender());
        } else customerDto.setGender("");

        customerDto.setAddress(customerEntity.getAddress());
        customerDto.setEmail(email);
        customerDto.setPhoneNumber(userEntity.getPhoneNumber());

        customerDto.setPassword("");
        customerDto.setNewPassword("");
        return customerDto;
    }

    @Override
    @Transactional
    public void updateProfile(CustomerDto customerDto, String email) {
        UserEntity userEntity = userRepository.findByEmailAndStatus(email, 1).get();

        CustomerEntity customerEntity = userEntity.getCustomerEntity();
        CustomerEntity saveCustomer = new CustomerEntity();
        saveCustomer.setId(customerDto.getId());
        saveCustomer.setCreatedAt(customerEntity.getCreatedAt());
        saveCustomer.setCreatedBy(customerEntity.getCreatedBy());
        saveCustomer.setStatus(1);
        saveCustomer.setId(customerDto.getId());
        saveCustomer.setPhone(customerDto.getPhoneNumber());
        saveCustomer.setFullName(customerDto.getFullName());
        saveCustomer.setAddress(customerDto.getAddress());
        String DATE_FORMAT="yyyy-MM-dd";
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate date = LocalDate.parse(customerDto.getDob(), formatter);
        saveCustomer.setDob(date);
        saveCustomer.setGender(customerDto.getGender());
        saveCustomer.setUpdatedBy(email);
        saveCustomer.setUpdatedAt(LocalDateTime.now());
        saveCustomer.setCreatedAt(userEntity.getCreatedAt());
        saveCustomer.setCreatedBy(userEntity.getCreatedBy());

        saveCustomer.setDob(DateUtils.toDate(customerDto.getDob()));
        customerRepository.save(saveCustomer);

        userEntity.setEmail(customerDto.getEmail());
        if(Objects.equals(customerDto.getNewPassword(), "")) {
            userEntity.setPassword(userEntity.getPassword());
        }
        else {
            userEntity.setPassword(passwordEncoder.encode(customerDto.getNewPassword()));
        }
        userEntity.setPhoneNumber(customerDto.getPhoneNumber());
        userEntity = userRepository.save(userEntity);
        saveCustomer.setUserEntity(userEntity);
        saveCustomer = customerRepository.save(saveCustomer);
        userEntity.setCustomerEntity(saveCustomer);
        userRepository.save(userEntity);
    }

    @Override
    public Long getIdByEmail(String email) {
        return customerRepository.findByUserEntity_Email(email).getId();
    }

    @Override
    public CustomerEntity findByEmail(String email) {
        return customerRepository.findByUserEntity_Email(email);
    }

    @Override
    public CustomerDto findById(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).get();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerEntity.getId());
        customerDto.setFullName(customerEntity.getFullName());
        customerDto.setAddress(customerEntity.getAddress());
        if(customerEntity.getDob() != null) {
            customerDto.setDob(customerEntity.getDob().toString());
        }
        else {
            customerDto.setDob("");
        }

        customerDto.setAddress(customerEntity.getAddress());
        customerDto.setPassword("");
        customerDto.setNewPassword("");
        return customerDto;
    }
}
