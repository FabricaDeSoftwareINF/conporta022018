package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDao;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ControladorEncPortTest {

  private static ControladorEncPort controladorEncPort;


  @BeforeClass
  public static void casoTestPepararCenario() throws IOException {

    String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/EncPortDadosTest.csv";
    String REGRA = ";";
    List<String> dadosSoftware = new ArrayList<>();
    Extrator extrator = new ExtratorCSV();
    LerArquivo lerArquivo = new LerArquivo();
    String tabelaAtual = " ";
    String dados[];
    String linha;

    dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

    PessoaDAO pessoaDAO = new PessoaDAO();
    PortariaDao portariaDao = new PortariaDao();
    DesignadoDAO designadoDAO = new DesignadoDAO();
    UndAdmDAO undAdmDAO = new UndAdmDAO();

    for (int index = 0; index < dadosSoftware.size(); index++) {
      linha = dadosSoftware.get(index);

      if (linha.equals("pessoa") || linha.equals("portaria") || linha.equals("undAdm") || linha
          .equals("designado")) {
        tabelaAtual = linha;
        index++;
        continue;
      }

      switch (tabelaAtual) {
        case "pessoa":

          extrator.setTexto(linha);
          dados = extrator.getResultado(REGRA);

          Pessoa pessoa = new Pessoa();

          pessoa.setId(Long.parseLong(dados[0]));
          pessoa.setNomePes(dados[1]);
          pessoa.setCpfPes(dados[2]);
          pessoa.setEmailPes(dados[3]);
          pessoa.setSenhaUsu(dados[4]);
          pessoa.setEhUsuAtivo(Boolean.getBoolean(dados[5]));

          pessoaDAO.salvar(pessoa);

          break;
        case "portaria":
          extrator.setTexto(linha);
          dados = extrator.getResultado(REGRA);



          break;
        case "undAdm":
          extrator.setTexto(linha);
          dados = extrator.getResultado(REGRA);

          UndAdm undAdm = new UndAdm();

          undAdm.setId(Long.parseLong(dados[0]));
          undAdm.setNomeUnd(dados[1]);
          undAdm.setSiglaUnAdm(dados[2]);
          undAdm.setTipoUnd(Integer.valueOf(dados[3]));
          undAdm.setMinInat(Integer.valueOf(dados[4]));
          undAdm.setUltPort(dados[5]);
          undAdm.setAnoPort(Integer.valueOf(dados[6]));
          undAdm.setUltNumExped(Integer.valueOf(dados[7]));
          undAdm.setUltNumProp(Integer.valueOf(dados[8]));

          break;
        case "designado":
          extrator.setTexto(linha);
          dados = extrator.getResultado(REGRA);
          break;
      }
    }
  }

  @Before
  public void casoTestPrepararExecucao() {

    controladorEncPort = new ControladorEncPort();
  }

  /*
   * Criar os cenários de testes para a aplicação:
   * Os cenarios de testes devem obrigatóriamente ser divididos em dois grupos.
   * DadosValidos : Grupo destinado ao cenatio típico e aos cenarios alternativos do caso de uso.
   * DadosExcecoes : Grupo destinado as exceções do cenario típico e dos cenarios alternativos.
   * Cada cenário e cada exceção deve necessáriamente ser testado no minimo uma vez, cada entrada e/ou combinação
   * de entrada deve ser testadas pelo menos os seus limites quando houver para o G1 e para o G2.
   */

  @Test
  public void casoTestDadosValidos() throws IOException {

    controladorEncPort.encPortariaCiencia("INF201802");

    controladorEncPort.encPortariaCiencia("INF201802");

  }

  @Test
  public void casoTestDadosExcecoes() throws IOException {

    controladorEncPort.encPortariaCiencia("INF201803");

  }

  @AfterClass
  public static void casoTestResultados() throws IOException {

    List emails = new ArrayList();
    emails.add("keslleyls@exemplo.com");

    // Assert.assertEquals(emails, rodaSQLparaPegarOsEnderecosDeEmailQueDeveriamReceberEmail);
  }

}
