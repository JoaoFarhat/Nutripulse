package com.senac.Nutripulse.Controller;

import com.senac.Nutripulse.DTO.Request.DietasRequestDTO;
import com.senac.Nutripulse.Service.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm/criacao-dietas")
public class CriarDietasController {

    @Autowired
    DietaService dietaService;

    @GetMapping()
    public String cricaoDietas() {
        return "criacao-dietas";
    }

    @PostMapping()
    public String criarDietas(DietasRequestDTO dietasRequestDto){
        dietaService.criarDieta(dietasRequestDto);
        return "redirect:/adm/dietas";
    }
}