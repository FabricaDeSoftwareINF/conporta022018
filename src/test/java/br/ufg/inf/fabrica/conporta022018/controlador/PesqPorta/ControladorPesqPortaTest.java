/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.PesqPorta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorPesqPorta;
import br.ufg.inf.fabrica.conporta022018.dto.FiltroDTO;
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
import java.util.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ControladorPesqPortaTest {

  private static ControladorPesqPorta controladorPesqPorta;

  /*
   * Preparação do ambiente para teste.
   * População do banco de Dados para atendam os pré-requisitos do caso de uso.
   */

  @BeforeClass
  public static void casoTestPepararCenario() throws IOException, ParseException {

    String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/pesqPorta/PesqPortaDadosTest.csv";
    String REGRA = ";";
    List<String> dadosSoftware = new ArrayList<>();
    Extrator extrator = new ExtratorCSV();
    LerArquivo lerArquivo = new LerArquivo();
    String tabelaAtual = " ";
    String dados[];
    String linha;
    //Criar as instâncias de todos os objetos DAO's necessários para preparar o cenario.

    PessoaDAO pessoaDAO = new PessoaDAO();
    PortariaDAO portariaDao = new PortariaDAO();
    UndAdmDAO undAdmDAO = new UndAdmDAO();

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);



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

          pessoaDAO.abrirTransacao();
          pessoaDAO.salvar(pessoa);
          pessoaDAO.commitarTransacao();

          break;
        case "portaria":
          extrator.setTexto(linha);
          dados = extrator.getResultado(REGRA);

          Portaria portaria = new Portaria();

          portaria.setId(Long.parseLong(dados[0]));
          portaria.setAnoId(Integer.valueOf(dados[1]));
          portaria.setSeqId(Integer.valueOf(dados[2]));
          portaria.setStatus(PortariaStatus.valueOf(dados[3]));
          portaria.setAssunto(dados[4]);
          portaria.setDtExped(formato.parse(dados[5]));
          portaria.setDtIniVig(formato.parse(dados[7]));
          portaria.setDtFimVig(formato.parse(dados[8]));

          portariaDao.abrirTransacao();
          portariaDao.salvar(portaria);
          portariaDao.commitarTransacao();


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

          undAdmDAO.abrirTransacao();
          undAdmDAO.salvar(undAdm);
          undAdmDAO.commitarTransacao();

          break;
        case "designado":

          extrator.setTexto(linha);
          dados = extrator.getResultado(REGRA);

//          List<Portaria> portarias = portariaDao.buscarTodos();
//
//          Portaria portariaSalva = portariaDao.buscar(Long.parseLong(dados[6]));
//          Pessoa pessoaSalva = pessoaDAO.buscar(Long.parseLong(dados[7]));
//
//          Designado designado = new Designado();
//
//          designado.setDesignado(pessoaSalva);
//
//          portariaSalva.setDesignados(new ArrayList<Designado>());
//
//          portariaSalva.getDesignados().add(designado);
//
//
//          portariaDao.abrirTransacao();
//          portariaDao.salvar(portariaSalva);
//          portariaDao.commitarTransacao();




          break;
      }
    }
  }

  @Before
  public void casoTestPrepararExecucao() {

    //Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.
    controladorPesqPorta = new ControladorPesqPorta();
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

    //Grupo de teste DadosValidos, exemplo:
    FiltroDTO filtro = new FiltroDTO(null, null, 2018, null, null);
    List<Portaria> portarias = controladorPesqPorta.pesqPorta(filtro);
    Assert.assertNotEquals(portarias.size(), 0);

  }

  @Test
  public void casoTestDadosExcecoes() throws IOException {

    //Grupo de teste DadosExcecoes, exemplo:
    // controladorPesqPorta.pesqPorta("123.456.789-12", "FACE", 2018, 0001);
    //O cenario acima testa a primeira exceção do caso de uso a unidade acadêmica não é localizada.
  }

  @AfterClass
  public static void casoTestResultados() throws IOException {

    //Aqui deve ser verificado os resultados da exceção do Grupo G1 e G2, normalmente aqui
    // irá fica as suas pós-condições. Exemplo:

    //Dados do caso de teste de sucesso para a data atual.

    //Busca a data atual.
    Date hoje = new Date();
    SimpleDateFormat df;
    df = new SimpleDateFormat("dd/MM/yyyy");
    String dataHoje = df.format(hoje);

    //pega a data que foi armazenada no banco de dados e verifica com a data de execução do teste, ou seja,
    // a data de hoje.

    //Verifica o caso de teste que retorna resultados
    //Assert.assertEquals(dataHoje, rodaSQLparaPegarADataGravadaNoBancoDeDados);
  }

}
