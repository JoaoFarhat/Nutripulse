package com.senac.Nutripulse.Service;

import com.senac.Nutripulse.DTO.Request.ExerciciosRequestDTO;
import com.senac.Nutripulse.DTO.Response.ExerciciosResponseDTO;
import com.senac.Nutripulse.Mapper.ExerciciosMapper;
import com.senac.Nutripulse.Model.Exercicios;
import com.senac.Nutripulse.Model.Treinos;
import com.senac.Nutripulse.Repository.ExerciciosRepository;
import com.senac.Nutripulse.Repository.TreinosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciciosService {
    @Autowired
    private ExerciciosRepository exerciciosRepository;

    @Autowired
    private ExerciciosMapper exerciciosMapper;

    @Autowired
    private TreinosRepository treinosRepository;

    public ExerciciosResponseDTO criarExericios(ExerciciosRequestDTO exerciciosRequestDTO, @RequestParam Integer id) {

        // Cria um novo alimento
        Exercicios exercicios = exerciciosMapper.toEntity(exerciciosRequestDTO);

        // Obtém a treinos
        Treinos treinos = treinosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("A treinos com ID " + id + " não existe."));

        // Adiciona o alimento à lista de exercicios da treinos
        treinos.getExercicios().add(exercicios);
        exercicios.setTreino(treinos);

        // Salva a treinos
        treinosRepository.save(treinos);

        // Retorna o alimento criado
        return exerciciosMapper.toResponseDto(exercicios);
    }


    public ExerciciosResponseDTO atualizarExericios(Integer id, ExerciciosRequestDTO exerciciosRequestDTO) {

        // Encontra o alimento
        Exercicios exercicios = exerciciosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exericios não encontrado"));

        // Atualiza o alimento
        exercicios.setNome(exerciciosRequestDTO.getNome());
        exercicios.setDescricao(exerciciosRequestDTO.getDescricao());
        exercicios.setSeries(exerciciosRequestDTO.getSeries());
        exercicios.setRepeticoes(exerciciosRequestDTO.getRepeticoes());

        // Salva o alimento
        exerciciosRepository.save(exercicios);

        // Retorna o alimento atualizado
        return exerciciosMapper.toResponseDto(exercicios);
    }

    public void excluirExericios(Integer id) {

        // Encontra o alimento
        Exercicios exercicios = exerciciosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exericios não encontrado"));

        // Exclui o alimento
        exerciciosRepository.delete(exercicios);
    }

    public List<ExerciciosResponseDTO> listarExericioss() {

        // Lista os exercicios
        List<Exercicios> exercicios = exerciciosRepository.findAll();

        // Converte os exercicios para DTOs
        List<ExerciciosResponseDTO> exerciciosResponseDTOs = exercicios.stream()
                .map(exerciciosMapper::toResponseDto)
                .collect(Collectors.toList());

        return exerciciosResponseDTOs;
    }

    public Exercicios obterPorID(Integer id) {

        Optional<Exercicios> alimentsOptional = exerciciosRepository.findById(id);

        if (alimentsOptional.isEmpty()) {
            throw new IllegalArgumentException("O Exericios não existe");
        }

        return alimentsOptional.get();
    }

    public ExerciciosRequestDTO obterExerciciosPorIdRequest(Integer id) {
        Exercicios alimento = exerciciosRepository.findById(id).orElse(null);
        if (alimento == null) {
            throw new EntityNotFoundException("Nenhum alimento encontrado para o ID fornecido.");
        }
        return exerciciosMapper.toRequestDto(alimento);
    }

    public ExerciciosResponseDTO obterExerciciosPorIdResponse(Integer id) {
        Exercicios alimento = exerciciosRepository.findById(id).orElse(null);
        if (alimento == null) {
            throw new EntityNotFoundException("Nenhum usuário encontrado para o ID fornecido.");
        }
        return exerciciosMapper.toResponseDto(alimento);
    }

    public List<ExerciciosResponseDTO> listarExerciciosPorTreinos(Integer idTreino) {
        List<Exercicios> treinos = exerciciosRepository.findByTreino_id(idTreino);

        return treinos.stream()
                .map(exerciciosMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
