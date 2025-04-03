package com.WellSpace.modules.salas.domain;



import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "salas")
@Table(name = "salas")
public class Salas {

    @Id
    @Column(name = "salas_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID salasId;

    @Column(name = "nome_sala", nullable = false)
    private  String nomeSala;
    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;
    @Column(name = "tamanho", nullable = false)
    private String tamanho;
    @Column(name = "preco_hora", nullable = false)
    private Float precoHora;
    @Column(name = "disponibilidade_dia_semana", nullable = false)
    private String disponibilidadeDiaSemana;

    @Column(name = "disponibilidade_inicio", nullable = false)
    private LocalTime disponibilidadeInicio;

    @Column(name = "disponibilidade_fim", nullable = false)
    private LocalTime disponibilidadeFim;

    @Column(name = "disponibilidade", nullable = false)
    @Enumerated(EnumType.STRING)
    private DisponibilidadeSalaEnum disponibilidadeSala;



}
