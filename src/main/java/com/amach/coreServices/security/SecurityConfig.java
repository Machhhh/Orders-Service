package com.amach.coreServices.security;

import com.amach.coreServices.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private ClientRepository clientRepository;
    private String[] staticResources = {
            "/css/**",
            "/img/**",
            "/js/**",
            "/scripts/**",
    };

    @Autowired
    public SecurityConfig(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.cors().disable();
        http.authorizeRequests()
                .antMatchers("/", "/index", "/about")
                .permitAll()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/clients/register", "/apply")
                .permitAll()
                .antMatchers("index.html", "error", "client.html",
                        "register.html", "login.html", "about.html")
                .permitAll()
                .antMatchers("/requests", "/h2-console/**",
                        "/swagger-ui.html#/**", "/api/**")
                .hasRole("ADMIN")
                .anyRequest().authenticated();

        http.exceptionHandling().accessDeniedPage("/clients/access/error");

        http.formLogin()
                .loginPage("/clients/login").permitAll()
                .failureHandler((req, resp, e) -> resp.sendRedirect("/clients/login/error")).permitAll()
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl("/welcome")
                .permitAll();
        http.logout()
                .logoutUrl("/clients/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/clients/login")
                .permitAll();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(clientRepository);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
