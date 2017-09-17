package com.example.oauth2login;

import org.springframework.boot.autoconfigure.security.StaticResourceRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationProperties;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "security.oauth2")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private Map<String, ClientRegistrationProperties> clients;
    private Map<String, String> userNameAttributeNames;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        OAuth2LoginConfigurer<HttpSecurity> configurer = http.authorizeRequests() //
                .requestMatchers(StaticResourceRequest.toCommonLocations()).permitAll() //
                .anyRequest().authenticated()  //
                .and() //
                .oauth2Login();

        this.userNameAttributeNames.forEach((client, name) -> {
            configurer.userInfoEndpoint() //
                    .userNameAttributeName(name, URI.create(this.clients.get(client).getUserInfoUri()));
        });
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.values().stream() //
                .map(c -> new ClientRegistration.Builder(c).build()) //
                .collect(Collectors.toList());
        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("demo") //
                .password("demo") //
                .roles("USER") //
                .build());
        return manager;
    }

    public Map<String, ClientRegistrationProperties> getClients() {
        return clients;
    }

    public void setClients(Map<String, ClientRegistrationProperties> clients) {
        this.clients = clients;
    }

    public Map<String, String> getUserNameAttributeNames() {
        return userNameAttributeNames;
    }

    public void setUserNameAttributeNames(Map<String, String> userNameAttributeNames) {
        this.userNameAttributeNames = userNameAttributeNames;
    }
}
