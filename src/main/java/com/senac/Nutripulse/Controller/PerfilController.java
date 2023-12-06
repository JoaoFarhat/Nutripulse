package com.senac.Nutripulse.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/perfil")
public class PerfilController {
    
    @GetMapping()
    public ModelAndView perfil(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        if(session != null){
            modelAndView.setViewName("perfil");
            return modelAndView;
        } else {
           modelAndView.setViewName("login");
            return modelAndView; 
        }
    }
}
