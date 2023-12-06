package com.senac.Nutripulse.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.senac.Nutripulse.DTO.Request.UsersRequestDTO;
import com.senac.Nutripulse.Model.Users;
import com.senac.Nutripulse.Repository.UsersRepository;

@Controller
@RequestMapping("/adm/criar-adm")
public class CriarAdmController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping()
    public String criacaoAdm(final Model model) {
        model.addAttribute("users", new Users());
        return "criar-adm";
    }

    @PostMapping()
    public String adicionarAdm(UsersRequestDTO users){

        String encryptedPassword = new BCryptPasswordEncoder().encode(users.getSenha());
        
        Users users2 = new Users(users.getEmail(), encryptedPassword, users.getRole());

        usersRepository.save(users2);
        
        return "redirect:/adm/dietas";
    }
}