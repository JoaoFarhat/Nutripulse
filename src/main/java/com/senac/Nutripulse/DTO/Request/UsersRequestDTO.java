package com.senac.Nutripulse.DTO.Request;

import com.senac.Nutripulse.Enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsersRequestDTO {

    private Integer id;
    
    private String nome;

    @NotBlank(message = "O campo 'email' não pode estar vazio")
    @Email(message = "O campo 'email' deve ser um endereço de e-mail válido")
    @Size(max = 255, message = "O campo 'email' deve ter no máximo 255 caracteres")
    private String email;

    private String cpf;

    @NotBlank(message = "O campo 'senha' não pode estar vazio")
    @Size(max = 30, message = "O campo 'senha' deve ter no máximo 30 caracteres")
    String senha;

    private double altura;

    private double peso;

    private Role role;

}
