package com.demo.file.service.impl;


import com.demo.file.entity.RoleEntity;
import com.demo.file.repository.RoleRepository;
import com.demo.file.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleRepository.findById(id);
    }
}
