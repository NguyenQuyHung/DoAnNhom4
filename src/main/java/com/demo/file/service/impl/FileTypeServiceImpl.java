package com.demo.file.service.impl;

import com.demo.file.entity.FileEntity;
import com.demo.file.entity.FileTypeEntity;
import com.demo.file.entity.UserEntity;
import com.demo.file.payload.dto.FileDto;
import com.demo.file.payload.dto.FileTypeDto;
import com.demo.file.repository.FileRepository;
import com.demo.file.repository.FileTypeRepository;
import com.demo.file.repository.UserRepository;
import com.demo.file.service.FileService;
import com.demo.file.service.FileTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileTypeServiceImpl implements FileTypeService {
    private final FileTypeRepository fileTypeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public FileTypeServiceImpl(FileTypeRepository fileTypeRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.fileTypeRepository = fileTypeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public FileTypeEntity save(FileTypeEntity fileTypeEntity) {
        return fileTypeRepository.save(fileTypeEntity);
    }

    @Override
    public List<FileTypeDto> getAllIfActive() {
        List<FileTypeEntity> fileTypeEntities = fileTypeRepository.findAll();

        List<FileTypeDto> fileDtos = fileTypeEntities.stream().map(fileTypeEntity -> {
            FileTypeDto fileTypeDto = modelMapper.map(fileTypeEntity, FileTypeDto.class);
            fileTypeDto.setCreatedDate(fileTypeEntity.getCreatedAt().toString());
            fileTypeDto.setNumberOfFiles(fileTypeEntity.getFiles().stream().count());

            return fileTypeDto;
        }).collect(Collectors.toList());
        return fileDtos;
    }

    @Override
    public List<FileTypeDto> getAll() {
        List<FileTypeEntity> fileTypeEntities = fileTypeRepository.findAll();

        List<FileTypeDto> fileDtos = fileTypeEntities.stream().map(fileTypeEntity -> {
            FileTypeDto fileTypeDto = modelMapper.map(fileTypeEntity, FileTypeDto.class);
            fileTypeDto.setCreatedDate(fileTypeEntity.getCreatedAt().toString());
            fileTypeDto.setNumberOfFiles(fileTypeEntity.getFiles().stream().count());

            return fileTypeDto;
        }).collect(Collectors.toList());
        return fileDtos;
    }

    @Override
    public void save(FileTypeDto fileTypeDto, String username) {
        FileTypeEntity fileTypeEntity = new FileTypeEntity();
        fileTypeEntity.setCreatedAt(LocalDateTime.now());
        fileTypeEntity.setUpdatedAt(LocalDateTime.now());
        fileTypeEntity.setCreatedBy(username);
        fileTypeEntity.setUpdatedBy(username);

        fileTypeEntity.setName(fileTypeDto.getName());

        fileTypeRepository.save(fileTypeEntity);
    }

    @Override
    public void update(FileTypeDto fileTypeDto, String username) {
        FileTypeEntity fileTypeEntity = modelMapper.map(fileTypeDto, FileTypeEntity.class);
        fileTypeEntity.setId(fileTypeDto.getId());
        fileTypeEntity.setCreatedAt(LocalDateTime.now());
        fileTypeEntity.setUpdatedAt(LocalDateTime.now());
        fileTypeEntity.setCreatedBy(username);
        fileTypeEntity.setUpdatedBy(username);

        fileTypeEntity.setName(fileTypeDto.getName());

        fileTypeRepository.save(fileTypeEntity);
    }

    @Override
    public FileTypeDto findById(Long id) {
        FileTypeEntity fileTypeEntity = fileTypeRepository.findById(id).get();
        FileTypeDto fileTypeDto = modelMapper.map(fileTypeEntity, FileTypeDto.class);
        fileTypeDto.setNumberOfFiles(fileTypeEntity.getFiles().stream().count());
        fileTypeDto.setCreatedDate(fileTypeEntity.getCreatedAt().toString());
        return fileTypeDto;
    }

    @Override
    public FileTypeEntity findEntityById(Long id) {
        return fileTypeRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id, String username) {
        fileTypeRepository.deleteById(id);
    }
}
