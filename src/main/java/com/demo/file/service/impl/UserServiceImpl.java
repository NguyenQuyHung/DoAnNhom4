package com.demo.file.service.impl;

import com.demo.file.entity.RoleEntity;
import com.demo.file.entity.UserEntity;
import com.demo.file.payload.dto.UserDto;
import com.demo.file.repository.*;
import com.demo.file.service.UserService;
import com.demo.file.config.SecurityUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserRepository userRepository1, RoleRepository roleRepository, CustomerRepository customerRepository, ModelMapper modelMapper){
        this.userRepository = userRepository1;
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }



    @Override
    public Long findIdByEmail(String email) {
        return userRepository.findIdByEmail(email);
    }

    @Override
    public List<UserDto> getAllAdmins() {
        List<UserEntity> userEntities = userRepository.getAllAdmins();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<UserDto> userDtos = userEntities.stream().map(userEntity -> {
            UserDto userDto = modelMapper.map(userEntity,UserDto.class);

            if(userEntity.getCreatedAt() != null) {
                String formattedDate = userEntity.getCreatedAt().format(formatter);
                userDto.setCreatedDate(formattedDate);
            }
            return userDto;
        }).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserDto findUserDtoById(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        UserDto userDto = modelMapper.map(userEntity,UserDto.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(userEntity.getCreatedAt() != null) {
            String formattedDate = userEntity.getCreatedAt().format(formatter);
            userDto.setCreatedDate(formattedDate);
        }
        return userDto;
    }

    @Override
    public int recoverPassword(String password, String email) {
        return userRepository.updatePassword(password, email);
    }

    @Override
    public UserDto getProfile(String email) {
        UserEntity user = userRepository.findByEmailAndStatus(email, 1).get();
        UserDto userDto = modelMapper.map(user, UserDto.class);

        userDto.setNewPassword("");
        userDto.setPassword("");
        return userDto;
    }

    @Override
    public void updateProfile(UserDto userDto, String email) {
        try{
            UserEntity userEntity = userRepository.findByEmailAndStatus(email, 1).orElseThrow();

            userEntity.setUpdatedBy(email);
            userEntity.setUpdatedAt(LocalDateTime.now());

            userEntity.setEmail(userDto.getEmail());
            if (!userDto.getNewPassword().isEmpty()) {
                userEntity.setPassword(passwordEncoder.encode(userDto.getNewPassword()));
            } else {
                userEntity.setPassword(userEntity.getPassword());
            }
            userRepository.save(userEntity);
        }catch(Exception e) {
            System.out.println(e.getMessage());

        }
    }



    @Override
    public boolean validateCredentials(String username, String password) {
        return userRepository.existsByEmailAndPassword(username, passwordEncoder.encode(password));
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.getAll();
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        UserEntity user = userRepository.findById(id).get();
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, 1);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userByUsername = userRepository.findByEmailAndStatus(username, 1);
        if (userByUsername.isEmpty()) {
            System.out.println("Could not find user with that email: {}");
            throw new UsernameNotFoundException("Invalid credentials!");
        }
        UserEntity user = userByUsername.get();
        System.out.println(user);
        if (!user.getEmail().equals(username)) {
            System.out.println("Could not find user with that username: {}");
            throw new UsernameNotFoundException("Invalid credentials!");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (RoleEntity role : user.getRoleEntities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().name()));
        }
        System.out.println(grantedAuthorities);
        return new SecurityUser(user.getEmail(), user.getPassword(), true, true, true, true, grantedAuthorities,
                user.getEmail());
    }
}
