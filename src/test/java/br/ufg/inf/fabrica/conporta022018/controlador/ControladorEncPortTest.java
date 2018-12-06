package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ControladorEncPortTest {

  private static ControladorEncPort controladorEncPort;


  @BeforeClass
  public static void casoTestPepararCenario() throws IOException, ParseException {

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
    PortariaDAO portariaDao = new PortariaDAO();
    DesignadoDAO designadoDAO = new DesignadoDAO();
    UndAdmDAO undAdmDAO = new UndAdmDAO();

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

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


          break;
        case "portaria":
          extrator.setTexto(linha);
          dados = extrator.getResultado(REGRA);




          break;
        case "undAdm":
          extrator.setTexto(linha);
          dados = extrator.getResultado(REGRA);



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
