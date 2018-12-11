package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortariaDAO extends GenericoDAO<Portaria> {

    public List<Portaria> pesquisaExpedidorSemCiencia(Date dataLimite){

        StringBuilder builder = new StringBuilder();
        /*
        * Ainda para fazer a query de pesquisa do banco de dados
         */
        builder.append("select p from Portaria d where p.dtCienciaDesig is null ");
        builder.append(" and d.portaria.dtExped < :dtExped ");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("dtExped", dataLimite );

        return this.pesquisarJPQLCustomizada(builder.toString(),parametros);

    }

}