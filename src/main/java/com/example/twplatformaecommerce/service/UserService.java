package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.RoleEntity;
import com.example.twplatformaecommerce.model.entity.UserEntity;
import com.example.twplatformaecommerce.repo.RoleRepo;
import com.example.twplatformaecommerce.repo.UserRepo;
import com.example.twplatformaecommerce.security.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    public UserEntity saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public RoleEntity saveRole(RoleEntity role) {
        return roleRepo.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        Optional<UserEntity> optUser = userRepo.findByUsername(username);
        if (optUser.isPresent()) {
            UserEntity user = optUser.get();
            RoleEntity role = roleRepo.findByName(roleName);
            user.getRoles().add(role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<UserEntity> optUser=userRepo.findByUsername(username);
        if(optUser.isPresent()){
            UserEntity user = optUser.get();
            Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
            user.getRoles().forEach(roleEntity -> {
                authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));
            });
            return new User(user.getUsername(),user.getPassword(),authorities);
        }
        throw new UsernameNotFoundException(username);
    }

    public UserEntity getUser(String username) {
        Optional<UserEntity> optUser=userRepo.findByUsername(username);
        if(optUser.isPresent())
            return optUser.get();
        throw new UsernameNotFoundException(username);
    }

    public List<UserEntity> getUsers() {
        return userRepo.findAll();
    }
}
