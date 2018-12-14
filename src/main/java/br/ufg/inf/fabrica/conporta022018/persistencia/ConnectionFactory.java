package br.ufg.inf.fabrica.conporta022018.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe utilitária que retorna uma instâcia do {@link EntityManager} e fecha sua conexão.
 *
 * @author Iago Bruno
 * @since 1.0
 */
public class ConnectionFactory {

  private static final String UNIT_NAME = "COMPORTA";
  private static final EntityManagerFactory emf;
  private static final ThreadLocal<EntityManager> threadLocal;

  static {
    emf = Persistence.createEntityManagerFactory(UNIT_NAME);
    threadLocal = new ThreadLocal<EntityManager>();
  }

  /**
   * Classe utilitária, método construtor não deve ser invocado.
   */
  private ConnectionFactory() {
    throw new java.lang.UnsupportedOperationException(
            "Essa é uma classe utilitária e não pode ser inicializada!");
  }

  /**
   * Retorna uma instância do objeto {@link EntityManager}, gerenciado por uma {@link ThreadLocal}.
   *
   * @return instância do {@link EntityManager}
   */
  public static EntityManager obterManager() {
    EntityManager em = threadLocal.get();

    if (em == null) {
      em = emf.createEntityManager();
      threadLocal.set(em);
    }

    return em;
  }

  /**
   * Fecha a instância do {@link EntityManager} que está na {@link ThreadLocal}.
   */
  public static void closeEntityManager() {
    EntityManager em = threadLocal.get();

    if (em != null) {
      em.close();
      threadLocal.set(null);
    }
  }

  /**
   * Fecha a instância do {@link EntityManagerFactory} da classe {@link ConnectionFactory}.
   */
  public static void closeEntityManagerFactory() {
    emf.close();
  }


}