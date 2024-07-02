package com.demo.file.repository;

import com.demo.file.entity.FileEntity;
import com.demo.file.entity.FileTypeEntity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SpringBootApplication
public interface FileTypeRepository extends JpaRepository<FileTypeEntity, Long> {
}
