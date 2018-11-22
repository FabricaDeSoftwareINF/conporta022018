/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*
 * Classe responsável por gerenciar as responsábilidades de manter dados de um servidor no sistema de gestão de portarias.
 */
public class MantServ {

    public void cadastrarServidor(JsonObject dadosCadastrais) {

        UndAdmDAO unidadeDAO = new UndAdmDAO();

        //UndAdm unidade = unidadeDAO.pesquisarUmJPQLCustomizada();

    }
}
