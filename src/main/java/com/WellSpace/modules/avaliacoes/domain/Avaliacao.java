package com.WellSpace.modules.avaliacoes.domain;

import com.WellSpace.modules.salas.domain.Salas;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "avaliacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "avaliacao_id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sala_id", nullable = false)
    private Salas sala;

    @Column(name = "quant_estrelas", nullable = false)
    private Integer quantEstrelas;

}
