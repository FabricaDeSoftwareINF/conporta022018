package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;

import java.util.Map;

public class UndAdmDAO extends GenericoDAO {

    public UndAdmDAO(){
        super();
    }

    public UndAdm pesquisarUndAdm(String sigla, Map<String,Object> paramentros){
        return (UndAdm) super.pesquisarUmJPQLCustomizada(sigla, paramentros);
    }

    public void editarTimeOut(UndAdm undAdm) {
        super.abrirTransacao();
        try {
            super.salvar(undAdm);
            super.commitarTransacao();
        } catch (Exception exc) {
            super.rollBackTransacao();
        }
    }

}