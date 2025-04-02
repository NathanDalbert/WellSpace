package com.WellSpace.modules.salas.domain;


import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @Column(name = "horario_inicio", nullable = false)
    private LocalDateTime horarioInicio;
    @Column(name = "horario_fim", nullable = false)
    private LocalDateTime horarioFim;
    @Column(name = "disponibilidade", nullable = false)
    private DisponibilidadeEnum disponibilidade;


    public  Salas(String descricao, String tamanho, Float precoHora, LocalDateTime horarioInicio, LocalDateTime horarioFim, DisponibilidadeEnum disponibilidade) {
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.precoHora = precoHora;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.disponibilidade = disponibilidade;
    }
    public  static  Salas newSalas(String descricao, String tamanho, Float precoHora, LocalDateTime horarioInicio, LocalDateTime horarioFim, DisponibilidadeEnum disponibilidade) {
        return new Salas(descricao, tamanho, precoHora, horarioInicio, horarioFim, disponibilidade);
    }

}
