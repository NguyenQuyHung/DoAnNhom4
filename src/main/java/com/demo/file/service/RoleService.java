package com.demo.file.service;

import com.demo.file.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public interface RoleService {
    Optional<RoleEntity> findById(Long id);
}

