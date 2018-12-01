/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.persistencia.GenericoDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;
public class ControladorManterUndAdm {

    private final int TEMPO_MAXIMO = 60;
    private final int TEMPO_MINIMO = 15;

    public void editarTimeOut(int timeOut, String sigla) {
        //verificar se a unidade adm existe
        UndAdmDAO undAdmDao = new UndAdmDAO();
//        if (undAdmDao.pesquisarUndAdm(sigla) != null && timeOut >= TEMPO_MINIMO && timeOut <= TEMPO_MAXIMO) {
//            undAdmDao.editarTimeOut(timeOut);
//        }
    }
}
