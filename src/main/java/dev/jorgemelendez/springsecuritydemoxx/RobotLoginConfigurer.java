package dev.jorgemelendez.springsecuritydemoxx;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

public class RobotLoginConfigurer extends AbstractHttpConfigurer<RobotLoginConfigurer, HttpSecurity> {


    private final List<String> passwords = new ArrayList<>();


    @Override
    public void init(HttpSecurity http) throws Exception {
        http.authenticationProvider(new RobotAuthenticationProvider(passwords));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
       // 2. Initializes objects but can reuse objs from step 1 and from other configurers.
        var httpSecurity = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new RoboFilter(httpSecurity), UsernamePasswordAuthenticationFilter.class);
    }

    public RobotLoginConfigurer password(String password) {
        this.passwords.add(password);
        return this;
    }
}
