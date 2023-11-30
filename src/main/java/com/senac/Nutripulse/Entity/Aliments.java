package com.senac.Nutripulse.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_alimentos")
public class Aliments {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "alimento_id")
  private Integer id;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "dieta_id", referencedColumnName = "dieta_id",
        foreignKey = @ForeignKey(name = "fk_alimento_dieta"))
  private Dietas dieta;

  @Column(name = "alimento_name",  nullable = false, unique = false, length = 30)
  private String nome;

  @Column(name = "alimento_cal",  nullable = false, unique = false, length = 10)
  private double calorias;

  @Column(name = "alimento_prot",  nullable = false, unique = false, length = 10)
  private double proteinas;

  @Column(name = "alimento_carb",  nullable = false, unique = false, length = 10)
  private double carboidratos;

  @Column(name = "alimento_gord",  nullable = false, unique = false, length = 10)
  private double gorduras;

}
