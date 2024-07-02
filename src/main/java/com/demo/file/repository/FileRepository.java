package com.demo.file.repository;

import com.demo.file.entity.FileEntity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SpringBootApplication
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findAllByStatus(Integer status);

    @Query(value = "SELECT p FROM FileEntity p where p.status = 1 ORDER BY p.id ASC LIMIT 4")
    List<FileEntity> getTop4();

    Long countByStatus(Integer status);
}
