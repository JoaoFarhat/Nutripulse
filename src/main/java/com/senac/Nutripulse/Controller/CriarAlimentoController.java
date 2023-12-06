package com.senac.Nutripulse.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.senac.Nutripulse.DTO.Request.AlimentsRequestDTO;
import com.senac.Nutripulse.Service.AlimentsService;

@Controller
@RequestMapping("/adm/criar-alimento")
public class CriarAlimentoController {

    @Autowired
    private AlimentsService alimentsService;

    @GetMapping()
    public String cricaoAlimento() {
        return "criar-alimento";
    }

    @PostMapping()
    public String adicionarAlimentoDieta(AlimentsRequestDTO alimentsRequestDTO){
        
        alimentsService.criarAlimento(alimentsRequestDTO);
        
        return "redirect:/adm/dietas";
    }
}
