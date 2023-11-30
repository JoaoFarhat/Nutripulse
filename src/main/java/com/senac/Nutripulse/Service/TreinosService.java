package com.senac.Nutripulse.Service;

import com.senac.Nutripulse.DTO.Request.ExerciciosRequestDTO;
import com.senac.Nutripulse.DTO.Request.TreinosRequestDTO;
import com.senac.Nutripulse.DTO.Response.TreinosResponseDTO;
import com.senac.Nutripulse.Entity.Exercicios;
import com.senac.Nutripulse.Entity.Treinos;
import com.senac.Nutripulse.Mapper.TreinosMapper;
import com.senac.Nutripulse.Repository.TreinosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TreinosService {
    @Autowired
    private TreinosRepository treinosRepository;

    @Autowired
    private TreinosMapper treinosMapper;

    @Autowired
    public TreinosService(TreinosRepository treinoRepository, TreinosMapper treinoMapper) {
        this.treinosRepository = treinoRepository;
        this.treinosMapper = treinoMapper;
    }

    public List<TreinosResponseDTO> listarTreinosResponse() {
        List<Treinos> treinos = treinosRepository.findAll();

        return treinos.stream()
                .map(treinosMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<TreinosRequestDTO> listarTreinosRequest() {
        List<Treinos> treinos = treinosRepository.findAll();

        return treinos.stream()
                .map(treinosMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public TreinosResponseDTO criarTreino(TreinosRequestDTO treinosRequestDTO) {

        // Valida se a treino tem pelo menos 3 exercicios
        if (treinosRequestDTO.getExercicios().size() < 3) {
            throw new IllegalArgumentException("A treino deve ter pelo menos 3 exercicios");
        }

        // Cria uma nova treino
        Treinos treinos = new Treinos();
        treinos.setModo(treinosRequestDTO.getModo());
        treinos.setDescricao(treinosRequestDTO.getDescricao());

        // Cria os exercicios da treino
        for (ExerciciosRequestDTO exerciciosRequestDTO : treinosRequestDTO.getExercicios()) {
            Exercicios exercicios = new Exercicios();
            exercicios.setNome(exerciciosRequestDTO.getNome());
            exercicios.setDescricao(exerciciosRequestDTO.getDescricao());
            exercicios.setSeries(exerciciosRequestDTO.getSeries());
            exercicios.setRepeticoes(exerciciosRequestDTO.getRepeticoes());
            exercicios.setTreino(treinos);
            treinos.getExercicios().add(exercicios);
        }

        // Salva a treino
        treinosRepository.save(treinos);

        // Retorna a treino criada
        return treinosMapper.toResponseDto(treinos);
    }


    public Treinos obterPorID(Integer id) {

        Optional<Treinos> treinosOptional = treinosRepository.findById(id);

        if (treinosOptional.isEmpty()) {
            throw new IllegalArgumentException("A treino não existe");
        }

        return treinosOptional.get();
    }

    public TreinosRequestDTO obterTreinoPorIdRequest(Integer id) {
        Treinos treino = treinosRepository.findById(id).orElse(null);
        if (treino == null) {
            throw new EntityNotFoundException("Nenhum usuário encontrado para o ID fornecido.");
        }
        return treinosMapper.toRequestDto(treino);
    }

    public TreinosResponseDTO obterTreinoPorIdResponse(Integer id) {
        Treinos treino = treinosRepository.findById(id).orElse(null);
        if (treino == null) {
            throw new EntityNotFoundException("Nenhum usuário encontrado para o ID fornecido.");
        }
        return treinosMapper.toResponseDto(treino);
    }

    public TreinosResponseDTO atualizarTreino(TreinosRequestDTO treinosRequestDTO) {

        // Encontra a treino
        Treinos treinos = treinosRepository.findById(treinosRequestDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Treino não encontrada"));

        // Atualiza os atributos da treino
        treinos.setModo(treinosRequestDTO.getModo());
        treinos.setDescricao(treinosRequestDTO.getDescricao());

        // Salva a treino
        treinosRepository.save(treinos);

        // Retorna a treino atualizada
        return treinosMapper.toResponseDto(treinos);
    }


    public void excluirTreino(Integer id) {
        obterTreinoPorIdResponse(id);
        treinosRepository.deleteById(id);
    }

}
