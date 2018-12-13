package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.modelo.Lotacao;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;


/**
 * Classe de teste do {@link LotacaoDAO}
 *
 * @author Iago Bruno
 * @since 1.0
 */
public class LotacaoDAOTest {

  @Test
  public void salvar() {
    Lotacao lotacao = new Lotacao();
    LotacaoDAO lotacaoDAO = new LotacaoDAO();

    lotacao.setDtIniLotServ(new Date());
    lotacao.setDtFimLotServ(new Date());
    lotacao.setDescrCargoServ("Teste");

    lotacaoDAO.abrirTransacao();
    Lotacao lotacaoSalva = new LotacaoDAO().salvar(lotacao);
    lotacaoDAO.commitarTransacao();

    List<Lotacao> lotacaos = new LotacaoDAO().buscarTodos();

    System.out.println(lotacaos.size());

    Assert.assertNotNull(lotacaos);
  }

}
