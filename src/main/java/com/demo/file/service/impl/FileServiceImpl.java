package com.demo.file.service.impl;

import com.demo.file.entity.FileEntity;
import com.demo.file.entity.FileTypeEntity;
import com.demo.file.entity.UserEntity;
import com.demo.file.payload.dto.FileDto;
import com.demo.file.repository.FileRepository;
import com.demo.file.repository.FileTypeRepository;
import com.demo.file.repository.UserRepository;
import com.demo.file.service.FileService;
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
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileTypeRepository fileTypeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public FileServiceImpl(FileRepository fileRepository, FileTypeRepository fileTypeRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.fileRepository = fileRepository;
        this.fileTypeRepository = fileTypeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FileEntity save(FileEntity fileEntity) {
        return fileRepository.save(fileEntity);
    }

    @Override
    public List<FileDto> getAllIfActive() {
        List<FileEntity> fileEntities = fileRepository.findAllByStatus(1);

        List<FileDto> fileDtos = fileEntities.stream().map(fileEntity -> {
            FileDto fileDto = modelMapper.map(fileEntity, FileDto.class);
            fileDto.setLink(fileEntity.getLink());
            fileDto.setOriginalFilename(fileEntity.getOriginalFilename());
            fileDto.setStatus(fileEntity.getStatus());
            fileDto.setCreatedDate(fileEntity.getCreatedAt().toString());
            if(fileEntity.getUserEntity() != null) {
                fileDto.setUserId(fileEntity.getUserEntity().getId());
                fileDto.setEmail(fileEntity.getUserEntity().getEmail());
            }
            if(fileEntity.getFileTypeEntity() != null) {
                fileDto.setFileTypeId(fileEntity.getFileTypeEntity().getId());
                fileDto.setFileTypeName(fileEntity.getFileTypeEntity().getName());
            }
            return fileDto;
        }).collect(Collectors.toList());
        return fileDtos;
    }

    @Override
    public List<FileDto> getAll() {
        List<FileEntity> fileEntities = fileRepository.findAll();

        List<FileDto> fileDtos = fileEntities.stream().map(fileEntity -> {
            FileDto fileDto = modelMapper.map(fileEntity, FileDto.class);
            fileDto.setStatus(fileEntity.getStatus());
            fileDto.setCreatedDate(fileEntity.getCreatedAt().toString());
            if(fileEntity.getFileTypeEntity() != null) {
                fileDto.setFileTypeId(fileEntity.getFileTypeEntity().getId());
                fileDto.setFileTypeName(fileEntity.getFileTypeEntity().getName());
            }

            if(fileEntity.getUserEntity() != null) {
                fileDto.setUserId(fileEntity.getUserEntity().getId());
                fileDto.setEmail(fileEntity.getUserEntity().getEmail());
            }
            return fileDto;
        }).collect(Collectors.toList());
        return fileDtos;
    }

    @Override
    public List<FileEntity> getTop4() {
        return fileRepository.getTop4();
    }

    @Override
    public Long countProductActive() {
        return fileRepository.countByStatus(1);
    }

    @Override
    public void save(FileDto fileDto, MultipartFile file, String username) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setCreatedAt(LocalDateTime.now());
        fileEntity.setUpdatedAt(LocalDateTime.now());
        fileEntity.setCreatedBy(username);
        fileEntity.setUpdatedBy(username);

        fileEntity.setLink(fileDto.getLink());
        fileEntity.setDescription(fileDto.getDescription());
        fileEntity.setStatus(fileDto.getStatus());

        if (!file.isEmpty()) {
            try {
                String uniqueCode = UUID.randomUUID().toString();
                byte[] bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/files/" + uniqueCode +"-" + file.getOriginalFilename());
                Files.write(path, bytes);
                fileEntity.setOriginalFilename(file.getOriginalFilename());
                fileEntity.setLink(uniqueCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        UserEntity userEntity = userRepository.findById(fileDto.getUserId()).get();
        fileEntity.setUserEntity(userEntity);

        FileTypeEntity fileTypeEntity = fileTypeRepository.findById(fileDto.getFileTypeId()).get();
        fileEntity.setFileTypeEntity(fileTypeEntity);

        fileRepository.save(fileEntity);
    }

    @Override
    public void update(FileDto fileDto , MultipartFile file, String username) {
        FileEntity fileEntity = modelMapper.map(fileDto, FileEntity.class);
        fileEntity.setId(fileDto.getId());
        fileEntity.setCreatedAt(LocalDateTime.now());
        fileEntity.setUpdatedAt(LocalDateTime.now());
        fileEntity.setCreatedBy(username);
        fileEntity.setUpdatedBy(username);

        fileEntity.setLink(fileDto.getLink());
        fileEntity.setDescription(fileDto.getDescription());
        fileEntity.setStatus(fileDto.getStatus());

        if (!file.isEmpty()) {
            try {
                String uniqueCode = UUID.randomUUID().toString();
                byte[] bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/files/" + uniqueCode +"-" + file.getOriginalFilename());
                Files.write(path, bytes);
                fileEntity.setLink(uniqueCode);
                fileEntity.setOriginalFilename(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        UserEntity userEntity = userRepository.findById(fileDto.getUserId()).get();
        fileEntity.setUserEntity(userEntity);

        FileTypeEntity fileTypeEntity = fileTypeRepository.findById(fileDto.getFileTypeId()).get();
        fileEntity.setFileTypeEntity(fileTypeEntity);
        fileRepository.save(fileEntity);
    }

    @Override
    public FileDto findById(Long id) {
        FileEntity file = fileRepository.findById(id).get();
        FileDto fileDto = modelMapper.map(file, FileDto.class);
        fileDto.setUserId(file.getUserEntity().getId());
        fileDto.setEmail(file.getUserEntity().getEmail());
        fileDto.setCreatedDate(file.getCreatedAt().toString());
        if(file.getFileTypeEntity() != null) {
            fileDto.setFileTypeId(file.getFileTypeEntity().getId());
            fileDto.setFileTypeName(file.getFileTypeEntity().getName());
        }
        return fileDto;
    }

    @Override
    public FileEntity findEntityById(Long id) {
        return fileRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id, String username) {
        fileRepository.deleteById(id);
    }
}
