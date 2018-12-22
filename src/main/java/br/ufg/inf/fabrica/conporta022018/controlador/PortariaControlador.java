package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.dto.FiltroDTO;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador da Portaria
 *
 * @author Iago Bruno
 * @since 1.0
 */
public class PortariaControlador {

  private PortariaDAO portariaDao = new PortariaDAO();

  /**
   * Retorna uma lista de {@link Portaria} conforme o filtro informado.
   *
   * @param filtro filtros informados para a consulta.
   */
  public List<Portaria> pesquisa(FiltroDTO filtro) {

    try {

      if (filtro.getInicioVigencia() != null && filtro.getFimVigencia() != null){
        if (filtro.getFimVigencia().before(filtro.getInicioVigencia())){
          throw new IllegalArgumentException("A data fim não deve ser menor que a de início");
        } else {
          return portariaDao.pesquisarPortaria(filtro);
        }
      } else {
        return portariaDao.pesquisarPortaria(filtro);
      }


    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<Portaria>();
    }

  }

}
