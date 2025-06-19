package com.WellSpace.modules.usuario.domain;

import com.WellSpace.modules.contato.domain.Contato;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.usuario.domain.ENUM.UsuarioRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID usuarioId;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "foto_perfil", nullable = false)
    private String fotoPerfil;

    @Column(name = "integridade", nullable = false)
    private Boolean integridade;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 50)
    private UsuarioRole usuarioRole = UsuarioRole.LOCATARIO;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salas> salas;

    private Usuario(String nome, String email, String senha, String fotoPerfil, Boolean integridade, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.fotoPerfil = fotoPerfil;
        this.integridade = integridade;
        this.dataNascimento = dataNascimento;
        this.usuarioRole = UsuarioRole.LOCATARIO;
    }

    public static Usuario newUsuario(String nome, String email, String senha, String fotoPerfil, Boolean integridade, LocalDate dataNascimento) {
        Usuario usuario = new Usuario(nome, email, senha, fotoPerfil, integridade, dataNascimento);
        return usuario;
    }
}