package com.WellSpace.modules.salas.domain;

import com.WellSpace.modules.avaliacoes.domain.Avaliacao;
import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import com.WellSpace.modules.salas_images.domain.SalasImages;

import com.WellSpace.modules.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
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
    private String nomeSala;
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
    private DisponibilidadeSalaEnum disponibilidadeSala =DisponibilidadeSalaEnum.DISPONIVEL;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SalasImages> imagens;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Avaliacao> avaliacao;

    @ManyToOne
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario usuario;

    public Salas(String nomeSala, String descricao, String tamanho, Float precoHora, String disponibilidadeDiaSemana,
            LocalTime disponibilidadeInicio, LocalTime disponibilidadeFim,
            DisponibilidadeSalaEnum disponibilidadeSala) {
        this.nomeSala = nomeSala;
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.precoHora = precoHora;
        this.disponibilidadeDiaSemana = disponibilidadeDiaSemana;
        this.disponibilidadeInicio = disponibilidadeInicio;
        this.disponibilidadeFim = disponibilidadeFim;
        this.disponibilidadeSala = disponibilidadeSala;
    }

    public static Salas newSala(String nomeSala, String descricao, String tamanho, Float precoHora,
            String disponibilidadeDiaSemana, LocalTime disponibilidadeInicio, LocalTime disponibilidadeFim,
            DisponibilidadeSalaEnum disponibilidadeSala) {
        return new Salas(nomeSala, descricao, tamanho, precoHora, disponibilidadeDiaSemana, disponibilidadeInicio,
                disponibilidadeFim, disponibilidadeSala);
    }

}
