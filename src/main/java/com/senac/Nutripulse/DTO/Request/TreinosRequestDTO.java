package com.senac.Nutripulse.DTO.Request;

import com.senac.Nutripulse.Enums.Modo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TreinosRequestDTO {
    private Integer id;

    @NotNull(message = "O campo 'descricao' não pode estar vazio")
    @Size(max = 3000, message = "O campo 'descricao' deve ter no máximo 3000 caracteres")
    private String descricao;

    @NotNull(message = "O campo 'modo' não pode estar vazio")
    private Modo modo;

    @NotNull(message = "O campo 'exercicios' não pode estar vazio")
    private List<ExerciciosRequestDTO> exercicios;

}
