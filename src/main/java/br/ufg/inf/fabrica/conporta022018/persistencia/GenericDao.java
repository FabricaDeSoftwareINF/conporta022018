package br.ufg.inf.fabrica.conporta022018.persistencia;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by Iago Bruno on 26/10/18.
 */
public abstract class GenericDao<T> {

    private Class<T> classType = ((Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0]);

    /**
     * Recebe entidade para persistir no banco de dados.
     *
     * @param entidade que será persistida.
     */
    public void salvar(T entidade) {

    }

    public T buscar(Long id) {
        return null;
    }

    /**
     *
     *
     * @param jpql
     * @param parametros
     * @return
     */
    public List<T> pesquisarJPQLCustomizada(String jpql, Map<String, Object> parametros) {
        return null;
    }

    public T pesquisarUmJPQLCustomizada(String jpql, Map<String, Object> parametros){
        return  null;
    }

}
