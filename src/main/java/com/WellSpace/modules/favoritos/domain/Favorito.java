package com.WellSpace.modules.favoritos.domain;

import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favoritos", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "sala_id"}))
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "favorito_id", updatable = false, nullable = false)
    private UUID favoritoId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sala_id", nullable = false)
    private Salas sala;

    public Favorito(Usuario usuario, Salas sala) {
        this.usuario = usuario;
        this.sala = sala;
    }

    public static Favorito novoFavorito(Usuario usuario, Salas sala) {
        return new Favorito(usuario, sala);
    }
}
