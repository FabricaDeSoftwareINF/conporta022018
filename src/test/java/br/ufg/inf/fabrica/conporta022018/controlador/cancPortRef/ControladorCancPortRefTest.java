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

    /*
     * Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.
     */
    @Before
    public void prepararExecucao() {
        controladorCancPortRef = new ControladorCancPortRef();
    }

    /* GRUPO DE TESTES COM DADOS VÁLIDOS */

    /*
     * O cenário abaixo testa o cancelamento de uma portaria referenciada com indicativo de cancelamento
     * pela portaria em questão.
     */
    @Test
    public void portariaComUmaReferenciaParaCancelar() throws IOException {
        PortariaDAO portariaDAO = new PortariaDAO();

        Long idPortaria = null;
        Long idPortariaCancelada = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF201810")) {
                idPortaria = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF20180")) {
                idPortariaCancelada = portaria.getId();
            }
        }

        boolean op = controladorCancPortRef.cancelarPortariaReferenciada(idPortaria);
        Assert.assertEquals(true, op);

        // Verifica se a portaria referenciada teve o status alterado para cancelada.
        Assert.assertEquals(PortariaStatus.CANCELADA, portariaDAO.buscar(idPortariaCancelada).getStatus());
    }

    /*
    * O cenário abaixo testa o cancelamento de uma portaria referenciada a partir de uma portaria que possui
     * duas portarias referenciadas, onde apenas uma possui indicativo de cancelamento.
     */
    @Test
    public void portariaComDuasReferenciasUmaParaCancelar() throws IOException {
        PortariaDAO portariaDAO = new PortariaDAO();

        Long idPortaria = null;
        Long idPortariaCancelada = null;
        Long idPortariaNaoCancelada = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF201815")) {
                idPortaria = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF201814")) {
                idPortariaCancelada = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF201811")) {
                idPortariaNaoCancelada = portaria.getId();
            }
        }

        boolean op = controladorCancPortRef.cancelarPortariaReferenciada(idPortaria);
        Assert.assertEquals(true, op);

        // Verifica se a primeira portaria referenciada teve o status alterado para cancelada e
        // a segunda, que não contia indicativo de cancelamento, permanece com o status inalterado.
        Assert.assertEquals(PortariaStatus.ATIVA, portariaDAO.buscar(idPortariaNaoCancelada).getStatus());
        Assert.assertEquals(PortariaStatus.CANCELADA, portariaDAO.buscar(idPortariaCancelada).getStatus());
    }

    /*
     * O cenário abaixo testa a chamada do método com uma portaria que não possue referências.
     */
    @Test
    public void portariaSemReferencias() throws IOException {
        Long idPortaria = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF20184")) {
                idPortaria = portaria.getId();
            }
        }

        boolean op = controladorCancPortRef.cancelarPortariaReferenciada(idPortaria);
        Assert.assertEquals(true, op);
    }

    /* GRUPO DE TESTES COM DADOS QUE GERAM EXCEÇÃO */

    /*
     * O cenario abaixo testa a primeira exceção da seção de caso de uso,
     * onde a portaria não é localizada na base de dados.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void excecaoPortariaNaoLocalizada() throws Exception {
        boolean op = controladorCancPortRef.cancelarPortariaReferenciada((long) -1);
        Assert.assertEquals(false, op);
    }

    /*
     * O cenario abaixo testa um dos itens da regra de negócio da seção de caso de uso, PortVali, onde a portaria possui status
     * diferente de "Ativa".
     */
    @Test(expected = UnsupportedOperationException.class)
    public void excecaoPortariaNaoAtiva() throws Exception {
        Long idPortaria = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF20185")) {
                idPortaria = portaria.getId();
            }
        }

        boolean op = controladorCancPortRef.cancelarPortariaReferenciada(idPortaria);
        Assert.assertEquals(false, op);
    }

    /*
     * O cenario abaixo testa a segunda exceção da seção de caso de uso, onde uma das portarias referenciadas para
     * cancelamento possui o status "Cancelada".
     */
    @Test(expected = UnsupportedOperationException.class)
    public void excecaoReferenciaComPortariaComStatusCancelada() throws Exception {
        PortariaDAO portariaDAO = new PortariaDAO();

        Long idPortaria = null;
        Long idPortariaReferenciada = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF201812")) {
                idPortaria = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF20182")) {
                idPortariaReferenciada = portaria.getId();
            }
        }

        boolean op = controladorCancPortRef.cancelarPortariaReferenciada(idPortaria);
        Assert.assertEquals(false, op);

        // Verifica se a portaria referenciada permanece com o status inalterado.
        Assert.assertEquals(PortariaStatus.CANCELADA, portariaDAO.buscar(idPortariaReferenciada).getStatus());
    }

    /*
     * O cenario abaixo testa a segunda exceção da seção de caso de uso, onde uma das portarias referenciadas para
     * cancelamento possui o status "Proposta".
     */
    @Test(expected = UnsupportedOperationException.class)
    public void excecaoReferenciaComPortariaComStatusProposta() throws Exception {
        PortariaDAO portariaDAO = new PortariaDAO();

        Long idPortaria = null;
        Long idPortariaReferenciada = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF201813")) {
                idPortaria = portaria.getId();
            }

            if (idLogicoDaPortaria.equals("INF20183")) {
                idPortariaReferenciada = portaria.getId();
            }
        }

        boolean op = controladorCancPortRef.cancelarPortariaReferenciada(idPortaria);
        Assert.assertEquals(false, op);

        // Verifica se a portaria referenciada permanece com o status inalterado.
        Assert.assertEquals(PortariaStatus.PROPOSTA, portariaDAO.buscar(idPortariaReferenciada).getStatus());
    }

    /*
     * O cenario acima testa um dos itens da regra de negócio da seção de caso de uso, PortVali , onde uma
     * das portarias que são referenciadas não existe no banco.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void excecaoReferenciaComPortariaNaoLocalizada() throws Exception {
        Long idPortaria = null;

        for (Portaria portaria: portarias) {
            String idLogicoDaPortaria = getIdLogicoDaProtaria(portaria);

            if (idLogicoDaPortaria.equals("INF20187")) {
                idPortaria = portaria.getId();
            }
        }

        boolean op = controladorCancPortRef.cancelarPortariaReferenciada(idPortaria);
        Assert.assertEquals(false, op);
    }

    /*
     * Como a verificação do estado das portarias no banco já é realizado em cada cenário de teste
     * a seção `casoTestResultados` não se faz necessária.
     */

    /*
     * Método auxiliar utilizado para obter o id lógico
     * a partir de uma instância de Portaria.
     */
    private static String getIdLogicoDaProtaria(Portaria portaria) {
        return portaria.getSiglaUndId() + portaria.getAnoId() + portaria.getSeqId();
    }

    /*
     * Classe auxiliar utilizada para mapear o relacionamento
     * de referência entre as portarias.
     */
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
