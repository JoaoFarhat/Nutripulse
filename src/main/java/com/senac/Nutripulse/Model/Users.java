package com.senac.Nutripulse.Model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.senac.Nutripulse.Enums.Caso;
import com.senac.Nutripulse.Enums.Role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_users")
public class Users implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Integer id;

    @Column(name = "users_name", nullable = true, unique = true, length = 30)
    private String nome;

    @Column(name = "users_email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "users_cpf", nullable = true, unique = true, length = 11)
    private String cpf;

    @Column(name = "users_senha", nullable = false, unique = true, length = 255)
    private String senha;

    @Column(name = "users_altura", nullable = true, unique = false, length = 3)
    private double altura;

    @Column(name = "users_peso", nullable = true, unique = false, length = 3)
    private double peso;

    @Column(name = "users_IMC", nullable = true, unique = false, length = 3)
    private double IMC;

    @Column(name = "users_casoDieta", nullable = true, unique = false, length = 20)
    @Enumerated(EnumType.STRING)  
    private Caso casoDietas;

    @Column(name = "users_role", nullable = true, unique = false, length = 20)
    @Enumerated(EnumType.STRING) 
    private Role role;

    public Users(String email, String senha, Role role) {
      this.email = email;
      this.senha = senha;
      this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      if(this.role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
      else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
      return senha;
    }

    @Override
    public String getUsername() {
      return email;
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }
}
