package inhatc.aic.sbb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.formLogin(Customizer.withDefaults());
        http.formLogin(form -> form
                .loginPage("/member/login") // 로그인 페이지 URL
                .defaultSuccessUrl("/")
                .failureUrl("/member/login/error")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll());

        http.logout(Customizer.withDefaults());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/member/**").permitAll()
                .anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}