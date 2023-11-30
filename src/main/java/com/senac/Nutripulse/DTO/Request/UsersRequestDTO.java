package com.senac.Nutripulse.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsersRequestDTO {

    private Integer id;
    
    @NotBlank(message = "O campo 'nome' não pode estar vazio")
    @Size(max = 30, message = "O campo 'nome' deve ter no máximo 30 caracteres")
    private String nome;

    @NotBlank(message = "O campo 'email' não pode estar vazio")
    @Email(message = "O campo 'email' deve ser um endereço de e-mail válido")
    @Size(max = 255, message = "O campo 'email' deve ter no máximo 255 caracteres")
    private String email;

    @NotBlank(message = "O campo 'cpf' não pode estar vazio")
    @Size(max = 11, message = "O campo 'cpf' deve ter no máximo 11 caracteres")
    private String cpf;

    @NotBlank(message = "O campo 'senha' não pode estar vazio")
    @Size(max = 30, message = "O campo 'senha' deve ter no máximo 30 caracteres")
    String senha;

    @NotBlank(message = "O campo 'altura' não pode estar vazio")
    @Size(max = 3, message = "O campo 'altura' deve ter no máximo 3 caracteres")
    private double altura;

    @NotBlank(message = "O campo 'peso' não pode estar vazio")
    @Size(max = 3, message = "O campo 'peso' deve ter no máximo 3 caracteres")
    private double peso;

}
