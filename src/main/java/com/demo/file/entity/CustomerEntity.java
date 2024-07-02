package com.demo.file.entity;
import com.demo.file.utils.DateUtils;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Customers",
        catalog = "")
@Data
public class CustomerEntity extends AbstractEntity {
    @Basic
    @Column(name = "full_name", nullable = true, length = 255, columnDefinition = "nvarchar(255)")
    private String fullName;

    @Basic
    @Column(name = "gender", nullable = true, length = 255, columnDefinition = "nvarchar(255)")
    private String gender;

    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    private String phone;

    @Basic
    @Column(name = "address", nullable = true, length = 255, columnDefinition = "nvarchar(255)")
    private String address;

    @DateTimeFormat(pattern = DateUtils.DATE_FORMAT)
    @Column(name = "dob", nullable = true)
    private LocalDate dob;

    @OneToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

}
