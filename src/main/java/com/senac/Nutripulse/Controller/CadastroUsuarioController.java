package com.senac.Nutripulse.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.senac.Nutripulse.DTO.Request.UsersRequestDTO;
import com.senac.Nutripulse.Entity.Users;
import com.senac.Nutripulse.Service.UsersService;

@Controller
@RequestMapping("/cadastro")
public class CadastroUsuarioController {

    @Autowired
    private UsersService usersService;
    
    @GetMapping
    public String init(final Model model) {
        model.addAttribute("users", new Users());
        return "cadastro";
    }

    @PostMapping(consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public ModelAndView result(@ModelAttribute UsersRequestDTO usersRequestDto) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(usersRequestDto.getSenha());
        usersRequestDto.setSenha(encryptedPassword);
        usersService.criarUsers(usersRequestDto);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Criando usuário com CPF: {0}", usersRequestDto.getCpf());
        //return new ResponseEntity<>("Usuário criado com sucesso.", HttpStatus.CREATED);
        ModelAndView successModelAndView = new ModelAndView("redirect:/login");
        return successModelAndView;
    }

    // @PostMapping()
    // public ModelAndView result(@ModelAttribute UsersRequestDTO usuarioRequestDto) {
    //     String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioRequestDto.getSenha());
    //     usuarioRequestDto.setSenha(encryptedPassword);
    //     usersService.criarUsers(usuarioRequestDto);

    //     ModelAndView successModelAndView = new ModelAndView("redirect:/login");
    //     return successModelAndView;
    //     for (UsersRequestDTO usuario:usersService.listarUsersRequest()) {
    //         String senha = new BCryptPasswordEncoder().encode(usuario.getSenha());
    //         if(usuario.getEmail().equals(usuarioRequestDto.getEmail()) &&  senha.equals(usuarioRequestDto.getSenha())){
    //             return new ModelAndView("redirect:/login");
    //         }
    //     }
    //     return new ModelAndView("redirect:/cadastro");
    // }
}
