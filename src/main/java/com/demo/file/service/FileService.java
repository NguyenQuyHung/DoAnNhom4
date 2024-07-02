package com.demo.file.service;


import com.demo.file.entity.FileEntity;
import com.demo.file.payload.dto.FileDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    FileEntity save(FileEntity fileEntity);
    List<FileDto> getAllIfActive();

    List<FileDto> getAll();
    List<FileEntity> getTop4();

    Long countProductActive();
    void save(FileDto productDto, MultipartFile file, String username);
    void update(FileDto productDto, MultipartFile file, String username);
    FileDto findById(Long id);
    FileEntity findEntityById(Long id);
    void deleteById(Long id, String username);
}
