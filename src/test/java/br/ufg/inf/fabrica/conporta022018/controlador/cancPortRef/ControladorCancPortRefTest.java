/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.cancPortRef;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorCancPortRef;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControladorCancPortRefTest {
    private static ControladorCancPortRef controladorCancPortRef;
    private static List<Portaria> portarias = new ArrayList<>();

    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos da seção de caso de uso.
     */
    @BeforeClass
    public static void casoTestPepararCenario() throws IOException {
        PessoaDAO pessoaDAO = new PessoaDAO();
        UndAdmDAO undAdmDAO = new UndAdmDAO();
        PortariaDAO portariaDAO = new PortariaDAO();

        Pessoa pessoa = null;
        UndAdm undAdm = null;
        List<ReferenciaDoCSV> referenciasDoCSV = new ArrayList<>();

        String CAMINHO_CSV = "./src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/cancPortRef/CancPortRefDadosTest.csv";
        String REGRA = ",";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {
            linha = dadosSoftware.get(index);

            // Definir as tabelas que serão populadas no Banco de Dados.
            if (linha.equals("pessoa")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            if (linha.equals("undAdm")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            if (linha.equals("portaria")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            if (linha.equals("referencia")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "pessoa":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    pessoa = new Pessoa();
                    pessoa.setNomePes(dados[0]);
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
                        undAdm = undAdmDAO.salvar(undAdm);
                        undAdmDAO.commitarTransacao();
                    } catch (Exception ex) {
                        undAdmDAO.rollBackTransacao();
                    }

                    break;
                case "referencia":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    ReferenciaDoCSV referenciaDoCSV = new ReferenciaDoCSV();
                    referenciaDoCSV.setIdPortaria(dados[0]);
                    referenciaDoCSV.setIdPortariaReferenciada(dados[1]);
                    referenciaDoCSV.setEhCancelamento(Boolean.parseBoolean(dados[2]));

                    referenciasDoCSV.add(referenciaDoCSV);

                    break;
                case "portaria":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    Portaria portaria = new Portaria();
                    portaria.setSiglaUndId(dados[0]);
                    portaria.setAnoId(Integer.parseInt(dados[1]));
                    portaria.setSeqId(Integer.parseInt(dados[2]));
                    portaria.setStatus(PortariaStatus.valueOf(dados[3]));
                    portaria.setAssunto(dados[4]);
                    portaria.setExpedidor(pessoa);
                    portaria.setUnidadeExpedidora(undAdm);

                    List<Referencia> referencias = new ArrayList<>();

                    String idLogicoPortaria = getIdLogicoDaProtaria(portaria);

                    for (ReferenciaDoCSV referenciaCSV : referenciasDoCSV) {
                        if (referenciaCSV.getIdPortaria().equals(idLogicoPortaria)) {
                            String idLogicoPortariaReferenciada = referenciaCSV.getIdPortariaReferenciada();

                            for (Portaria portariaReferenciada : portarias) {
                                String idLogico = getIdLogicoDaProtaria(portariaReferenciada);

                                if (idLogico.equals(idLogicoPortariaReferenciada)) {
                                    Referencia referencia = new Referencia();
                                    referencia.setReferencia(portariaReferenciada);
                                    referencia.setEhCancelamento(referenciaCSV.getEhCancelamento());
                                    referencias.add(referencia);
                                }

                                // Identifica a portaria que não deve existir no banco.
                                if (idLogicoPortariaReferenciada.equals("INF20186")) {
                                    Portaria portariaNaoSalvaNoBanco = new Portaria();
                                    portariaNaoSalvaNoBanco.setSiglaUndId("INF");
                                    portariaNaoSalvaNoBanco.setAnoId(2018);
                                    portariaNaoSalvaNoBanco.setSeqId(6);
                                    portariaNaoSalvaNoBanco.setStatus(PortariaStatus.ATIVA);
                                    portariaNaoSalvaNoBanco.setAssunto("TESTE DE BANCO");
                                    portariaNaoSalvaNoBanco.setExpedidor(pessoa);
                                    portariaNaoSalvaNoBanco.setUnidadeExpedidora(undAdm);
                                    portariaNaoSalvaNoBanco.setReferencias(referencias);

                                    Referencia referencia = new Referencia();
                                    referencia.setReferencia(portariaNaoSalvaNoBanco);
                                    referencia.setEhCancelamento(referenciaCSV.getEhCancelamento());
                                    referencias.add(referencia);
                                }

                            }
                        }
                    }

                    portaria.setReferencias(referencias);

                    portariaDAO.abrirTransacao();

                    try {
                        Portaria portariaDoBanco = portariaDAO.salvar(portaria);
                        portarias.add(portariaDoBanco);
                        portariaDAO.commitarTransacao();
                    } catch (Exception ex) {
                        portariaDAO.rollBackTransacao();
                    }

                    break;
            }
        }
    }

    @Before
    public void casoTestPrepararExecucao() {
        // Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.

        controladorCancPortRef = new ControladorCancPortRef();
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

        boolean op1 = controladorCancPortRef.cancelarPortariaReferenciada(idPortariaOp1);
        Assert.assertEquals(true, op1);
        // O cenário acima testa o cancelamento de uma portaria referenciada com indicativo de cancelamento
        // pela portaria em questão.

        boolean op2 = controladorCancPortRef.cancelarPortariaReferenciada(idPortariaOp2);
        Assert.assertEquals(true, op2);
        // O cenário acima testa o cancelamento de uma portaria referenciada a partir de uma portaria que possui
        // duas portarias referenciadas, onde apenas uma possui indicativo de cancelamento.

        boolean op3 = controladorCancPortRef.cancelarPortariaReferenciada(idPortariaOp3);
        Assert.assertEquals(true, op3);
        // O cenário acima testa a chamada do método com uma portaria que não possue referências.
    }

    @Test(expected = UnsupportedOperationException.class)
    public void casoTestDadosExcecoes() throws Exception {

        // Grupo de teste DadosExceções.

        Long idPortariaOp2 = null;
        Long idPortariaOp3 = null;
        Long idPortariaOp4 = null;
        Long idPortariaOp5 = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF20185")) {
                idPortariaOp2 = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF201812")) {
                idPortariaOp3 = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF201813")) {
                idPortariaOp4 = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF20187")) {
                idPortariaOp5 = portaria.getId();
            }

        }

        // A exeção atribuida para as chamadas abaixo é UnsupportedOperationException, mas um tipo de Error
        // expecífico pode ser implemantado e utilizado posteriormente.

        boolean op1 = controladorCancPortRef.cancelarPortariaReferenciada((long) -1);
        // O cenario acima testa a primeira exceção da seção de caso de uso, onde a portaria não é localizada na base de dados.

        boolean op2 = controladorCancPortRef.cancelarPortariaReferenciada(idPortariaOp2);
        // O cenario acima testa um dos itens da regra de negócio da seção de caso de uso, PortVali , onde a portaria possui status
        // diferente de "Ativa".

        boolean op3 = controladorCancPortRef.cancelarPortariaReferenciada(idPortariaOp3);
        // O cenario acima testa a segunda exceção da seção de caso de uso, onde uma das portarias referenciadas para
        // cancelamento possui o status "Cancelada".

        boolean op4 = controladorCancPortRef.cancelarPortariaReferenciada(idPortariaOp4);
        // O cenario acima testa a segunda exceção da seção de caso de uso, onde uma das portarias referenciadas para
        // cancelamento possui o status "Proposta".

        boolean op5 = controladorCancPortRef.cancelarPortariaReferenciada(idPortariaOp5);
        // O cenario acima testa um dos itens da regra de negócio da seção de caso de uso, PortVali , onde a portaria uma
        // das portarias que são referenciadas não existe no banco.

        // As variáveis de retorno não seram utilizadas pois a execução dos métodos vão gerar exceções.
    }

    @AfterClass
    public static void casoTestResultados() throws IOException {
        PortariaDAO portariaDAO = new PortariaDAO();

        // Aqui deve ser verificado os resultados da exceção do Grupo G1 e G2, normalmente aqui
        // irá fica as suas pós-condições.

        Long idPortariaCanceladaOp1 = null;
        Long idPortariaCanceladaOp2 = null;
        Long idPortariaNaoCanceladaOp2 = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF20180")) {
                idPortariaCanceladaOp1 = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF201814")) {
                idPortariaCanceladaOp2 = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF201811")) {
                idPortariaNaoCanceladaOp2 = portaria.getId();
            }
        }

        // Verifica a pós-condição do da execução da op1 contida no casoTestDadosValidos,
        // onde a portaria referenciada teve o status alterado para cancelada.
        Assert.assertEquals(PortariaStatus.CANCELADA, portariaDAO.buscar(idPortariaCanceladaOp1).getStatus());

        // Verifica a pós-condição do da execução da op1 contida no casoTestDadosValidos,
        // onde a primeira portaria referenciada teve o status alterado para cancelada e
        // a segunda, que não contia indicativo de cancelamento, permanece com o status inalterado.
        Assert.assertEquals(PortariaStatus.ATIVA, portariaDAO.buscar(idPortariaNaoCanceladaOp2).getStatus());
        Assert.assertEquals(PortariaStatus.CANCELADA, portariaDAO.buscar(idPortariaCanceladaOp2).getStatus());

        // Como o caso de uso realiza alterações nos dados manipulados apenas caso exceções não sejam lançadas, verificar
        // o resultado das chamadas que geram exceção é basicamente averiguar a igualdade das portarias
        // manipuladas antes e depois da chamada do método.
    }

    // Método auxiliar utilizado para obter o id lógico
    // a partir de uma instaância de Portaria.
    private static String getIdLogicoDaProtaria(Portaria portaria) {
        return portaria.getSiglaUndId() + portaria.getAnoId() + portaria.getSeqId();
    }

    // Classe auxiliar utilizada para mapear o relacionamento
    // de referência entre as portarias.
    private static class ReferenciaDoCSV {
        private String idPortaria;
        private String idPortariaReferenciada;
        private boolean ehCancelamento;

        public String getIdPortaria() {
            return idPortaria;
        }

        public void setIdPortaria(String idPortaria) {
            this.idPortaria = idPortaria;
        }

        public String getIdPortariaReferenciada() {
            return idPortariaReferenciada;
        }

        public void setIdPortariaReferenciada(String idPortariaReferenciada) {
            this.idPortariaReferenciada = idPortariaReferenciada;
        }

        public boolean getEhCancelamento() {
            return ehCancelamento;
        }

        public void setEhCancelamento(boolean ehCancelamento) {
            this.ehCancelamento = ehCancelamento;
        }
    }
}
