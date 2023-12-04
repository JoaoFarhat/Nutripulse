package com.senac.Nutripulse.Controller;

import com.senac.Nutripulse.Repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/login")
public class LoginUsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private
    TokenService tokenService;

    @Autowired
    private UsersRepository usersRepository;
    
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

        if (!userSenha.getPrincipal().equals(dto.email())) {
            throw new BadCredentialsException("E-mail ou senha inválidos");
        }

        var auth = authenticationManager.authenticate(userSenha);
        var token = tokenService.generateToken( (Users) auth.getPrincipal());

        new LoginResponseDTO(token);

        HttpSession session = request.getSession(true);
        session.setAttribute("loggedInUser", dto.email());

        UserDetails users = usersRepository.findByEmail(dto.email());
        Set<String> authorities = users.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());


        if (authorities.contains("ROLE_ADMIN")) {
            // Redireciona para a rota dos administradores
            return new ModelAndView("redirect:/lista-dietas");
        } else if ((authorities.contains("ROLE_USER"))){
            // Redireciona para a rota dos usuários comuns
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("redirect:/login");
        }



        //return new ModelAndView("redirect:/");
    }
    
}
