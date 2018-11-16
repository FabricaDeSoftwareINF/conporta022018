package br.ufg.inf.fabrica.conporta022018.persistencia;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Classe abstrata de persistência.
 *
 * @param <T> modelo que será persistido e cosnultado
 */
public abstract class GenericoDAO<T> {

  private Class<T> classType = ((Class<T>) ((ParameterizedType) getClass()
      .getGenericSuperclass()).getActualTypeArguments()[0]);

  /**
   * Salva ou altera um modelo no banco de dados.
   *
   * @param modelo que será persistido ou alterado
   * @return um instância do modelo gerenciado pelo {@link EntityManager}, caso ocorra algum erro,
   * retornará null.
   */
  public T salvar(T modelo) {
    EntityManager entityManager = ConnectionFactory.getEntityManager();

    try {
      return entityManager.merge(modelo);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  public void remover(T modelo) {
    EntityManager entityManager = ConnectionFactory.getEntityManager();

    try {
      entityManager.remove(modelo);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public T buscar(Long id) {
    EntityManager entityManager = ConnectionFactory.getEntityManager();

    try {
      return entityManager.find(classType, id);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<T> buscarTodos() {
    EntityManager entityManager = ConnectionFactory.getEntityManager();

    try {
      return entityManager.createQuery("select c from " + classType.getName() + " c ",
          classType).getResultList();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public T pesquisarUmJPQLCustomizada(String jpql, Map<String, Object> parametros) {

    try {
      return criarQuery(jpql, parametros).getSingleResult();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<T> pesquisarJPQLCustomizada(String jpql, Map<String, Object> parametros) {
    try {
      return criarQuery(jpql, parametros).getResultList();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void abrirTransacao() {
    try {
      ConnectionFactory.getEntityManager().getTransaction().begin();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void commitarTransacao() {
    try {
      ConnectionFactory.getEntityManager().getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void rollBackTransacao() {
    try {
      ConnectionFactory.getEntityManager().getTransaction().rollback();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private TypedQuery<T> criarQuery(String jpql, Map<String, Object> parametros) {
    EntityManager entityManager = ConnectionFactory.getEntityManager();

    TypedQuery<T> query = entityManager.createQuery(jpql, classType);

    for (Entry<String, Object> parametro : parametros.entrySet()) {
      query.setParameter(parametro.getKey(), parametro.getValue());
    }
    return query;
  }


}
