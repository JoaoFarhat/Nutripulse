package com.senac.Nutripulse.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.senac.Nutripulse.DTO.Request.AlimentsRequestDTO;
import com.senac.Nutripulse.Service.AlimentsService;
import com.senac.Nutripulse.Service.DietaService;

@Controller
@RequestMapping("/adm/alimento")
public class CriarAlimentoController {

    @Autowired
    private AlimentsService alimentsService;

    @Autowired
    DietaService dietaService;

    @GetMapping("/criar/{id}")
    public String cricaoAlimento() {
        return "criar-alimento";
    }

    @PostMapping("/criar/{id}")
    public String adicionarAlimentoDieta(@PathVariable("id") Integer id, 
                                          AlimentsRequestDTO alimentsRequestDTO){
        
        alimentsService.criarAlimento(alimentsRequestDTO, id);
        
        return "redirect:/adm/dietas";
    }
}
