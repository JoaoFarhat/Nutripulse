package com.senac.Nutripulse.DTO.Response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AlimentsResponseDTO {
    private Integer id;

    @NotBlank(message = "O campo 'nome' não pode estar vazio")
    @Size(max = 30, message = "O campo 'nome' não pde ter mais que 30 caracteres")
    private String nome;

    @NotBlank(message = "O campo 'calorias' não pode estar vazio")
    @Size(max = 30, message = "O campo 'calorias' não pde ter mais que 10 caracteres")
    private double calorias;
    
    @NotBlank(message = "O campo 'proteinas' não pode estar vazio")
    @Size(max = 10, message = "O campo 'proteinas' não pde ter mais que 10 caracteres")
    private double proteinas;

    @NotBlank(message = "O campo 'carboidratos' não pode estar vazio")
    @Size(max = 10, message = "O campo 'carboidratos' não pde ter mais que 10 caracteres")
    private double carboidratos;

    @NotBlank(message = "O campo 'gorduras' não pode estar vazio")
    @Size(max = 10, message = "O campo 'gorduras' não pde ter mais que 10 caracteres")
    private double gorduras;






}
