package com.WellSpace.modules.localidades.domain;

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
@Entity(name= "localidades")
@Table(name= "localidades")
public class Localidades {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID localidadeId;

    @Column(name = "nome_local",nullable = false)
    private String nomeLocal;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "localizacao", nullable = false)
    private String localizacao;

    @ManyToOne
    @JoinColumn(name = "locador_id")
    private Usuario locador;

    public Localidades(String nomeLocal, String descricao, String localizacao) {
        this.nomeLocal = nomeLocal;
        this.descricao = descricao;
        this.localizacao = localizacao;

    }

    public static Localidades newLocalidade(String nome_local, String descricao, String localizacao){
        return new Localidades(nome_local,descricao,localizacao);
    }
}
