package com.example.twplatformaecommerce.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"name", "warehouseName","storeName"})
})
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String quantity;
    private String warehouseName;
    private String storeName;
}
