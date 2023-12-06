package com.senac.Nutripulse.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
