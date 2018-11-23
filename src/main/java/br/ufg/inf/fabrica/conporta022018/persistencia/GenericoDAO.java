/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static javafx.scene.input.KeyCode.T;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Breno Gomes
 */
public abstract class GenericoDAO<T> {
    
    private Class<T> classType;   
    
    /**
     * Recebe entidade para persistir no banco de dados.
     *
     * @param entidade que será persistida.
     */
    public void salvar(T entidade) {
    }
  
    public List<T> pesquisarJPQLCustomizada(String jpql, Map<String, Object> parametros) {               
        return null;
    }
    public T pesquisarUmJPQLCustomizada(String jpql, Map<String, Object> parametros) {
        return null;
    }
    
}
