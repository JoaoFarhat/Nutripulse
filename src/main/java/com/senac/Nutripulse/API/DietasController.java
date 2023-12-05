package com.senac.Nutripulse.API;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.Nutripulse.DTO.Request.DietasRequestDTO;
import com.senac.Nutripulse.DTO.Response.DietasResponseDTO;
import com.senac.Nutripulse.Service.DietaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/dietas")
public class DietasController {

    @Autowired
    private DietaService dietaService;

    @PostMapping("/criar")
    public ResponseEntity<DietasResponseDTO> criarDieta(@RequestBody DietasRequestDTO dietasRequestDTO) {

        DietasResponseDTO dietas = dietaService.criarDieta(dietasRequestDTO);

        // Retorna a dieta criada
        return ResponseEntity.status(HttpStatus.CREATED).body(dietas);
    }

    @GetMapping("/listar")
    public ResponseEntity<Object> listarDietas() {
        List<DietasResponseDTO> listaDietas = dietaService.listarDietasResponse();
            return ResponseEntity.ok(listaDietas);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> obterDietaPorId(@PathVariable Integer id) {
        DietasResponseDTO dietasResponseDTODto = dietaService.obterDietaPorIdResponse(id);
        return ResponseEntity.ok(dietasResponseDTODto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DietasResponseDTO> atualizarDieta(@PathVariable Integer id,
                                                            @RequestBody DietasRequestDTO dietasRequestDTO) {

        DietasResponseDTO dietas = dietaService.atualizarDieta(dietasRequestDTO);

        // Retorna a dieta atualizada
        return ResponseEntity.status(HttpStatus.OK).body(dietas);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirDieta(@PathVariable Integer id) {
        dietaService.excluirDieta(id);
        return new ResponseEntity<>("Dieta excluída com sucesso.", HttpStatus.OK);
    }

}





