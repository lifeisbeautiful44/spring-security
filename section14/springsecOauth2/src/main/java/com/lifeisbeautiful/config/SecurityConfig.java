package com.lifeisbeautiful.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((request) -> request.requestMatchers("/secure").authenticated()
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration github = getGithubClient();
//        ClientRegistration facebook = getFacebookClient();
        return new InMemoryClientRegistrationRepository(github);
    }

    private ClientRegistration getGithubClient() {
        return CommonOAuth2Provider.GITHUB
                .getBuilder("github")
                .clientId("Ov23litD4xiXhKQgzk7K")
                .clientSecret("64eb75ceab3ef50e645375622295a84c0662d3b9")
                .build();
    }

/*    private ClientRegistration getFacebookClient() {
        return CommonOAuth2Provider.FACEBOOK
                .getBuilder("facebook")
                .clientId("")
                .clientSecret("")
                .build();
    }*/

}
