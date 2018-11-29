package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.dto.FiltroDTO;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDao;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador da Portaria
 *
 * @author Iago Bruno
 * @since 1.0
 */
public class PortariaControlador {

  private PortariaDao portariaDao;

  /**
   * Retorna uma lista de {@link Portaria} conforme o filtro informado.
   *
   * @param filtro filtros informados para a consulta.
   */
  public List<Portaria> pesquisa(FiltroDTO filtro) {

    try {

      return portariaDao.pesquisarPortaria(filtro);

    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<Portaria>();
    }

  }

}
