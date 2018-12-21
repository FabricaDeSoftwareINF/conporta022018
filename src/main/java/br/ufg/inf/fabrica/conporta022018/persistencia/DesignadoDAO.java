package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;

import java.util.*;

public class DesignadoDAO extends GenericoDAO<Designado>{

    public List<Designado> pesquisaDesignadosSemCiencia(Date dataLimite){


        StringBuilder builder = new StringBuilder();
        builder.append("select d from Designado d where d.dtCienciaDesig is null ");
        builder.append(" and d.portaria.dtExped < :dtExped ");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("dtExped", dataLimite );

       return this.pesquisarJPQLCustomizada(builder.toString(),parametros);

    }

}