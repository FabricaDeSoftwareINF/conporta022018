package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.modelo.Lotacao;
import java.util.Date;
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

    lotacao.setDtIniLotServ(new Date());
    lotacao.setDtFimLotServ(new Date());
    lotacao.setDescrCargoServ("Teste");

    Lotacao lotacaoSalva = new LotacaoDAO().salvar(lotacao);

    //Assert.checkNonNull(lotacaoSalva);
  }

}
