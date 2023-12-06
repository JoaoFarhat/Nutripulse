package com.senac.Nutripulse.Controller;

import com.senac.Nutripulse.DTO.Response.ExerciciosResponseDTO;
import com.senac.Nutripulse.DTO.Response.TreinosResponseDTO;
import com.senac.Nutripulse.Service.ExerciciosService;
import com.senac.Nutripulse.Service.TreinosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/adm/treinos")
public class PagTreinosAdmController {
    @Autowired
    private TreinosService treinosService;

    @Autowired
    private ExerciciosService exerciciosService;

    @GetMapping()
    public String listarTreinos(Model model){
        List<TreinosResponseDTO> listaTreinos = treinosService.listarTreinosResponse();
        model.addAttribute("listaTreinos", listaTreinos);
        return "treinos";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesTreino(@PathVariable("id") final Integer id, Model model) {
        TreinosResponseDTO treinosResponseDTO = treinosService.obterTreinoPorIdResponse(id);
        List<ExerciciosResponseDTO> exercicios = exerciciosService.listarExerciciosPorTreinos(id);
        model.addAttribute("treinos", treinosResponseDTO);
        model.addAttribute("exercicios", exercicios);
        return "detalhes-treinos-adm";
    }

    @GetMapping("/deletar/{id}")
    public String excluirDieta(@PathVariable("id") final Integer id) {
        treinosService.excluirTreino(id);;
        return "redirect:/adm/treinos";
    }
}
