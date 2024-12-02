package com.lifeIsbeautiful.config;

import com.lifeIsbeautiful.exception.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

//        http.requiresChannel(rrc -> rrc.anyRequest().requiresSecure()) //only HTTPS
        http.requiresChannel(rrc -> rrc.anyRequest().requiresInsecure())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans").authenticated()
                        .requestMatchers("/contact", "/notices", "/register").permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(hcc -> hcc.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));
        return http.build();
    }

//    @Bean
//    public UserDetailsService getUserDetails(DataSource dataSource)
//    {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker passwordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
