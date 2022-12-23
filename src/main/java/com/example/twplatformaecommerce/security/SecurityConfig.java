package com.example.twplatformaecommerce.security;

import com.example.twplatformaecommerce.filter.CustomAuthenticationFilter;
import com.example.twplatformaecommerce.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] METHODS_ALLOWED = {
            GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name(),
    };

    @Bean
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/register/**", "/seller_register/**").permitAll()
                .antMatchers(GET, "/forms/**").hasRole("ADMIN")
                .antMatchers(GET,"/products/**").hasAnyRole("WAREHOUSE","SHOP","USER")
                .antMatchers("/newproduct/**","/orders/**").hasRole("WAREHOUSE")
                .antMatchers("/storeorder/**","/buy/**").hasRole("SHOP")
                .antMatchers("/userorder/**","/search/**").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/login_success_handler")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .deleteCookies(AUTHORIZATION)
                .invalidateHttpSession(true)
                .and()
                .addFilter(new CustomAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods(METHODS_ALLOWED)
//                .allowedOrigins("*");
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
