package com.example.demooauth2login;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public Object hello(@AuthenticationPrincipal OAuth2User user) {
		System.out.println(user);
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("class", user.getClass());
		res.put("name", user.getName());
		res.put("authorities", user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		res.put("attributes", user.getAttributes());
		return res;
	}
}