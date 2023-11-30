package com.senac.Nutripulse.Controller;

import java.util.List;

import com.senac.Nutripulse.Service.AlimentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.Nutripulse.DTO.Request.AlimentsRequestDTO;
import com.senac.Nutripulse.DTO.Request.DietasRequestDTO;
import com.senac.Nutripulse.DTO.Response.DietasResponseDTO;
import com.senac.Nutripulse.Entity.Aliments;
import com.senac.Nutripulse.Entity.Dietas;
import com.senac.Nutripulse.Mapper.DietasMapper;
import com.senac.Nutripulse.Repository.DietaRepository;
import com.senac.Nutripulse.Service.DietaService;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/dietas")
public class DietasController {

    private final DietaRepository dietasRepository;

    private final DietaService dietaService;

    private final DietasMapper dietaMapper;


    public DietasController(DietaRepository dietasRepository, DietaService dietasService, DietasMapper dietasMapper, AlimentsService alimentsService) {
        this.dietasRepository = dietasRepository;
        this.dietaMapper = dietasMapper;
        this.dietaService = dietasService;
    }

    @PostMapping("/criar")
    public ResponseEntity<DietasResponseDTO> criarDieta(@RequestBody DietasRequestDTO dietasRequestDTO) {

        // Valida se a dieta tem pelo menos 3 alimentos
        if (dietasRequestDTO.getAlimentos().size() < 3) {
            throw new IllegalArgumentException("A dieta deve ter pelo menos 3 alimentos");
        }

        // Cria uma nova dieta
        Dietas dietas = new Dietas();
        dietas.setCaso(dietasRequestDTO.getCaso());
        dietas.setDescricao(dietasRequestDTO.getDescricao());

        // Cria os alimentos da dieta
        for (AlimentsRequestDTO alimentosRequestDTO : dietasRequestDTO.getAlimentos()) {
            Aliments alimentos = new Aliments();
            alimentos.setNome(alimentosRequestDTO.getNome());
            alimentos.setCalorias(alimentosRequestDTO.getCalorias());
            alimentos.setProteinas(alimentosRequestDTO.getProteinas());
            alimentos.setCarboidratos(alimentosRequestDTO.getCarboidratos());
            alimentos.setGorduras(alimentosRequestDTO.getGorduras());
            alimentos.setDieta(dietas);
            dietas.getAlimentos().add(alimentos);
        }

        // Salva a dieta
        dietasRepository.save(dietas);

        // Retorna a dieta criada
        return ResponseEntity.status(HttpStatus.CREATED).body(dietaMapper.toResponseDto(dietas));
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

        // Encontra a dieta
        Dietas dietas = dietasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dieta não encontrada"));

        // Atualiza os atributos da dieta
        dietas.setCaso(dietasRequestDTO.getCaso());
        dietas.setDescricao(dietasRequestDTO.getDescricao());

        // Salva a dieta
        dietasRepository.save(dietas);

        // Retorna a dieta atualizada
        return ResponseEntity.status(HttpStatus.OK).body(dietaMapper.toResponseDto(dietas));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirDieta(@PathVariable Integer id) {
        dietaService.excluirDieta(id);
        return new ResponseEntity<>("Dieta excluída com sucesso.", HttpStatus.OK);
    }

}





