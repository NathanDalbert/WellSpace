package com.WellSpace.modules.usuario.domain;

import com.WellSpace.modules.contato.domain.Contato;
import com.WellSpace.modules.usuario.domain.ENUM.UsuarioRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "usuario")
@Table(name = "usuario")
public class Usuario  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID usuarioId;

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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private UsuarioRole usuarioRole;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatos;


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
