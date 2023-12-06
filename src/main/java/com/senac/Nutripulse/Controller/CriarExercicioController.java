package com.senac.Nutripulse.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.senac.Nutripulse.DTO.Request.ExerciciosRequestDTO;
import com.senac.Nutripulse.Service.ExerciciosService;

@Controller
@RequestMapping("/adm/criar-exercicio")
public class CriarExercicioController {

    @Autowired
    private ExerciciosService exerciciosService;

    @GetMapping()
    public String cricaoAlimento() {
        return "criar-exercicio";
    }

    @PostMapping()
    public String adicionarAlimentoDieta(ExerciciosRequestDTO exerciciosRequestDTO){
        
        exerciciosService.criarExericios(exerciciosRequestDTO);
        
        return "redirect:/adm/treinos";
    }
}
