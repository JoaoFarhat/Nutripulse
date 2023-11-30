package com.senac.Nutripulse.DTO;

import com.senac.Nutripulse.Enums.Role;

public record RegistroDTO(String email, String senha, Role role) {
    
}
