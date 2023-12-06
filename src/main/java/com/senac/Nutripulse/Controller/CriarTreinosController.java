package com.senac.Nutripulse.Controller;

import com.senac.Nutripulse.DTO.Request.TreinosRequestDTO;
import com.senac.Nutripulse.Service.TreinosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm/criacao-treinos")
public class CriarTreinosController {

    @Autowired
    private TreinosService treinoService;

    @GetMapping()
    public String criacaoTreinos() {
        return "criacao-treinos";
    }

    @PostMapping()
    public String criartreinos(TreinosRequestDTO treinosRequestDto){
        treinoService.criarTreino(treinosRequestDto);
        return "redirect:/adm/treinos";
    }
}