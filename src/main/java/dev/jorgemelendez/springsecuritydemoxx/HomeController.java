package dev.jorgemelendez.springsecuritydemoxx;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello world";
    }

    @GetMapping("/private")
    public String privateController(Authentication authentication) {
        return "Hello, %s".formatted(authentication.getName());
    }
}
