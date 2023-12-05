package com.senac.Nutripulse.Model;

import java.util.ArrayList;
import java.util.List;

import com.senac.Nutripulse.Enums.Caso;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_dieta")
public class Dietas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dieta_id")
    private Integer id;

    @Column(name = "dieta_nome", nullable = false, unique = false, length = 30)
    private String nome;

    @Column(name = "dieta_descricao", nullable = false, unique = false, length = 3000)
    private String descricao;

    @Column(name = "dieta_caso", nullable = false, unique = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Caso caso;

    @ToString.Exclude
    @OneToMany(mappedBy = "dieta", cascade = CascadeType.ALL)
    private List<Aliments> alimentos = new ArrayList<>();

    
}
