package com.senac.Nutripulse.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.Nutripulse.DTO.Request.AlimentsRequestDTO;
import com.senac.Nutripulse.DTO.Request.DietasRequestDTO;
import com.senac.Nutripulse.DTO.Response.DietasResponseDTO;
import com.senac.Nutripulse.Entity.Aliments;
import com.senac.Nutripulse.Entity.Dietas;
import com.senac.Nutripulse.Mapper.DietasMapper;
import com.senac.Nutripulse.Repository.DietaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DietaService {

    @Autowired
    private DietaRepository dietasRepository;

    @Autowired
    private DietasMapper dietasMapper;

    @Autowired
    public DietaService(DietaRepository dietaRepository, DietasMapper dietaMapper) {
        this.dietasRepository = dietaRepository;
        this.dietasMapper = dietaMapper;
    }

    public List<DietasResponseDTO> listarDietasResponse() {
        List<Dietas> dietas = dietasRepository.findAll();

        return dietas.stream()
                .map(dietasMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<DietasRequestDTO> listarDietasRequest() {
        List<Dietas> dietas = dietasRepository.findAll();

        return dietas.stream()
                .map(dietasMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public DietasResponseDTO criarDieta(DietasRequestDTO dietasRequestDTO) {

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
        return dietasMapper.toResponseDto(dietas);
    }


    public Dietas obterPorID(Integer id) {

        Optional<Dietas> dietasOptional = dietasRepository.findById(id);

        if (dietasOptional.isEmpty()) {
            throw new IllegalArgumentException("A dieta não existe");
        }

        return dietasOptional.get();
    }

    public DietasRequestDTO obterDietaPorIdRequest(Integer id) {
        Dietas dieta = dietasRepository.findById(id).orElse(null);
        if (dieta == null) {
            throw new EntityNotFoundException("Nenhum usuário encontrado para o ID fornecido.");
        }
        return dietasMapper.toRequestDto(dieta);
    }

    public DietasResponseDTO obterDietaPorIdResponse(Integer id) {
        Dietas dieta = dietasRepository.findById(id).orElse(null);
        if (dieta == null) {
            throw new EntityNotFoundException("Nenhum usuário encontrado para o ID fornecido.");
        }
        return dietasMapper.toResponseDto(dieta);
    }

    public DietasResponseDTO atualizarDieta(DietasRequestDTO dietasRequestDTO) {

        // Encontra a dieta
        Dietas dietas = dietasRepository.findById(dietasRequestDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Dieta não encontrada"));

        // Atualiza os atributos da dieta
        dietas.setCaso(dietasRequestDTO.getCaso());
        dietas.setDescricao(dietasRequestDTO.getDescricao());

        // Salva a dieta
        dietasRepository.save(dietas);

        // Retorna a dieta atualizada
        return dietasMapper.toResponseDto(dietas);
    }


    public void excluirDieta(Integer id) {
        obterDietaPorIdResponse(id);
        dietasRepository.deleteById(id);
    }

}
