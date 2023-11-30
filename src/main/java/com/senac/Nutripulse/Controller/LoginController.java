package com.senac.Nutripulse.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.senac.Nutripulse.DTO.AuthenticationDTO;
import com.senac.Nutripulse.DTO.Request.UsersRequestDTO;
import com.senac.Nutripulse.DTO.Response.LoginResponseDTO;
import com.senac.Nutripulse.Entity.Users;
import com.senac.Nutripulse.Security.TokenService;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private
    TokenService tokenService;
    
    @GetMapping
    public String init(final Model model) {
        model.addAttribute("users", new UsersRequestDTO());
        return "login";
    }

    public ModelAndView redirect(final Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView logar(@ModelAttribute AuthenticationDTO dto) {
        var userSenha = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = this.authenticationManager.authenticate(userSenha);

        var token = tokenService.generateToken( (Users) auth.getPrincipal());

        new LoginResponseDTO(token);
        return new ModelAndView("redirect:/");

    }
    
}
