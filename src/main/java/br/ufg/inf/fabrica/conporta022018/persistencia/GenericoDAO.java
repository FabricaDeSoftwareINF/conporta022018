package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.modelo.ModeloAbstrato;
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
public abstract class GenericoDAO<T extends ModeloAbstrato> {

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
    EntityManager entityManager = ConnectionFactory.obterManager();

    try {
      this.abrirTransacao();

      T retorno = entityManager.merge(modelo);

      this.commitarTransacao();

      return retorno;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  /**
   * Remove um registro do banco com base na entidade informada.
   *
   * @param modelo instâcnia da entidade
   */
  public void remover(T modelo) {
    EntityManager entityManager = ConnectionFactory.obterManager();

    try {
      entityManager.remove(modelo);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Consulta pela chave primária, pesquisando por uma entidade específica <T>.
   *
   * @param id chave da entidade
   * @return instância da entidade
   */
  public T buscar(Long id) {
    EntityManager entityManager = ConnectionFactory.obterManager();

    try {
      return entityManager.find(classType, id);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retorna uma lista de instância de uma entidade específica <T>.
   *
   * @return lista de instâncias de T
   */
  public List<T> buscarTodos() {
    EntityManager entityManager = ConnectionFactory.obterManager();

    try {
      return entityManager.createQuery("select c from " + classType.getName() + " c ",
          classType).getResultList();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Consulta através de um script de pesquisa e de seus parâmetros informados, retorna apenas uma instância de  T.
   *
   * @param jpql String de consulta no formato JPQL, ver <a href="https://docs.oracle.com/html/E13946_01/ejb3_langref.html"></a>.
   * @param parametros {@link Map} de parâmetros da consulta.
   * @return instância de T caso pesquisa traga resultados, caso contrário, null.
   */
  public T pesquisarUmJPQLCustomizada(String jpql, Map<String, Object> parametros) {

    try {
      return criarQuery(jpql, parametros).getSingleResult();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Consulta através de um script de pesquisa e de seus parâmetros informados, retorna apenas uma lista de T.
   *
   * @param jpql String de consulta no formato JPQL, ver <a href="https://docs.oracle.com/html/E13946_01/ejb3_langref.html"></a>.
   * @param parametros {@link Map} de parâmetros da consulta.
   * @return lista de instâncias de T caso pesquisa traga resultados, caso contrário, null.
   */
  public List<T> pesquisarJPQLCustomizada(String jpql, Map<String, Object> parametros) {
    try {
      return criarQuery(jpql, parametros).getResultList();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Inicia uma transação no banco gerenciado pelo {@link EntityManager}
   */
  public void abrirTransacao() {
    try {
      ConnectionFactory.obterManager().getTransaction().begin();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Realiza o Commit em uma transação no banco gerenciado pelo {@link EntityManager}
   */
  public void commitarTransacao() {
    try {
      ConnectionFactory.obterManager().getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Realiza o Rollback em uma transação no banco gerenciado pelo {@link EntityManager}
   */
  public void rollBackTransacao() {
    try {
      ConnectionFactory.obterManager().getTransaction().rollback();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Métoo privado que instância um objeto {@link TypedQuery} com seus parâmetros
   *
   * @param jpql String de consulta no formato JPQL, ver <a href="https://docs.oracle.com/html/E13946_01/ejb3_langref.html"></a>.
   * @param parametros  {@link Map} de parâmetros da consulta.
   * @return
   */
  private TypedQuery<T> criarQuery(String jpql, Map<String, Object> parametros) {
    EntityManager entityManager = ConnectionFactory.obterManager();

    TypedQuery<T> query = entityManager.createQuery(jpql, classType);

    if (parametros != null) {
      for (Entry<String, Object> parametro : parametros.entrySet()) {
        query.setParameter(parametro.getKey(), parametro.getValue());
      }
    }
    return query;
  }


}