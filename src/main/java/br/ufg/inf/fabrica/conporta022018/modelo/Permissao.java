package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Permissao extends ModeloAbstrato {

    private static final long serialVersionUID = 1L;

    private String urlFuncionalidade;
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
