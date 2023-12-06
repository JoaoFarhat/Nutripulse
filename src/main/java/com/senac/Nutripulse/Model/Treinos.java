package com.senac.Nutripulse.Model;

import java.util.ArrayList;
import java.util.List;

import com.senac.Nutripulse.Enums.Modo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_treinos")
public class Treinos{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treino_id")
    private long id;

    @Column(name = "treino_nome", nullable = false, unique = false, length = 30)
    private String nome;

    @Column(name = "treino_descricao", nullable = false, unique = false, length = 3000)
    private String descricao;

    @Column(name = "treino_modo", nullable = false, unique = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Modo modo;

    @ToString.Exclude
    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL)
    private List<Exercicios> exercicios = new ArrayList<>();

}
