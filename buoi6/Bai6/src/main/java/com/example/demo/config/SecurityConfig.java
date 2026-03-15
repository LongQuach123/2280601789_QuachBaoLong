package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AccountService accountService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                // Phân quyền theo CSDL
                .requestMatchers("/products").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/products/add", "/products/edit/**", "/products/delete/**").hasRole("ADMIN")
                .requestMatchers("/order").hasRole("USER")
                
                // Cho phép tất cả truy cập trang login và trang lỗi 403 (THÊM /403 VÀO ĐÂY)
                .requestMatchers("/login", "/403", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
        )
        // Cấu hình dùng trang login custom
        .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/products", true) // Thành công thì vào trang sản phẩm
                .permitAll()
        )
        // Cấu hình đăng xuất
        .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        )
        // THÊM ĐÚNG ĐOẠN NÀY ĐỂ BẮT LỖI 403
        .exceptionHandling(exception -> exception
                .accessDeniedPage("/403")
        );

        return http.build();
    }
}