package br.ufg.inf.fabrica.conporta022018.persistencia;
import br.ufg.inf.fabrica.conporta022018.modelo.Recebedora;

public class RecebedoraDAO extends GenericoDAO<Recebedora>{
	
	private Recebedora recebedora;
	private ConnectionFactory entityManager;

	@Override
	public Recebedora salvar(Recebedora recebedora) {
	    return new Recebedora();
	}

    public void remover(Long id) {}

    public Recebedora buscar(Long id) {
      return new Recebedora();
    }

    public void abrirTransacao() {}

	public void commitarTransacao() {}

    public void rollBackTransacao() {}
    
}