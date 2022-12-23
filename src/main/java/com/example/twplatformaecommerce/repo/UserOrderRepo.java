package com.example.twplatformaecommerce.repo;

import com.example.twplatformaecommerce.model.entity.UserOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderRepo extends JpaRepository<UserOrderEntity,Long> {
    public List<UserOrderEntity> findAllByUser_Username(String username);
}
