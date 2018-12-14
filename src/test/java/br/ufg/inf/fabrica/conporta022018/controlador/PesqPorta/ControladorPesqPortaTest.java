/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.PesqPorta;

import static org.junit.Assert.assertTrue;

import br.ufg.inf.fabrica.conporta022018.controlador.PortariaControlador;
import br.ufg.inf.fabrica.conporta022018.dto.FiltroDTO;
import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ControladorPesqPortaTest {

  private static PortariaControlador controladorPesqPorta;

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

    Map<Long, List<Designado>> mapDesignados = new HashMap<Long, List<Designado>>();

    UndAdm unidadeExp = null;

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
          portaria.setDesignados(mapDesignados.get(Long.parseLong(dados[0])));
          portaria.setUnidadeExpedidora(unidadeExp);

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
          unidadeExp = undAdmDAO.salvar(undAdm);
          undAdmDAO.commitarTransacao();

          break;
        case "designado":

          extrator.setTexto(linha);
          dados = extrator.getResultado(REGRA);

          Designado designado = new Designado();

          Map<String, Object> parametro = new HashMap<String, Object>();

          parametro.put("CpfPes", dados[7]);

          Pessoa pessoaSalva = pessoaDAO.pesquisarUmJPQLCustomizada(
              "select p from Pessoa p where p.cpfPes = :CpfPes", parametro);

          designado.setDesignado(pessoaSalva);

          if (!mapDesignados.containsKey(Long.parseLong(dados[6]))) {
            mapDesignados.put(Long.parseLong(dados[6]), new ArrayList<Designado>());
          }

          mapDesignados.get(Long.parseLong(dados[6])).add(designado);

          break;
      }
    }
  }

  @Before
  public void casoTestPrepararExecucao() {

    //Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.
    controladorPesqPorta = new PortariaControlador();
  }

  /**
   * Casos de teste válido para filtro de ano
   */
  @Test
  public void casoTestDadosValidos() throws IOException {

    //Grupo de teste DadosValidos, exemplo:
    FiltroDTO filtroAno = new FiltroDTO(null, null, 2018, null, null);
    List<Portaria> portarias = controladorPesqPorta.pesquisa(filtroAno);

    //Teste para consuta por ano
    Assert.assertNotEquals(portarias.size(), 0);

  }

  /**
   * Casos de teste válido para filtro de CPF
   */
  @Test
  public void casoTesteFiltroCPF() {
    FiltroDTO filtroCPF = new FiltroDTO("784.456.818-12", null, null, null, null);

    List<Portaria> portarias = controladorPesqPorta.pesquisa(filtroCPF);

    //Teste para consuta por CPF
    Assert.assertNotEquals(portarias.size(), 0);
  }

  /**
   * Casos de teste válido para filtro de Sigla de unidade Adm.
   */
  @Test
  public void casoTesteFiltroSiglaUnidade() {
    FiltroDTO filtroSigla = new FiltroDTO(null, "INF", null, null, null);

    List<Portaria> portarias = controladorPesqPorta.pesquisa(filtroSigla);

    //Teste para consuta por Sigla Unidade
    Assert.assertNotEquals(portarias.size(), 0);
  }

  /**
   * Casos de teste válido para filtro de Datas.
   */
  @Test
  public void casoTesteFiltroDatas() throws ParseException {

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    FiltroDTO filtro = new FiltroDTO(null, null, null, formato.parse("01/01/2019"),
        formato.parse("01/05/2020"));

    List<Portaria> portarias = controladorPesqPorta.pesquisa(filtro);

    //Teste para consuta por Sigla Unidade
    Assert.assertNotEquals(portarias.size(), 0);
  }

  /**
   * Casos de teste válido para filtro sem retorno
   */
  @Test
  public void casoTestDadosValidosSemResultado() throws IOException {

    //Grupo de teste DadosValidos, exemplo:
    FiltroDTO filtroAno = new FiltroDTO(null, null, 2030, null, null);
    List<Portaria> portarias = controladorPesqPorta.pesquisa(filtroAno);

    //Teste para consuta por ano
    Assert.assertEquals(portarias.size(), 0);

  }

  /**
   * Caso de exceção, onde datas incoerentes são informadas.
   */
  @Test
  public void casoTestDadosExcecoes() throws ParseException {

    try {
      SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

      FiltroDTO filtro = new FiltroDTO(null, null, null, formato.parse("02/01/2018"),
          formato.parse("01/01/2018"));

      controladorPesqPorta.pesquisa(filtro);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().equals("A data fim não deve ser menor que a de início"));
    }

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
