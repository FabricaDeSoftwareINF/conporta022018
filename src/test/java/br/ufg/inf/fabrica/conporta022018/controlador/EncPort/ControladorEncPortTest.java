/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.EncPort;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorEncPort;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(JUnit4.class)
public class ControladorEncPortTest {

    private static ControladorEncPort controladorEncPort;
    private static List<Designado> designados = new ArrayList();
    private static List<Recebedora>  recebedoras = new ArrayList();
    private static List<Portaria> portarias = new ArrayList();
    private static List<Gestao> gestaos = new ArrayList();
    private static List<Mensagem> mensagens = new ArrayList();

    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos da seção de caso de uso.
     */

    @BeforeClass
    public static void casoTestPepararCenario() throws IOException, ParseException {
        String CAMINHO_CSV = "EncPortDadosTest.csv";
        String REGRA = ",";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;

        // Criar as instâncias de todos os objetos DAO's necessários para preparar o cenario.

        PessoaDAO pessoaDAO = new PessoaDAO();
        PortariaDAO portariaDAO = new PortariaDAO();
        DesignadoDAO designadoDAO = new DesignadoDAO();
        RecebedoraDAO recebedoraDAO = new RecebedoraDAO();
        GestaoDAO gestaoDAO = new GestaoDAO();
        MensagemDAO mensagemDAO = new MensagemDAO();
        UndAdmDAO undAdmDAO = new UndAdmDAO();

        // Objetos que são usados por externos

        Pessoa pessoa = null;
        UndAdm undAdm = null;
        UndAdm unidadeExp = null;

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {
            linha = dadosSoftware.get(index);
            if (linha.equals("pessoa")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            if (linha.equals("portaria")) {
                tabelaAtual = linha;
                index++;
                continue;
            }


            if (linha.equals("undAdm")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            if (linha.equals("designado")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            if (linha.equals("gestao")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            if (linha.equals("mensagem")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            if (linha.equals("recebedora")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

             switch (tabelaAtual) {
                case "pessoa":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    //Comandos para popular a tabela pessoa no Banco de Dados.

                    pessoa = new Pessoa();
                    pessoa.setNomePes(dados[0]);
                    System.out.println("==== DADO 1" +dados[1]);
                    System.out.println("======== TABELA ATUAL:" + tabelaAtual);
                    pessoa.setCpfPes(dados[1]);
                    pessoa.setEmailPes(dados[2]);
                    pessoa.setSenhaUsu(dados[3]);
                    pessoa.setEhUsuAtivo(Boolean.parseBoolean(dados[4]));

                    pessoaDAO.abrirTransacao();

                    try {
                        pessoa = pessoaDAO.salvar(pessoa);
                        pessoaDAO.commitarTransacao();
                    } catch (Exception ex) {
                        pessoaDAO.rollBackTransacao();
                    }

                    break;
                case "portaria":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    Portaria portaria = new Portaria();
                    portaria.setSiglaUndId(dados[0]);
                    portaria.setAnoId(Integer.parseInt(dados[1]));
                    portaria.setSeqId(Integer.parseInt(dados[2])); //Essencial para o caso de uso pois é usado no JavaMail
                    portaria.setStatus(PortariaStatus.valueOf(dados[3])); ////Essencial para o caso de uso ter o status da portaria
                    portaria.setAssunto(dados[4]);
                    portaria.setUndRecebedora(recebedoras); //Essencial para o caso de uso ter as UndRecebedora da portaria
                    portaria.setExpedidor(pessoa);
                    portaria.setDesignados(designados); //Essencial para o caso de uso ter os designados da portaria
                    portaria.setUnidadeExpedidora(unidadeExp);
                    System.out.println("antes de abrir");
                    portariaDAO.abrirTransacao();
                    System.out.println("teste");
                    try {
                        Portaria portariaDoBanco = portariaDAO.salvar(portaria); //fazer isso para undAdm que ta null
                        System.out.println(portariaDoBanco.getStatus());
                        portarias.add(portariaDoBanco);
                        portariaDAO.commitarTransacao();
                    } catch (Exception ex) {
                        portariaDAO.rollBackTransacao();
                    }

                    break;
                case "designado":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    Designado designado = new Designado();
                    designado.setDescrFuncDesig(dados[2]);
                    designado.setDesignado(pessoa); //Essencial para o caso de uso ter os designados da portaria

                    designadoDAO.abrirTransacao();

                    try {
                        Designado designaDoBanco = designadoDAO.salvar(designado);
                        designados.add(designaDoBanco);
                        designadoDAO.commitarTransacao();
                    } catch (Exception ex) {
                        designadoDAO.rollBackTransacao();
                    }

                    break;

                case "undAdm":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    undAdm = new UndAdm();
                    undAdm.setNomeUnd(dados[0]);
                    undAdm.setSiglaUnAdm(dados[1]);
                    undAdm.setTipoUnd(Integer.parseInt(dados[2]));
                    undAdm.setMinInat(Integer.parseInt(dados[3]));
                    undAdm.setUltPort(dados[4]);
                    undAdm.setAnoPort(Integer.parseInt(dados[5]));
                    undAdm.setUltNumExped(Integer.parseInt(dados[6]));
                    undAdm.setUltNumProp(Integer.parseInt(dados[7]));

                    undAdmDAO.abrirTransacao();

                    try {
                        unidadeExp = undAdmDAO.salvar(undAdm);
                        undAdmDAO.commitarTransacao();
                    } catch (Exception ex) {
                        undAdmDAO.rollBackTransacao();
                    }

                    break;

                case "recebedora":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    String dataStringReeb = dados[0];
                    SimpleDateFormat formataDataReeb = new SimpleDateFormat(dataStringReeb);
                    Date dateFormat = formataDataReeb.parse(dataStringReeb);

                    Recebedora recebedora = new Recebedora();
                    recebedora.setDtCienciaReeb(dateFormat);
                    recebedora.setUnidadeRecebedora(undAdm); //Essencial para o caso de uso ter as UndRecebedora da portaria
                    recebedoraDAO.abrirTransacao();

                    try {

                        recebedora = recebedoraDAO.salvar(recebedora);
                        recebedoras.add(recebedora);
                        recebedoraDAO.commitarTransacao();
                    } catch (Exception ex) {
                        recebedoraDAO.rollBackTransacao();
                    }

                    break;

                case "gestao":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    String dataString = dados[0];
                    SimpleDateFormat formataData= new SimpleDateFormat(dataString);
                    Date date1 = formataData.parse(dataString);

                    Gestao gestao = new Gestao();
                    gestao.setdtFim(date1);

                    gestaoDAO.abrirTransacao();

                    try {
                        Gestao gestaoDoBanco = gestaoDAO.salvar(gestao);
                        gestaos.add(gestaoDoBanco);
                        gestaoDAO.commitarTransacao();
                    } catch (Exception ex) {
                        gestaoDAO.rollBackTransacao();
                    }

                    break;

                case "mensagem":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    Mensagem mensagem = new Mensagem();
                    mensagem.setTitulo(dados[0]);
                    mensagem.setDescricao(dados[1]);

                    mensagemDAO.abrirTransacao();

                    try {
                        Mensagem mensagemDoBanco = mensagemDAO.salvar(mensagem);
                        mensagens.add(mensagemDoBanco);
                        mensagemDAO.commitarTransacao();
                    } catch (Exception ex) {
                        mensagemDAO.rollBackTransacao();
                    }

            }
        }
    }

    @Before
    public void casoTestPrepararExecucao() {

        // Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.

        controladorEncPort = new ControladorEncPort();
    }

    /*
     * Criar os cenários de testes para a aplicação:
     * Os cenarios de testes devem obrigatóriamente ser divididos em dois grupos.
     * DadosValidos : Grupo destinado ao cenatio típico e aos cenarios alternativos da seção de caso de uso.
     * DadosExcecoes : Grupo destinado as exceções do cenario típico e dos cenarios alternativos.
     * Cada cenário e cada exceção deve necessáriamente ser testado no minimo uma vez, cada entrada e/ou combinação
     * de entrada deve ser testadas pelo menos os seus limites quando houver para o G1 e para o G2.
     */

    @Test
    public void casoTestDadosValidos() throws IOException {

        // Grupo de teste DadosValidos

        Long idPortariaOp1 = null;
        Long idPortariaOp2 = null;
        Long idPortariaOp3 = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF201810")) {
                idPortariaOp1 = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF201815")) {
                idPortariaOp2 = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF20184")) {
                idPortariaOp3 = portaria.getId();
            }
        }

        boolean op1 = controladorEncPort.portariaIsValida(idPortariaOp1);
        Assert.assertEquals(true, op1);

        boolean op2 = controladorEncPort.portariaIsValida(idPortariaOp2);
        Assert.assertEquals(true, op2);

        boolean op3 = controladorEncPort.portariaIsValida(idPortariaOp3);
        Assert.assertEquals(true, op3);

    }

    @Test(expected = UnsupportedOperationException.class)
    public void casoTestDadosExcecoes() throws Exception {

        // Grupo de teste DadosExceceos

        Long idPortariaOp1 = null;
        Long idPortariaOp2 = null;
        Long idPortariaOp3 = null;
        Long idPortariaOp4 = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF201812")) {
                idPortariaOp1 = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF201813")) {
                idPortariaOp2 = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF201814")) {
                idPortariaOp3 = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF2018144")) {
                idPortariaOp4 = portaria.getId();
            }
        }

        boolean op1 = controladorEncPort.portariaIsValida(idPortariaOp1);

        boolean op2 = controladorEncPort.portariaIsValida(idPortariaOp2);

        boolean op3 = controladorEncPort.portariaIsValida(idPortariaOp3);

        boolean op4 = controladorEncPort.encPortariaCiencia(portarias.get(0));
    }

    @AfterClass
    public  static void casoTestResultados() throws IOException{
        PortariaDAO portariaDAO = new PortariaDAO();

        // Aqui deve ser verificado os resultados da exceção do Grupo G1 e G2, normalmente aqui
        // irá fica as suas pós-condições.


        Long idPortariaEncCienciaOp1 = null;

        for (Portaria portaria: portarias) {
            String idLogicoPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoPortaria.equals("INF201815")){
                idPortariaEncCienciaOp1 = portaria.getId();
            }
        }

        Assert.assertEquals(PortariaStatus.ATIVA, portariaDAO.buscar(idPortariaEncCienciaOp1).getStatus());
    }

    /**
     * Método auxiliar utilizado para obter o id lógico a partir de uma instância de Portaria.
     * @param portaria
     * @return
     */
    private static String getIdLogicoDaProtaria(Portaria portaria) {
        return portaria.getSiglaUndId() + portaria.getAnoId() + portaria.getSeqId();
    }

}

