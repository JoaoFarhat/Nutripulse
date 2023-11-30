package com.senac.Nutripulse.Controller;

import com.senac.Nutripulse.DTO.Request.ExerciciosRequestDTO;
import com.senac.Nutripulse.DTO.Request.TreinosRequestDTO;
import com.senac.Nutripulse.DTO.Response.TreinosResponseDTO;
import com.senac.Nutripulse.Entity.Exercicios;
import com.senac.Nutripulse.Entity.Treinos;
import com.senac.Nutripulse.Mapper.TreinosMapper;
import com.senac.Nutripulse.Repository.TreinosRepository;
import com.senac.Nutripulse.Service.TreinosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/treinos")
public class TreinosController {
    @Autowired
    private TreinosRepository treinosRepository;

    @Autowired
    private TreinosService treinosService;

    @Autowired
    private TreinosMapper treinosMapper;

    @PostMapping
    public ResponseEntity<TreinosResponseDTO> criarTreinos(@RequestBody TreinosRequestDTO treinosRequestDTO) {

        // Valida se a treinos tem pelo menos 3 exercicios
        if (treinosRequestDTO.getExercicios().size() < 3) {
            throw new IllegalArgumentException("A treinos deve ter pelo menos 3 exercicios");
        }

        // Cria uma nova treinos
        Treinos treinos = new Treinos();
        treinos.setModo(treinosRequestDTO.getModo());
        treinos.setDescricao(treinosRequestDTO.getDescricao());

        // Cria os exercicios da treinos
        for (ExerciciosRequestDTO exerciciosRequestDTO : treinosRequestDTO.getExercicios()) {
            Exercicios exercicios = new Exercicios();
            exercicios.setNome(exerciciosRequestDTO.getNome());
            exercicios.setDescricao(exerciciosRequestDTO.getDescricao());
            exercicios.setSeries(exerciciosRequestDTO.getSeries());
            exercicios.setRepeticoes(exerciciosRequestDTO.getRepeticoes());
            exercicios.setTreino(treinos);
            treinos.getExercicios().add(exercicios);
        }

        // Salva a treinos
        treinosRepository.save(treinos);

        // Retorna a treinos criada
        return ResponseEntity.status(HttpStatus.CREATED).body(treinosMapper.toResponseDto(treinos));
    }

    @GetMapping
    public ResponseEntity<Object> listarTreinos() {
        List<TreinosResponseDTO> listaTreinos = treinosService.listarTreinosResponse();
        return ResponseEntity.ok(listaTreinos);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> obterTreinosPorId(@PathVariable Integer id) {
        TreinosResponseDTO treinosResponseDTODto = treinosService.obterTreinoPorIdResponse(id);
        return ResponseEntity.ok(treinosResponseDTODto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TreinosResponseDTO> atualizarTreinos(@PathVariable Integer id,
                                                             @RequestBody TreinosRequestDTO treinosRequestDTO) {

        // Encontra a treinos
        Treinos treinos = treinosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treinos não encontrada"));

        // Atualiza os atributos da treinos
        treinos.setModo(treinosRequestDTO.getModo());
        treinos.setDescricao(treinosRequestDTO.getDescricao());

        // Salva a treinos
        treinosRepository.save(treinos);

        // Retorna a treinos atualizada
        return ResponseEntity.status(HttpStatus.OK).body(treinosMapper.toResponseDto(treinos));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirTreinos(@PathVariable Integer id) {
        treinosService.excluirTreino(id);
        return new ResponseEntity<>("Treinos excluída com sucesso.", HttpStatus.OK);
    }
}
