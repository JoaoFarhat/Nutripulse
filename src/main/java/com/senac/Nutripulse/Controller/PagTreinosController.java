package com.senac.Nutripulse.Controller;

import com.senac.Nutripulse.DTO.Response.ExerciciosResponseDTO;
import com.senac.Nutripulse.DTO.Response.TreinosResponseDTO;
import com.senac.Nutripulse.Service.ExerciciosService;
import com.senac.Nutripulse.Service.TreinosService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("lista-treinos")
public class PagTreinosController {

    @Autowired
    TreinosService treinosService;

    @Autowired
    ExerciciosService exerciciosService;

    @GetMapping()
    public String listarTreinos(Model model){
        List<TreinosResponseDTO> listaTreinos = treinosService.listarTreinosResponse();
        model.addAttribute("listaTreinos", listaTreinos);
        return "pag-treinos";
    }

    @GetMapping("/{id}")
    public String detalhesTreinos(@PathVariable Integer id, Model model, HttpSession session) {
        TreinosResponseDTO treinosResponseDTO = treinosService.obterTreinoPorIdResponse(id);
        List<ExerciciosResponseDTO> exercicios = exerciciosService.listarExerciciosPorTreinos(id);
        model.addAttribute("treinos", treinosResponseDTO);
        model.addAttribute("exercicios", exercicios);
        if(session != null){
            return "detalhes-treinos-login";
        } else {
            return "detalhes-treinos";
        }
    }
}
