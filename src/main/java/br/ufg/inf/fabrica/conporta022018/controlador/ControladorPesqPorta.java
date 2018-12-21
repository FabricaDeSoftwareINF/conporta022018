/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.dto.FiltroDTO;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import java.util.List;

public class ControladorPesqPorta {

    private PortariaDAO portariaDao = new PortariaDAO();

    /**
     * Lista uma coleção de {@link Portaria} conforme o filtro informado
     *
     * @param filtro filtro de pesquisa
     * @return coleção de {@link Portaria} caso sejam encontrados resultados
     * para o filtro informado.
     */
    public List<Portaria> pesqPorta(FiltroDTO filtro) {
        return portariaDao.pesquisarPortaria(filtro);
    }

}
