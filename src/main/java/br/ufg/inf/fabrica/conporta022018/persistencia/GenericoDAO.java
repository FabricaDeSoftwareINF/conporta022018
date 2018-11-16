package br.ufg.inf.fabrica.conporta022018.persistencia;

import javax.persistence.EntityManager;

/**
 * Classe abstrata de persistência.
 *
 * @param <T> modelo que será persistido e cosnultado
 */
public abstract class GenericoDAO<T> {

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


}
