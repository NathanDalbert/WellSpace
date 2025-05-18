package com.WellSpace.modules.contato.domain;

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
@Entity(name = "contato")
@Table(name = "contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contato_id", updatable = false, nullable = false)
    private UUID contatoId;

    @Column(name = "contato", nullable = false)
    private String contato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


    public Contato(Usuario usuario, String contato) {
        this.usuario = usuario;
        this.contato = contato;
    }

    public static Contato newContato(Usuario usuario, String contato) {
        return new Contato(usuario, contato);
    }
}
