package com.WellSpace.modules.usuario.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "usuario")
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Usuario_id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "fotoPerfil", nullable = false)
    private String fotoPerfil;

    @Column(name = "integridade", nullable = false)
    private Boolean integridade;

    @Column(name = "dataNascimento", nullable = false)
    private LocalDate dataNascimento;

    public Usuario(String nome, String email, String senha, String fotoPerfil, Boolean integridade, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.fotoPerfil = fotoPerfil;
        this.integridade = integridade;
        this.dataNascimento = dataNascimento;
    }

    public static Usuario newUsuario(String nome, String email, String senha, String fotoPerfil, Boolean integridade, LocalDate dataNascimento) {
        return new Usuario(nome, email, senha, fotoPerfil, integridade, dataNascimento);
    }

}
