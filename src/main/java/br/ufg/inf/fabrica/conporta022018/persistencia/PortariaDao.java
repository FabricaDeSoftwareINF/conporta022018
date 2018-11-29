package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.dto.FiltroDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Iago Bruno on 26/10/18.
 */
public class PortariaDao extends GenericoDAO<Portaria> {

  /**
   * Retorna uma lista de {@link Portaria} conforme o filtro informado.
   *
   * @param filtroDto filtros informados para a consulta.
   * @return
   */
  public List<Portaria> pesquisarPortaria(FiltroDTO filtroDto) {

    try {

      StringBuilder jpql = new StringBuilder();
      Map<String, Object> parametros = new HashMap<String, Object>();

      jpql.append(" select p from Portaria where 1 = 1 ");

      setParametroAnoPortaria(filtroDto.getAnoPortaria(), jpql, parametros);
      setParametroCodigoDesignado(filtroDto.getCodigoDesignado(), jpql, parametros);
      setParametroCodigoUnidadeAdm(filtroDto.getCodigoUnidadeAdm(), jpql, parametros);
      setParametroFimVigencia(filtroDto.getFimVigencia(), jpql, parametros);
      setParametroFimVigencia(filtroDto.getInicioVigencia(), jpql, parametros);

      return this.pesquisarJPQLCustomizada(jpql.toString(), parametros);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private void setParametroFimVigencia(Date fimVigencia, StringBuilder jpql,
      Map<String, Object> parametros) {

  }

  private void setParametroCodigoUnidadeAdm(Long codigoUnidadeAdm, StringBuilder jpql,
      Map<String, Object> parametros) {

  }

  private void setParametroCodigoDesignado(Long codigoDesignado, StringBuilder jpql,
      Map<String, Object> parametros) {

  }

  private void setParametroAnoPortaria(Integer anoPortaria, StringBuilder jpql,
      Map<String, Object> parametros) {

  }

}
