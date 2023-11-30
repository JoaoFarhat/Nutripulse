package com.senac.Nutripulse.Mapper;

import com.senac.Nutripulse.DTO.Request.TreinosRequestDTO;
import com.senac.Nutripulse.DTO.Response.TreinosResponseDTO;
import com.senac.Nutripulse.Entity.Treinos;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TreinosMapper {

    @Autowired
    private ModelMapper modelMapper;

    public TreinosResponseDTO toResponseDto(Treinos treinos) {
        return modelMapper.map(treinos, TreinosResponseDTO.class);
    }

    public TreinosRequestDTO toRequestDto(Treinos treinos) {
        return modelMapper.map(treinos, TreinosRequestDTO.class);
    }

    public Treinos toEntity(TreinosRequestDTO treinosRequestDto) {
        return modelMapper.map(treinosRequestDto, Treinos.class);
    }
    public Treinos toEntity(TreinosResponseDTO treinosResponseDto) {
        return modelMapper.map(treinosResponseDto, Treinos.class);
    }
}
