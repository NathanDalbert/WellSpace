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
    public UUID getLocalidadeId() {
        return localidadeId;
    }

    public void setLocalidadeId(UUID localidadeId) {
        this.localidadeId = localidadeId;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Usuario getLocador() {
        return locador;
    }

    public void setLocador(Usuario locador) {
        this.locador = locador;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID localidadeId;

    @Column(name = "nome_local",nullable = false)
    private String nomeLocal;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "localizacao", nullable = false, columnDefinition = "POINT")
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
