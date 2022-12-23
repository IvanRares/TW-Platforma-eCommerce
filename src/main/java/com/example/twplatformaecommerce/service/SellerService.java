package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.model.entity.RoleEntity;
import com.example.twplatformaecommerce.model.entity.SellerEntity;
import com.example.twplatformaecommerce.model.entity.UserEntity;
import com.example.twplatformaecommerce.repo.FormRepo;
import com.example.twplatformaecommerce.repo.RoleRepo;
import com.example.twplatformaecommerce.repo.SellerRepo;
import lombok.RequiredArgsConstructor;
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
public class SellerService {
    private final SellerRepo sellerRepo;
    private final RoleRepo roleRepo;
    private final FormRepo formRepo;
    private final PasswordEncoder passwordEncoder;

    public void add(String username) {
        FormEntity form=formRepo.findByUsername(username).get();
        SellerEntity seller=new SellerEntity();
        seller.setUsername(form.getUsername());
        seller.setCode(form.getCode());
        seller.setName(form.getName());
        seller.setAddress(form.getAddress());
        seller.setPassword(form.getPassword());
        addRoleToSeller(seller,form.getType());
        formRepo.deleteByUsername(form.getUsername());
        sellerRepo.save(seller);
    }

    private void addRoleToSeller(SellerEntity seller,String roleName){
        RoleEntity role = roleRepo.findByName(roleName);
        seller.getRoles().add(role);
    }

    public SellerEntity getSeller(String username) {
        Optional<SellerEntity> optSeller=sellerRepo.findSellerEntityByUsername(username);
        if(optSeller.isPresent())
            return optSeller.get();
        throw new UsernameNotFoundException(username);
    }

    public SellerEntity getSellerByName(String name) {
        Optional<SellerEntity> optSeller=sellerRepo.findByName(name);
        if(optSeller.isPresent())
            return optSeller.get();
        throw new UsernameNotFoundException(name);
    }

    public List<SellerEntity> getAllStores(){
        RoleEntity store=roleRepo.findByName("ROLE_SHOP");
        return sellerRepo.findAllByRoles_(store);
    }
}
