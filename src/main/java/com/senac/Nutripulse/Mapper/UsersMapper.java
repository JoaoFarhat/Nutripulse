package com.senac.Nutripulse.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.senac.Nutripulse.DTO.Request.UsersRequestDTO;
import com.senac.Nutripulse.DTO.Response.UsersResponseDTO;
import com.senac.Nutripulse.Entity.Users;

@Component
public class UsersMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public UsersMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Users toEntity(UsersResponseDTO usersResponseDTO) {
        return modelMapper.map(usersResponseDTO, Users.class);
    }
    public Users toEntity(UsersRequestDTO usersRequestDto) {
        return modelMapper.map(usersRequestDto, Users.class);
    }

    public UsersRequestDTO toRequestDto(Users usersEntity) {
        return modelMapper.map(usersEntity, UsersRequestDTO.class);
    }

    public UsersResponseDTO toResponseDto(Users usersEntity) {
        return modelMapper.map(usersEntity, UsersResponseDTO.class);
    }


}
