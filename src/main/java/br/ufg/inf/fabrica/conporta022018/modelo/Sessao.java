package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Breno Gomes
 */

@Entity
public class Sessao extends ModeloAbstrato  {

    private static final long serialVersionUID = 1L;

    private Long timeOut;

    @ManyToMany
    private List<Permissao> permissoes;


    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    public boolean verificarAcesso(String urlFuncionalidade, String unidadeAdm){
        for (Permissao obj : permissoes) {
            if((urlFuncionalidade.equalsIgnoreCase(obj.getUrlFuncionalidade())) && (unidadeAdm.equalsIgnoreCase(obj.getOperacao()))) {
                return true;
            }
        }
        return false;
    }
}
