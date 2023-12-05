package com.senac.Nutripulse.API;

import com.senac.Nutripulse.DTO.Request.TreinosRequestDTO;
import com.senac.Nutripulse.DTO.Response.TreinosResponseDTO;
import com.senac.Nutripulse.Service.TreinosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/treinos")
public class TreinosController {

    @Autowired
    private TreinosService treinosService;

    @PostMapping
    public ResponseEntity<TreinosResponseDTO> criarTreinos(@RequestBody TreinosRequestDTO treinosRequestDTO) {

        // Valida se a treinos tem pelo menos 3 exercicios
        TreinosResponseDTO treinos = treinosService.criarTreino(treinosRequestDTO);

        // Retorna a treinos criada
        return ResponseEntity.status(HttpStatus.CREATED).body(treinos);
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

        TreinosResponseDTO treinos = treinosService.atualizarTreino(treinosRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(treinos);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirTreinos(@PathVariable Integer id) {
        treinosService.excluirTreino(id);
        return new ResponseEntity<>("Treinos excluída com sucesso.", HttpStatus.OK);
    }
}
