package com.senac.Nutripulse.Controller;

import com.senac.Nutripulse.DTO.Request.AlimentsRequestDTO;
import com.senac.Nutripulse.DTO.Response.AlimentsResponseDTO;
import com.senac.Nutripulse.DTO.Response.DietasResponseDTO;
import com.senac.Nutripulse.Entity.Aliments;
import com.senac.Nutripulse.Mapper.DietasMapper;
import com.senac.Nutripulse.Service.AlimentsService;
import com.senac.Nutripulse.Service.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("lista-dietas")
public class PagDietasController {
    @Autowired
    private AlimentsService alimentsService;

    @Autowired
    private DietasMapper dietasMapper;

    @Autowired
    DietaService dietaService;

    @GetMapping()
    public String listarDietas(Model model){
        List<DietasResponseDTO> listaDietas = dietaService.listarDietasResponse();
        model.addAttribute("listaDietas", listaDietas);
        return "pag-dietas";
    }

    @GetMapping("/{id}")
    public String detalhesDieta(@PathVariable Integer id, Model model) {
        DietasResponseDTO dietasResponseDTO = dietaService.obterDietaPorIdResponse(id);
        List<AlimentsResponseDTO> aliments = alimentsService.listarAlimentosPorDieta(id);
        model.addAttribute("dietas", dietasResponseDTO);
        model.addAttribute("alimentos", aliments);
        return "detalhes-dietas";
    }

}
