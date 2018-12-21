package br.ufg.inf.fabrica.conporta022018.modelo;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Perfil extends ModeloAbstrato {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column
    private String nome;
    @NotNull
    @Column
    private String descricao;
    @ManyToMany(cascade= {CascadeType.ALL})
    @JoinColumn(name ="permissoes_id")
    private List<Permissao> permissoes;
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes){
        this.permissoes = permissoes;
    }
}
