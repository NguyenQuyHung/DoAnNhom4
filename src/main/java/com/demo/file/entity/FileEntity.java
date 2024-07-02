package com.demo.file.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "Files")
public class FileEntity extends AbstractEntity{
    @Basic
    @Column(length = 255,name = "link", nullable = true,  columnDefinition = "nvarchar(255)")
    private String link;

    @Basic
    @Column(length = 255,name = "originalFilename", nullable = true,  columnDefinition = "nvarchar(255)")
    private String originalFilename;

    @Basic
    @Column(name = "description", nullable = true, length = 255, columnDefinition = "nvarchar(255)")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "file_type_id")
    @EqualsAndHashCode.Exclude
    private FileTypeEntity fileTypeEntity;
}
