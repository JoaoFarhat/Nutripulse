package com.senac.Nutripulse.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.senac.Nutripulse.DTO.Request.DietasRequestDTO;
import com.senac.Nutripulse.DTO.Response.DietasResponseDTO;
import com.senac.Nutripulse.Entity.Dietas;

@Component
public class DietasMapper {
    
    @Autowired
    private ModelMapper modelMapper;

    public DietasResponseDTO toResponseDto(Dietas dieta) {
        return modelMapper.map(dieta, DietasResponseDTO.class);
    }

    public DietasRequestDTO toRequestDto(Dietas campanhaModel) {
        return modelMapper.map(campanhaModel, DietasRequestDTO.class);
    }

    public Dietas toEntity(DietasRequestDTO dietasRequestDto) {
        return modelMapper.map(dietasRequestDto, Dietas.class);
    }
    public Dietas toEntity(DietasResponseDTO dietasResponseDto) {
        return modelMapper.map(dietasResponseDto, Dietas.class);
    }
}
