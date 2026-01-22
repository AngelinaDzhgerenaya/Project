package com.example.project.base.config;
import com.example.project.base.routes.BaseRoutes;
import com.example.project.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class BasicConfig {

    @Autowired
    private AuthService authService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/not-secured/**").permitAll()
                        .requestMatchers(BaseRoutes.API + "/**").authenticated() // Требуется аутентификация для API
                        .anyRequest().anonymous() // Для остальных страниц доступ разрешен всем
                )
                .logout(logout -> logout
                        .logoutUrl("/not-secured/logout") // URL для выхода
                        .invalidateHttpSession(true) // Инвалидируем сессию
                        .clearAuthentication(true) // Очищаем аутентификацию
                        .deleteCookies("JSESSIONID") // Удаляем cookie сессии
                        .logoutSuccessUrl("/") // Редирект на главную страницу после выхода
                )
                .userDetailsService(authService) // Используем ваш сервис для аутентификации
                .build();
    }



}




