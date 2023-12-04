package com.senac.Nutripulse.API;

import com.senac.Nutripulse.DTO.Request.UsersRequestDTO;
import com.senac.Nutripulse.DTO.Response.UsersResponseDTO;
import com.senac.Nutripulse.Service.UsersService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping()
    public ResponseEntity<Object> criarUsers(@RequestBody @Valid UsersRequestDTO usersRequestDto) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(usersRequestDto.getSenha());
        usersRequestDto.setSenha(encryptedPassword);
        usersService.criarUsers(usersRequestDto);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Criando usuário com CPF: {0}", usersRequestDto.getCpf());
        return new ResponseEntity<>("Usuário criado com sucesso.", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> listaruserss() {
        List<UsersResponseDTO> listaRespostauserss = usersService.listarUsersResponse();

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Listando usuários.");

        if (listaRespostauserss == null || listaRespostauserss.isEmpty()) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Nenhum usuário encontrado.");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listaRespostauserss);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> obterusersPorId(@PathVariable Integer id) {
        Logger logger = Logger.getLogger(this.getClass().getName());

        logger.log(Level.INFO, "Obtendo usuário com ID: {0}", id);

        UsersResponseDTO usersResponseDto = usersService.obterUsersPorIdResponse(id);
        if (usersResponseDto == null) {
            logger.log(Level.INFO, "Usuário com ID {0} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usersResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarusers(@PathVariable Integer id, @RequestBody UsersRequestDTO usersRequestDTO) {
        Logger logger = Logger.getLogger(this.getClass().getName());

        logger.log(Level.INFO, "Atualizando usuário com ID: {0}", id);

        try {
            usersService.atualizarUsers(id, usersRequestDTO);
            return new ResponseEntity<>("Usuário atualizado com sucesso.", HttpStatus.OK);
        } catch (ValidationException e) {
            logger.log(Level.WARNING, "Erro ao validar dados do usuário: {0}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar usuário: {0}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirUsers(@PathVariable Integer id) {
        Logger logger = Logger.getLogger(this.getClass().getName());

        logger.log(Level.INFO, "Excluindo usuário com ID: {0}", id);

        try {
            usersService.excluirUsers(id);
            return new ResponseEntity<>("Usuário excluído com sucesso.", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            logger.log(Level.SEVERE, "Exclusão de usuário falhou devido a restrições de integridade de dados: {0}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Exclusão de usuário falhou devido a restrições de integridade de dados.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao excluir usuário: {0}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
