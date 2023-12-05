package com.senac.Nutripulse.Controller;

import com.senac.Nutripulse.DTO.Response.AlimentsResponseDTO;
import com.senac.Nutripulse.DTO.Response.DietasResponseDTO;
import com.senac.Nutripulse.Service.AlimentsService;
import com.senac.Nutripulse.Service.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/adm/dietas")
public class PagDietaAdm {
    @Autowired
    private AlimentsService alimentsService;

    @Autowired
    DietaService dietaService;

    @GetMapping()
    public String listarDietas(Model model){
        List<DietasResponseDTO> listaDietas = dietaService.listarDietasResponse();
        model.addAttribute("listaDietas", listaDietas);
        return "dietas";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesDieta(@PathVariable("id") final Integer id, Model model) {
        DietasResponseDTO dietasResponseDTO = dietaService.obterDietaPorIdResponse(id);
        List<AlimentsResponseDTO> aliments = alimentsService.listarAlimentosPorDieta(id);
        model.addAttribute("dietas", dietasResponseDTO);
        model.addAttribute("alimentos", aliments);
        return "detalhes-dietas-adm";
    }

    @GetMapping("/deletar/{id}")
    public String excluirDieta(@PathVariable("id") final Integer id) {
        dietaService.excluirDieta(id);
        return "redirect:/adm/dietas";
    }
}
