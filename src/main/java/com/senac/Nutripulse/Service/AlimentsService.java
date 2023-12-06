package com.senac.Nutripulse.Service;

import com.senac.Nutripulse.DTO.Request.AlimentsRequestDTO;
import com.senac.Nutripulse.DTO.Response.AlimentsResponseDTO;
import com.senac.Nutripulse.Mapper.AlimentsMapper;
import com.senac.Nutripulse.Model.Aliments;
import com.senac.Nutripulse.Model.Dietas;
import com.senac.Nutripulse.Repository.AlimentsRepository;
import com.senac.Nutripulse.Repository.DietaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlimentsService {

    @Autowired
    private AlimentsRepository alimentosRepository;

    @Autowired
    private AlimentsMapper alimentosMapper;

    @Autowired
    private DietaRepository dietaRepository;

    public AlimentsResponseDTO criarAlimento(AlimentsRequestDTO alimentosRequestDTO) {

        // Cria um novo alimento
        Aliments alimentos = alimentosMapper.toEntity(alimentosRequestDTO);

        // Obtém a dieta
        Dietas dieta = dietaRepository.findById(alimentosRequestDTO.getIdDieta()).orElseThrow(() -> new IllegalArgumentException("A dieta com ID " + alimentosRequestDTO.getIdDieta() + " não existe."));

        // Adiciona o alimento à lista de alimentos da dieta
        dieta.getAlimentos().add(alimentos);
        alimentos.setDieta(dieta);

        // Salva a dieta
        dietaRepository.save(dieta);

        // Retorna o alimento criado
        return alimentosMapper.toResponseDto(alimentos);
    }


    public AlimentsResponseDTO atualizarAlimento(Integer id, AlimentsRequestDTO alimentosRequestDTO) {

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
        return alimentosMapper.toResponseDto(alimentos);
    }

    public void excluirAlimento(Integer id) {

        // Encontra o alimento
        Aliments alimentos = alimentosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alimento não encontrado"));

        // Exclui o alimento
        alimentosRepository.delete(alimentos);
    }

    public List<AlimentsResponseDTO> listarAlimentos() {

        // Lista os alimentos
        List<Aliments> alimentos = alimentosRepository.findAll();

        // Converte os alimentos para DTOs
        List<AlimentsResponseDTO> alimentosResponseDTOs = alimentos.stream()
                .map(alimentosMapper::toResponseDto)
                .collect(Collectors.toList());

        return alimentosResponseDTOs;
    }

    public Aliments obterPorID(Integer id) {

        Optional<Aliments> alimentsOptional = alimentosRepository.findById(id);

        if (alimentsOptional.isEmpty()) {
            throw new IllegalArgumentException("O Alimento não existe");
        }

        return alimentsOptional.get();
    }

    public AlimentsRequestDTO obterAlimentsPorIdRequest(Integer id) {
        Aliments alimento = alimentosRepository.findById(id).orElse(null);
        if (alimento == null) {
            throw new EntityNotFoundException("Nenhum alimento encontrado para o ID fornecido.");
        }
        return alimentosMapper.toRequestDto(alimento);
    }

    public AlimentsResponseDTO obterAlimentsPorIdResponse(Integer id) {
        Aliments alimento = alimentosRepository.findById(id).orElse(null);
        if (alimento == null) {
            throw new EntityNotFoundException("Nenhum usuário encontrado para o ID fornecido.");
        }
        return alimentosMapper.toResponseDto(alimento);
    }

    public List<AlimentsResponseDTO> listarAlimentosPorDieta(Integer idDieta) {
        List<Aliments> aliments = alimentosRepository.findByDieta_id(idDieta);

        return aliments.stream()
                .map(alimentosMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
