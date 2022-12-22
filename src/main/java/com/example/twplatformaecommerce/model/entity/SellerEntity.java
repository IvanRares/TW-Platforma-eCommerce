package com.example.twplatformaecommerce.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerEntity extends UserEntity {
    @Column(unique = true)
    private String name;
    private String address;
    private String code;
}
