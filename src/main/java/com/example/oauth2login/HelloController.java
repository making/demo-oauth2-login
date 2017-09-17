package com.example.oauth2login;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public Object hello(@AuthenticationPrincipal OAuth2User user) {
        System.out.println(user);
        System.out.println(user.getClass());
        return user.getAttributes();
    }
}
