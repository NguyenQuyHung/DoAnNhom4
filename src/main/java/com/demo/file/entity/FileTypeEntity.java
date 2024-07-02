package com.demo.file.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@Table(name = "FileType")
public class FileTypeEntity extends AbstractEntity{
    @Basic
    @Column(length = 255,name = "name", nullable = true,  columnDefinition = "nvarchar(255)")
    private String name;

    @OneToMany(mappedBy = "fileTypeEntity", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<FileEntity> files;
}
