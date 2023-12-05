package com.senac.Nutripulse.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.senac.Nutripulse.DTO.Request.AlimentsRequestDTO;
import com.senac.Nutripulse.DTO.Response.AlimentsResponseDTO;
import com.senac.Nutripulse.Model.Aliments;

@Component
public class AlimentsMapper {
    
    @Autowired
    private ModelMapper modelMapper;

    public Aliments toEntity(AlimentsResponseDTO alimentsResponseDTO) {
        return modelMapper.map(alimentsResponseDTO, Aliments.class);
    }
    public Aliments toEntity(AlimentsRequestDTO alimentsRequestDto) {
        return modelMapper.map(alimentsRequestDto, Aliments.class);
    }

    public AlimentsRequestDTO toRequestDto(Aliments alimentsEntity) {
        AlimentsRequestDTO alimentsRequestDTO = modelMapper.map(alimentsEntity, AlimentsRequestDTO.class);
        return alimentsRequestDTO;
    }

    public AlimentsResponseDTO toResponseDto(Aliments alimentsEntity) {
        AlimentsResponseDTO alimentsResponseDTO = modelMapper.map(alimentsEntity, AlimentsResponseDTO.class);
        return alimentsResponseDTO;
    }
}
