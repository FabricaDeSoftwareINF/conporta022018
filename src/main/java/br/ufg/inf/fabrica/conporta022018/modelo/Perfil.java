package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Perfil extends ModeloAbstrato {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String descricao;
    @ManyToMany
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
