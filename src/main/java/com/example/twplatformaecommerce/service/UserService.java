package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.RoleEntity;
import com.example.twplatformaecommerce.model.entity.UserEntity;
import com.example.twplatformaecommerce.repo.RoleRepo;
import com.example.twplatformaecommerce.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    public UserEntity saveUser(UserEntity user) {
        return userRepo.save(user);
    }

    public RoleEntity saveRole(RoleEntity role) {
        return roleRepo.save(role);
    }

    public void addRoleToUser(String username, String roleName) {

        UserEntity user=userRepo.findByUsername(username);
        RoleEntity role=roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    public UserEntity getUser(String username) {
        return userRepo.findByUsername(username);
    }

    public List<UserEntity> getUsers() {
        return userRepo.findAll();
    }
}
