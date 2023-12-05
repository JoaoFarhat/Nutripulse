package com.senac.Nutripulse.API;

import com.senac.Nutripulse.DTO.AuthenticationDTO;
import com.senac.Nutripulse.DTO.RegistroDTO;
import com.senac.Nutripulse.DTO.Response.LoginResponseDTO;
import com.senac.Nutripulse.Model.Users;
import com.senac.Nutripulse.Repository.UsersRepository;
import com.senac.Nutripulse.Security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AdmController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private
    TokenService tokenService;

    @GetMapping
    public String init(final Model model) {
        model.addAttribute("users", new Users());
        return "login";
    }
    @PostMapping("/cadastro")
    public ResponseEntity register(@RequestBody @Valid RegistroDTO dto){
        if(this.usersRepository.findByEmail(dto.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.senha());
        Users novoUsers = new Users(dto.email(), encryptedPassword, dto.role());

        this.usersRepository.save(novoUsers);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity  login(@RequestBody @Valid AuthenticationDTO dto) {
        var userSenha = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = this.authenticationManager.authenticate(userSenha);

        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
