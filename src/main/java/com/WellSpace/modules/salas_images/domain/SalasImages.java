package com.WellSpace.modules.salas_images.domain;

import java.util.UUID;

import com.WellSpace.modules.salas.domain.Salas;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "sala_imagens")
@Entity(name = "sala_imagens")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class SalasImages {
    @Id
    @Column(name = "salas_images_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID salasImagesId;

    @Column(name = "imagem", nullable = false)
    private String imagem;

    @ManyToOne
    @JoinColumn(name = "salas_id")
    private Salas sala;

}
