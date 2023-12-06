package com.senac.Nutripulse.API;

import java.util.List;
import com.senac.Nutripulse.Service.AlimentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.Nutripulse.DTO.Request.AlimentsRequestDTO;
import com.senac.Nutripulse.DTO.Response.AlimentsResponseDTO;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/alimentos")
public class AlimentsController {

    @Autowired
    private AlimentsService alimentsService;

    @PostMapping()
    public ResponseEntity<AlimentsResponseDTO> criarAlimento(@RequestBody AlimentsRequestDTO alimentosRequestDTO) {

        AlimentsResponseDTO alimentos = alimentsService.criarAlimento(alimentosRequestDTO);

        // Retorna o alimento criado
        return ResponseEntity.status(HttpStatus.CREATED).body(alimentos);
    }

    @PutMapping("{id}")
    public ResponseEntity<AlimentsResponseDTO> atualizarAlimento(@PathVariable Integer id,
                                                                 @RequestBody AlimentsRequestDTO alimentosRequestDTO) {

        AlimentsResponseDTO alimentsResponseDTO = alimentsService.atualizarAlimento(id, alimentosRequestDTO);

        // Retorna o alimento atualizado
        return ResponseEntity.status(HttpStatus.OK).body(alimentsResponseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluirAlimento(@PathVariable Integer id) {

        alimentsService.excluirAlimento(id);

        return new ResponseEntity<>("Alimento excluída com sucesso.", HttpStatus.OK);
    }

    @GetMapping( )
    public ResponseEntity<List<AlimentsResponseDTO>> listarAlimentos() {

        List<AlimentsResponseDTO> alimentos = alimentsService.listarAlimentos();

        // Retorna a lista de alimentos
        return ResponseEntity.status(HttpStatus.OK).body(alimentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentsResponseDTO> obterPorID(@PathVariable Integer id) {

        AlimentsResponseDTO alimentsResponseDTO = alimentsService.obterAlimentsPorIdResponse(id);

        return ResponseEntity.ok(alimentsResponseDTO);
    }
}
