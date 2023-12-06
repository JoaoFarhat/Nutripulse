package com.senac.Nutripulse.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.senac.Nutripulse.DTO.Request.UsersRequestDTO;
import com.senac.Nutripulse.DTO.Response.UsersResponseDTO;
import com.senac.Nutripulse.Enums.Role;
import com.senac.Nutripulse.Mapper.UsersMapper;
import com.senac.Nutripulse.Model.Users;
import com.senac.Nutripulse.Repository.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

import static com.senac.Nutripulse.Enums.Caso.*;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    
    private final UsersMapper usersMapper;

    @Autowired
    public UsersService(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    public List<UsersResponseDTO> listarUsersResponse() {
        List<? extends Users> users = usersRepository.findAll();

        return users.stream()
                .map(usersMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    
    public List<UsersRequestDTO> listarUsersRequest() {
        List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(usersMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public UsersResponseDTO obterUsersPorIdResponse(Integer id) {
        Users users = usersRepository.findById(id).orElse(null);
        if (users == null) {
            throw new EntityNotFoundException("Nenhum usuário encontrado para o ID fornecido.");
        }
        return usersMapper.toResponseDto(users);
    }

    public UsersRequestDTO obterUsersPorIdRequest(Integer id) {
        Users users = usersRepository.findById(id).orElse(null);
        if (users == null) {
            throw new EntityNotFoundException("Nenhum usuário encontrado para o ID fornecido.");
        }
        return usersMapper.toRequestDto(users);
    }

        public void criarUsers(UsersRequestDTO usersRequestDto) {
            try {
                validarDadosDuplicadosSemId(usersRequestDto);
                String encryptedPassword = new BCryptPasswordEncoder().encode(usersRequestDto.getSenha());
                usersRequestDto.setSenha(encryptedPassword);
                
                Users users = usersMapper.toEntity(usersRequestDto);
                calcularIMC(users);
                colocarCaso(users);
                users.setRole(Role.USER);
                usersRepository.save(users);
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Ocorreu um erro ao criar o usuário. Verifique os dados informados.");
            } catch (Exception e) {
                throw new RuntimeException("Ocorreu um erro inesperado ao criar o usuário.", e);
            }
        }

    public void atualizarUsers(Integer id, UsersRequestDTO usersRequestDto) throws NotFoundException {
        usersRepository.findById(id).orElseThrow(NotFoundException::new);
    
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Atualizando usuário com ID: {0}", id);
    
        validarDadosDuplicadosComId(usersRequestDto, id);
        Users userAtualizado = usersMapper.toEntity(usersRequestDto);
        calcularIMC(userAtualizado);
        userAtualizado.setId(id);
        usersRepository.save(userAtualizado);
    }
    

        public void excluirUsers(Integer id) throws NotFoundException {
        usersRepository.findById(id).orElseThrow(NotFoundException::new);

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Excluindo usuário com ID: {0}", id);

        usersRepository.deleteById(id);
}
    

private void validarDadosDuplicadosComId(UsersRequestDTO usuarioRequestDto, Integer id) {
    for(Users usuario : usersRepository.findAll()){
        if(!(id.equals(usuario.getId()))){
            if(usuario.getEmail().equals(usuarioRequestDto.getEmail())){
                throw new DataIntegrityViolationException("Endereço de e-mail já em uso.");
            }
        }
    }
}
private void validarDadosDuplicadosSemId(UsersRequestDTO usuarioRequestDto) {
    for(Users usuario : usersRepository.findAll()){
        if(usuario.getEmail().equals(usuarioRequestDto.getEmail())){
            throw new DataIntegrityViolationException("Endereço de e-mail já em uso.");
        }
    }
}

    private void calcularIMC(Users users) {
        if (users.getAltura() <= 0) {
            throw new IllegalArgumentException("A altura deve ser maior que zero");
        }

        if (users.getPeso() <= 0) {
            throw new IllegalArgumentException("O peso deve ser maior que zero");
        }

        users.setIMC(users.getPeso() / Math.pow(users.getAltura(), 2));

    }
    private void colocarCaso(Users users){
        if (users.getIMC() < 18.5) {
            users.setCasoDietas(ENGORDAR);
        } else if (users.getIMC() < 25) {
            users.setCasoDietas(DEFINIR);
        } else {
            users.setCasoDietas(EMAGRECER);
        }
    }

}
