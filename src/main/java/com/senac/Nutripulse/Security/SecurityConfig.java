package com.senac.Nutripulse.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    //aplica métodos para validar os usuarios e definir se pode liberar para fazer a aplicação
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/dietas/criar").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/dietas/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/dietas/{id}").hasRole("ADMIN")

//                .requestMatchers(HttpMethod.POST, "/adm/criacao-dietas").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.GET, "/adm/criacao-dietas").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.GET, "/adm/dietas").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/alimentos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/alimentos/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/alimentos/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/exercicios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/exercicios/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/exercicios/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/treinos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/treinos/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/treinos/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/cadastro").permitAll()//.hasRole(("ADMIN"))
                        .anyRequest().permitAll()
                 )
                 .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    
    
}
