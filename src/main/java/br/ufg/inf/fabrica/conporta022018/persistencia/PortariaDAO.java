package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.dto.FiltroDTO;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class PortariaDAO extends GenericoDAO<Portaria> {

  /**
   * Retorna uma lista de {@link Portaria} conforme o filtro informado.
   *
   * @param filtroDto filtros informados para a consulta.
   */
  public List<Portaria> pesquisarPortaria(FiltroDTO filtroDto) {

    try {

      StringBuilder jpql = new StringBuilder();
      Map<String, Object> parametros = new HashMap<String, Object>();

      if (filtroDto.getCpfPes() != null) {
        jpql.append(" select p from Portaria p inner join p.designados d where 1 = 1 ");
      } else{
        jpql.append(" select p from Portaria p where 1 = 1 ");
      }


      setParametroAnoPortaria(filtroDto.getAnoPortaria(), jpql, parametros);
      setParametroCodigoDesignado(filtroDto.getCpfPes(), jpql, parametros);
      setParametroCodigoUnidadeAdm(filtroDto.getSiglaUnAdm(), jpql, parametros);

      setParametroFimVigencia(filtroDto.getInicioVigencia(), filtroDto.getFimVigencia(), jpql,
          parametros);

      return this.pesquisarJPQLCustomizada(jpql.toString(), parametros);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private void setParametroFimVigencia(Date inicioVigencia, Date fimVigencia, StringBuilder jpql,
      Map<String, Object> parametros) {

    if (inicioVigencia != null && fimVigencia != null) {
      jpql.append(" and ((p.dtIniVig between :inicioVigencia and :fimVigencia) ");
      jpql.append(" or (p.dtFimVig between :inicioVigencia and :fimVigencia)) ");

      parametros.put("inicioVigencia", inicioVigencia);
      parametros.put("fimVigencia", fimVigencia);
    }

  }

  private void setParametroCodigoUnidadeAdm(String siglaUnAdm, StringBuilder jpql,
      Map<String, Object> parametros) {

    if (siglaUnAdm != null) {
      jpql.append(" and (p.unidadeExpedidora.siglaUnAdm = :siglaUnAdm) ");
      parametros.put("siglaUnAdm", siglaUnAdm);
    }

  }

  private void setParametroCodigoDesignado(String cpfPes, StringBuilder jpql,
      Map<String, Object> parametros) {
    if (cpfPes != null) {
      jpql.append(" and (d.designado.cpfPes = :cpfPes) ");
      parametros.put("cpfPes", cpfPes);
    }
  }

  private void setParametroAnoPortaria(Integer anoPortaria, StringBuilder jpql,
      Map<String, Object> parametros) {
    if (anoPortaria != null) {
      jpql.append(" and (p.anoId = :anoPortaria) ");
      parametros.put("anoPortaria", anoPortaria);
    }
  }
}
