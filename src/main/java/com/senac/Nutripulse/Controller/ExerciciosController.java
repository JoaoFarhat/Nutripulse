package com.senac.Nutripulse.Controller;

import com.senac.Nutripulse.DTO.Request.ExerciciosRequestDTO;
import com.senac.Nutripulse.DTO.Response.ExerciciosResponseDTO;
import com.senac.Nutripulse.Entity.Exercicios;
import com.senac.Nutripulse.Entity.Treinos;
import com.senac.Nutripulse.Mapper.ExerciciosMapper;
import com.senac.Nutripulse.Repository.ExerciciosRepository;
import com.senac.Nutripulse.Service.TreinosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/exercicios")
public class ExerciciosController {
    @Autowired
    private TreinosService treinosService;

    @Autowired
    private ExerciciosRepository exerciciosRepository;

    @Autowired
    private ExerciciosMapper exerciciosMapper;

    @PostMapping()
    public ResponseEntity<ExerciciosResponseDTO> criarExercicio(@RequestBody ExerciciosRequestDTO exerciciosRequestDTO,
                                                             @RequestParam Integer id) {

        // Cria um novo alimento
        Exercicios exercicios = exerciciosMapper.toEntity(exerciciosRequestDTO);

        // Obtém a treinos
        Treinos treinos = treinosService.obterPorID(id);
        exercicios.setTreino(treinos);

        // Adiciona o alimento à lista de exercicios da treinos
        treinos.getExercicios().add(exercicios);

        // Salva o alimento
        exerciciosRepository.save(exercicios);

        // Retorna o alimento criado
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciciosMapper.toResponseDto(exercicios));
    }

    @PutMapping("{id}")
    public ResponseEntity<ExerciciosResponseDTO> atualizarExercicio(@PathVariable Integer id,
                                                                 @RequestBody ExerciciosRequestDTO exerciciosRequestDTO) {

        // Encontra o alimento
        Exercicios exercicios = exerciciosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercicio não encontrado"));

        // Atualiza o alimento
        exercicios.setNome(exerciciosRequestDTO.getNome());
        exercicios.setDescricao(exerciciosRequestDTO.getDescricao());
        exercicios.setSeries(exerciciosRequestDTO.getSeries());
        exercicios.setRepeticoes(exerciciosRequestDTO.getRepeticoes());


        // Salva o alimento
        exerciciosRepository.save(exercicios);

        // Retorna o alimento atualizado
        return ResponseEntity.status(HttpStatus.OK).body(exerciciosMapper.toResponseDto(exercicios));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluirExercicio(@PathVariable Integer id) {

        // Encontra o alimento
        Exercicios exercicios = exerciciosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercicio não encontrado"));

        // Exclui o alimento
        exerciciosRepository.delete(exercicios);

        return new ResponseEntity<>("Exercicio excluída com sucesso.", HttpStatus.OK);
    }

    @GetMapping( )
    public ResponseEntity<List<ExerciciosResponseDTO>> listarExercicios() {

        // Lista os exercicios
        List<Exercicios> exercicios = exerciciosRepository.findAll();

        // Converte os exercicios para DTOs
        List<ExerciciosResponseDTO> exerciciosResponseDTOs = exercicios.stream()
                .map(exerciciosMapper::toResponseDto)
                .collect(Collectors.toList());

        // Retorna a lista de exercicios
        return ResponseEntity.status(HttpStatus.OK).body(exerciciosResponseDTOs);
    }
}
