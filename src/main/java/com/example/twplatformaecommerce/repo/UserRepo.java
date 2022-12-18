package com.example.twplatformaecommerce.repo;

import com.example.twplatformaecommerce.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
