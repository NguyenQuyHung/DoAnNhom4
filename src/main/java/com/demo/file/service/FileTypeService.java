package com.demo.file.service;


import com.demo.file.entity.FileEntity;
import com.demo.file.entity.FileTypeEntity;
import com.demo.file.payload.dto.FileDto;
import com.demo.file.payload.dto.FileTypeDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileTypeService {
    FileTypeEntity save(FileTypeEntity fileTypeEntity);
    List<FileTypeDto> getAllIfActive();

    List<FileTypeDto> getAll();

    void save(FileTypeDto fileTypeDto, String username);

    void update(FileTypeDto fileTypeDto, String username);

    FileTypeDto findById(Long id);
    FileTypeEntity findEntityById(Long id);
    void deleteById(Long id, String username);
}
