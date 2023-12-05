package com.senac.Nutripulse.API;

import com.senac.Nutripulse.DTO.Request.ExerciciosRequestDTO;
import com.senac.Nutripulse.DTO.Response.ExerciciosResponseDTO;
import com.senac.Nutripulse.Service.ExerciciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/exercicios")
public class ExerciciosController {

    @Autowired
    private ExerciciosService exerciciosService;

    @PostMapping()
    public ResponseEntity<ExerciciosResponseDTO> criarExercicio(@RequestBody ExerciciosRequestDTO exerciciosRequestDTO,
                                                                @RequestParam Integer id) {

        ExerciciosResponseDTO exercicios = exerciciosService.criarExericios(exerciciosRequestDTO, id);

        // Retorna o alimento criado
        return ResponseEntity.status(HttpStatus.CREATED).body(exercicios);
    }

    @PutMapping("{id}")
    public ResponseEntity<ExerciciosResponseDTO> atualizarExercicio(@PathVariable Integer id,
                                                                    @RequestBody ExerciciosRequestDTO exerciciosRequestDTO) {

        // Encontra o alimento
        ExerciciosResponseDTO exercicios = exerciciosService.atualizarExericios(id, exerciciosRequestDTO);

        // Retorna o alimento atualizado
        return ResponseEntity.status(HttpStatus.OK).body(exercicios);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluirExercicio(@PathVariable Integer id) {

        exerciciosService.excluirExericios(id);

        return new ResponseEntity<>("Exercicio excluída com sucesso.", HttpStatus.OK);
    }

    @GetMapping( )
    public ResponseEntity<List<ExerciciosResponseDTO>> listarExercicios() {

        List<ExerciciosResponseDTO> exerciciosResponseDTOs = exerciciosService.listarExericioss();

        // Retorna a lista de exercicios
        return ResponseEntity.status(HttpStatus.OK).body(exerciciosResponseDTOs);
    }
}
