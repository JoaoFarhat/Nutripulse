package com.senac.Nutripulse.API;

import java.util.List;
import java.util.stream.Collectors;

import com.senac.Nutripulse.Entity.Aliments;
import com.senac.Nutripulse.Entity.Dietas;
import com.senac.Nutripulse.Mapper.AlimentsMapper;
import com.senac.Nutripulse.Repository.AlimentsRepository;
import com.senac.Nutripulse.Service.AlimentsService;
import com.senac.Nutripulse.Service.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.Nutripulse.DTO.Request.AlimentsRequestDTO;
import com.senac.Nutripulse.DTO.Response.AlimentsResponseDTO;

@RestController
@CrossOrigin("*")
@RequestMapping("/alimentos")
public class AlimentsController {

    @Autowired
    private DietaService dietaService;

    @Autowired
    private AlimentsService alimentsService;

    @Autowired
    private AlimentsRepository alimentosRepository;

    @Autowired
    private AlimentsMapper alimentosMapper;

    @PostMapping()
    public ResponseEntity<AlimentsResponseDTO> criarAlimento(@RequestBody AlimentsRequestDTO alimentosRequestDTO,
                                                             @RequestParam Integer id) {

        // Cria um novo alimento
        Aliments alimentos = alimentosMapper.toEntity(alimentosRequestDTO);

        // Obtém a dieta
        Dietas dieta = dietaService.obterPorID(id);
        alimentos.setDieta(dieta);

        // Adiciona o alimento à lista de alimentos da dieta
        dieta.getAlimentos().add(alimentos);

        // Salva o alimento
        alimentosRepository.save(alimentos);

        // Retorna o alimento criado
        return ResponseEntity.status(HttpStatus.CREATED).body(alimentosMapper.toResponseDto(alimentos));
    }

    @PutMapping("{id}")
    public ResponseEntity<AlimentsResponseDTO> atualizarAlimento(@PathVariable Integer id,
                                                                 @RequestBody AlimentsRequestDTO alimentosRequestDTO) {

        // Encontra o alimento
        Aliments alimentos = alimentosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alimento não encontrado"));

        // Atualiza o alimento
        alimentos.setNome(alimentosRequestDTO.getNome());
        alimentos.setCalorias(alimentosRequestDTO.getCalorias());
        alimentos.setProteinas(alimentosRequestDTO.getProteinas());
        alimentos.setCarboidratos(alimentosRequestDTO.getCarboidratos());
        alimentos.setGorduras(alimentosRequestDTO.getGorduras());


        // Salva o alimento
        alimentosRepository.save(alimentos);

        // Retorna o alimento atualizado
        return ResponseEntity.status(HttpStatus.OK).body(alimentosMapper.toResponseDto(alimentos));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluirAlimento(@PathVariable Integer id) {

        // Encontra o alimento
        Aliments alimentos = alimentosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alimento não encontrado"));

        // Exclui o alimento
        alimentosRepository.delete(alimentos);

        return new ResponseEntity<>("Alimento excluída com sucesso.", HttpStatus.OK);
    }

    @GetMapping( )
    public ResponseEntity<List<AlimentsResponseDTO>> listarAlimentos() {

        // Lista os alimentos
        List<Aliments> alimentos = alimentosRepository.findAll();

        // Converte os alimentos para DTOs
        List<AlimentsResponseDTO> alimentosResponseDTOs = alimentos.stream()
                .map(alimentosMapper::toResponseDto)
                .collect(Collectors.toList());

        // Retorna a lista de alimentos
        return ResponseEntity.status(HttpStatus.OK).body(alimentosResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentsResponseDTO> obterPorID(@PathVariable Integer id) {

        AlimentsResponseDTO alimentsResponseDTO = alimentsService.obterAlimentsPorIdResponse(id);

        return ResponseEntity.ok(alimentsResponseDTO);
    }
}
