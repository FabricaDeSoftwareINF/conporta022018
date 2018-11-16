package br.ufg.inf.fabrica.conporta022018.persistencia;
import br.ufg.inf.fabrica.conporta022018.modelo.Recebedora;

public class RecebedoraDAO extends GenericoDAO{
	
	private Recebedora recebedora;
	private ConnectionFactory entityManager;

  /**
   * Mtodo utilizado para salvar ou atualizar as informações de uma recebedora.
   * @param recebedora
   * @return
   * @throws java.lang.Exception
   */
  public Recebedora salvar(Recebedora recebedora) throws Exception {
    return new Recebedora();
  }

  /**
   * Método que apaga a recebedora do banco de dados.
   * @param id
   */
  public void remover(Long id) {
  }

   /**
   * Método que busca recebedora do banco de dados.
   * @param id
   */
  public Recebedora buscar(Long id) {
    return new Recebedora();
  }

   /**
   * Método que abre uma transação com banco de dados.
   */
  public void abrirTransacao() {
  }

    /**
   * Método que commita uma transação com banco de dados.
   */
  public void commitarTransacao() {
  }

   /**
   * Método que faz rollback de uma transação com banco de dados.
   */
  public void rollBackTransacao() {
  }
}