package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class PortariaDAO extends GenericoDAO<Portaria> {

//    public List<Portaria> pesquisaExpedidorSemCiencia(List<Long> idDesignado){
//
//        String buscarIdPortaria = "SELECT i FROM Portaria_Designados i WHERE i.Designado_id =:identificado";
//        Map<String, Object> parametros = new HashMap<>();
//        List<Portaria> portarias;
//        List<Long> idPortarias = new ArrayList<>();
//
//        for (Long id : idDesignado) {
//            parametros.put("identificado", id);
//            idPortarias = 12;
//
//            for (Long idPort : idPortarias) {
//                parametros.put("identificado", idPort);
//
//
//            }
//
//        }
//        return this.pesquisarJPQLCustomizada(builder.toString(),parametros);
//
//    }
//
//    private Class<Portaria> classType = ((Class<Portaria>) ((ParameterizedType) getClass()
//            .getGenericSuperclass()).getActualTypeArguments()[0]);
//
//
//    @Override
//    public List<Portaria> pesquisarJPQLCustomizada(String jpql, Map<String, Object> parametros) {
//        try {
//            return criarQuery(jpql, parametros).getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private TypedQuery<Portaria> criarQuery(String jpql, Map<String, Object> parametros) {
//        EntityManager entityManager = ConnectionFactory.obterManager();
//
//        TypedQuery<Portaria> query = entityManager.createQuery(jpql, classType);
//
//        if (parametros != null) {
//            for (Map.Entry<String, Object> parametro : parametros.entrySet()) {
//                query.setParameter(parametro.getKey(), parametro.getValue());
//            }
//        }
//        return query;
//    }

}