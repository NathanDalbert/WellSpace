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
@Table(name = "contatos")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID contato_id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "contato", nullable = false)
    private String contato;

    public Contato(Usuario usuario, String contato) {
        this.usuario = usuario;
        this.contato = contato;
    }

    public static Contato newContato(Usuario usuario, String contato) {
        return new Contato(usuario, contato);
    }
}
