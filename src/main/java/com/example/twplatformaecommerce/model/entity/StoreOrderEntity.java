package com.example.twplatformaecommerce.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String wantedQuantity;
    @OneToOne
    @JoinColumn(name = "productentity_id")
    private ProductEntity product;
    @OneToOne
    @JoinColumn(name = "sellerentity_id")
    private SellerEntity seller;
}
