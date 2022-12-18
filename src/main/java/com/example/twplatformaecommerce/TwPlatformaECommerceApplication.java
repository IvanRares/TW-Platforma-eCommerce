package com.example.twplatformaecommerce;

import com.example.twplatformaecommerce.model.entity.RoleEntity;
import com.example.twplatformaecommerce.model.entity.UserEntity;
import com.example.twplatformaecommerce.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class TwPlatformaECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwPlatformaECommerceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args->{
            userService.saveRole(new RoleEntity(null,"ROLE_USER"));
            userService.saveRole(new RoleEntity(null,"ROLE_SHOP"));
            userService.saveRole(new RoleEntity(null,"ROLE_WAREHOUSE"));
            userService.saveRole(new RoleEntity(null,"ROLE_ADMIN"));

            userService.saveUser(new UserEntity(null,"john@mail.com","1234",new ArrayList<>()));
            userService.saveUser(new UserEntity(null,"will@mail.com","1234",new ArrayList<>()));
            userService.saveUser(new UserEntity(null,"rares@mail.com","1234",new ArrayList<>()));

            userService.addRoleToUser("john@mail.com","ROLE_USER");
            userService.addRoleToUser("will@mail.com","ROLE_WAREHOUSE");
            userService.addRoleToUser("rares@mail.com","ROLE_ADMIN");
        };
    }
}
