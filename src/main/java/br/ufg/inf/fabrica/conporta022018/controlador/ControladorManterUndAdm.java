/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.persistencia.GenericoDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;

import java.util.HashMap;

public class ControladorManterUndAdm {

    private final int TEMPO_MAXIMO = 60;
    private final int TEMPO_MINIMO = 15;

    public void editarTimeOut(int minInat, String sigla) {
        //verificar se a unidade adm existe
        UndAdmDAO undAdmDao = new UndAdmDAO();
        String jpql = "SELECT u UndAdm u WHERE ";
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("u.sigla", sigla);
        UndAdm resultadoDaBusca = undAdmDao.pesquisarUndAdm(jpql, parametros);
        if (resultadoDaBusca != null && minInat >= TEMPO_MINIMO && minInat <= TEMPO_MAXIMO) {
            UndAdm unidadeAlterada = resultadoDaBusca;
            unidadeAlterada.setMinInat(minInat);
            undAdmDao.editarTimeOut(unidadeAlterada);
        }
    }
}
 