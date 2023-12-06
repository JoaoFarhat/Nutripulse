package com.senac.Nutripulse.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExerciciosRequestDTO {
    private Integer id;

    @NotBlank(message = "O campo 'nome' não pode estar vazio")
    @Size(max = 30, message = "O campo 'nome' não pde ter mais que 30 caracteres")
    private String nome;

    @NotBlank(message = "O campo 'descricao' não pode estar vazio")
    @Size(max = 3000, message = "O campo 'descricao' não pde ter mais que 3000 caracteres")
    private String descricao;

    @NotBlank(message = "O campo 'repeticoes' não pode estar vazio")
    @Size(max = 3, message = "O campo 'repeticoes' não pde ter mais que 3 caracteres")
    private int repeticoes;

    @NotBlank(message = "O campo 'series' não pode estar vazio")
    @Size(max = 3, message = "O campo 'series' não pde ter mais que 3 caracteres")
    private int series;

    @NotBlank(message = "O campo 'idTreino' não pode estar vazio")
    private Integer idTreino;

}
