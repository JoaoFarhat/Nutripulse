package com.senac.Nutripulse.Controller;

import com.senac.Nutripulse.DTO.Response.AlimentsResponseDTO;
import com.senac.Nutripulse.DTO.Response.DietasResponseDTO;
import com.senac.Nutripulse.Service.AlimentsService;
import com.senac.Nutripulse.Service.DietaService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("lista-dietas")
public class PagDietasController {
    @Autowired
    private AlimentsService alimentsService;

    @Autowired
    DietaService dietaService;

    @GetMapping()
    public String listarDietas(Model model, HttpSession session){
        List<DietasResponseDTO> listaDietas = dietaService.listarDietasResponse();
        model.addAttribute("listaDietas", listaDietas);
        if(session != null){
            return "pag-dietas-login";
        } else {
            return "pag-dietas";
        }
    }

    @GetMapping("/{id}")
    public String detalhesDieta(@PathVariable("id") Integer id, Model model, HttpSession session) {
        DietasResponseDTO dietasResponseDTO = dietaService.obterDietaPorIdResponse(id);
        List<AlimentsResponseDTO> aliments = alimentsService.listarAlimentosPorDieta(id);
        model.addAttribute("dietas", dietasResponseDTO);
        model.addAttribute("alimentos", aliments);
        if(session != null){
            return "detalhes-dietas-login";
        } else {
            return "detalhes-dietas";
        }
    }

}
