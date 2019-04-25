package com.example.webservice;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.ldaplogin")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private String dnPatterns;
    private String searchBase;
    private String url;
    private String passwordAttribute;
    private String managerDN;
    private String managerPassword;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
            .authorizeRequests()
                .anyRequest().fullyAuthenticated()
            .and()
                .httpBasic()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                    .userDnPatterns(dnPatterns)
                    .groupSearchBase(searchBase)
                .contextSource()
                    .url(url)
                .managerDn(managerDN)
                .managerPassword(managerPassword)
                    .and()
                .passwordCompare()
                    .passwordEncoder(new LdapShaPasswordEncoder())
                    .passwordAttribute(passwordAttribute);
    }

}
