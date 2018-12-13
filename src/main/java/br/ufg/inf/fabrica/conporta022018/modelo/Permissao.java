package br.ufg.inf.fabrica.conporta022018.modelo;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Permissao extends ModeloAbstrato {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column
    private String urlFuncionalidade;
    @NotNull
    @Column
    private String operacao;

    public String getUrlFuncionalidade() {
        return urlFuncionalidade;
    }

    public void setUrlFuncionalidade(String urlFuncionalidade) {
        this.urlFuncionalidade = urlFuncionalidade;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

}
