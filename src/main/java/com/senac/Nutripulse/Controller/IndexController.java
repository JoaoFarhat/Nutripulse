package com.senac.Nutripulse.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping()
    public ModelAndView home(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        if(session != null){
            modelAndView.setViewName("index-login");
            return modelAndView;
        } else{
            modelAndView.setViewName("index");
            return modelAndView;
        }
    }
}
