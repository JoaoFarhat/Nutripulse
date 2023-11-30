package com.senac.Nutripulse.Mapper;

import com.senac.Nutripulse.DTO.Request.ExerciciosRequestDTO;
import com.senac.Nutripulse.DTO.Response.ExerciciosResponseDTO;
import com.senac.Nutripulse.Entity.Exercicios;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExerciciosMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Exercicios toEntity(ExerciciosResponseDTO alimentsResponseDTO) {
        return modelMapper.map(alimentsResponseDTO, Exercicios.class);
    }
    public Exercicios toEntity(ExerciciosRequestDTO alimentsRequestDto) {
        return modelMapper.map(alimentsRequestDto, Exercicios.class);
    }

    public ExerciciosRequestDTO toRequestDto(Exercicios alimentsEntity) {
        ExerciciosRequestDTO alimentsRequestDTO = modelMapper.map(alimentsEntity, ExerciciosRequestDTO.class);
        return alimentsRequestDTO;
    }

    public ExerciciosResponseDTO toResponseDto(Exercicios alimentsEntity) {
        ExerciciosResponseDTO alimentsResponseDTO = modelMapper.map(alimentsEntity, ExerciciosResponseDTO.class);
        return alimentsResponseDTO;
    }
}
