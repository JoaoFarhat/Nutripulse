package com.senac.Nutripulse.DTO.Response;

import java.util.List;

import com.senac.Nutripulse.DTO.Request.AlimentsRequestDTO;
import com.senac.Nutripulse.Enums.Caso;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DietasResponseDTO {
    
    private Integer id;

    @NotNull(message = "O campo 'nome' não pode estar vazio")
    private String nome;

    @NotNull(message = "O campo 'descricao' não pode estar vazio")
    @Size(max = 3000, message = "O campo 'descricao' deve ter no máximo 3000 caracteres")
    private String descricao;

    @NotNull(message = "O campo 'caso' não pode estar vazio")
    private Caso caso;

    private List<AlimentsRequestDTO> alimentos;
}
