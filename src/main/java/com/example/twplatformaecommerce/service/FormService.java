package com.example.twplatformaecommerce.service;

import com.example.twplatformaecommerce.model.entity.FormEntity;
import com.example.twplatformaecommerce.model.entity.UserEntity;
import com.example.twplatformaecommerce.repo.FormRepo;
import com.example.twplatformaecommerce.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FormService {
    private final FormRepo formRepo;
    private final PasswordEncoder passwordEncoder;

    public FormEntity save(FormEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return formRepo.save(user);
    }

    public FormEntity getForm(String username) {
        Optional<FormEntity> optForm=formRepo.findByUsername(username);
        if(optForm.isPresent())
            return optForm.get();
        throw new UsernameNotFoundException(username);
    }

    public FormEntity getFormByName(String name) {
        Optional<FormEntity> optForm=formRepo.findByName(name);
        if(optForm.isPresent())
            return optForm.get();
        throw new UsernameNotFoundException(name);
    }

    public List<FormEntity> getForms(){
        return formRepo.findAll();
    }

    public void deleteForm(String username){
        formRepo.deleteByUsername(username);
    }
}
