package dev.jorgemelendez.springsecuritydemoxx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        ProviderManager providerManager = new ProviderManager(new RobotAuthenticationProvider(List.of("beep-boop")));

        RobotLoginConfigurer roboConfigurer = new RobotLoginConfigurer().password("beep-boop");
        return httpSecurity
                .authorizeHttpRequests(config -> {
                    config.requestMatchers("/").permitAll();
                    config.anyRequest().authenticated();
                })
                .formLogin(withDefaults())
                .oauth2Login(withDefaults())
                .apply(new RobotLoginConfigurer()).and()
                .authenticationProvider(new JorgeAuthenticationProvider())
                .build();
    }
}
