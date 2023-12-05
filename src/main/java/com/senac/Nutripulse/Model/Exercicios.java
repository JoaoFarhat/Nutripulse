package com.senac.Nutripulse.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_exercicio")
public class Exercicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exerc_id")
    private Integer id;

    @Column(name = "exerc_name", nullable = false, unique = true, length = 30)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "treino_id", referencedColumnName = "treino_id",
            foreignKey = @ForeignKey(name = "fk_exercicio_treino"))
    private Treinos treino;

    @Column(name = "exerc_desc", nullable = false, unique = false, length = 3000)
    private String descricao;

    @Column(name = "exerc_rep", nullable = false, unique = false, length = 3)
    private int repeticoes;

    @Column(name = "exerc_series", nullable = false, unique = false, length = 3)
    private int series;
}
