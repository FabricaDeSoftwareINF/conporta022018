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
    private static ArrayList<Portaria> portarias = new ArrayList();
    private static ArrayList<Portaria> portariasReferenciadas = new ArrayList();


    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos da seção de caso de uso.
     */

    @BeforeClass
    public static void casoTestPepararCenario() throws IOException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/cancPortRef/CancPortRefDadosTest.csv";
        String REGRA = ",";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;

        // Criar as instâncias de todos os objetos DAO's necessários para preparar o cenario.

        PessoaDAO pessoaDAO = new PessoaDAO();
        UndAdmDAO undAdmDAO = new UndAdmDAO();
        PortariaDAO portariaDAO = new PortariaDAO();

        // Objetos utilizados por outros

        Pessoa pessoa = null;
        UndAdm undAdm = null;
        ArrayList<ReferenciaCSV> referenciasCSV = new ArrayList();

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {
            linha = dadosSoftware.get(index);

            // Definir as tabelas que serão populadas no Banco de Dados.
            if (linha.equals("pessoa")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            if (linha.equals("undAdmin")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            if (linha.equals("portariaReferenciada")) {
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
                case "pessoa" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    if (dados[0] == "nomePes") break;

                    //Aqui colocar os comandos para popular a tabela pessoa no Banco de Dados.

                    pessoa = new Pessoa();
                    pessoa.setNomePes(dados[0]);
                    pessoa.setCpfPes(dados[1]);
                    pessoa.setEmailPes(dados[2]);
                    pessoa.setSenhaUsu(dados[3]);
                    pessoa.setEhUsuAtivo(Boolean.parseBoolean(dados[4]));

                    pessoaDAO.abrirTransacao();

                    try {
                        pessoaDAO.salvar(pessoa);
                        pessoaDAO.commitarTransacao();
                    } catch (Exception ex) {
                        pessoaDAO.rollBackTransacao();
                    }

                    break;
                case "undAdm" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    if (dados[0] == "nomeUnd") break;

                    //Aqui colocar os comandos para popular a tabela unidade administrativa no Banco de Dados.

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
                        undAdmDAO.salvar(undAdm);
                        undAdmDAO.commitarTransacao();
                    } catch (Exception ex) {
                        undAdmDAO.rollBackTransacao();
                    }

                    break;
                case "portariaReferenciada" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    if (dados[0] == "siglaUndId") break;

                    // Aqui colocar os comandos para popular a tabela portariaReferenciada no Banco de Dados.

                    Portaria portariaReferenciada = new Portaria();
                    portariaReferenciada.setSiglaUndId(dados[0]);
                    portariaReferenciada.setAnoId(Integer.parseInt(dados[1]));
                    portariaReferenciada.setSeqId(Integer.parseInt(dados[2]));
                    portariaReferenciada.setStatus(PortariaStatus.valueOf(dados[3]));
                    portariaReferenciada.setAssunto(dados[4]);
                    portariaReferenciada.setExpedidor(pessoa);
                    portariaReferenciada.setUnidadeExpedidora(undAdm);

                    portariaDAO.abrirTransacao();

                    try {
                        Portaria portariaReferenciadaDoBanco = portariaDAO.salvar(portariaReferenciada);
                        portariasReferenciadas.add(portariaReferenciadaDoBanco);
                        portariaDAO.commitarTransacao();
                    } catch (Exception ex) {
                        portariaDAO.rollBackTransacao();
                    }

                    break;
                case "referencia":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    if (dados[0] == "idPortaria") break;

                    ReferenciaCSV referenciaCSV = new ReferenciaCSV();
                    referenciaCSV.setIdPortaria(dados[0]);
                    referenciaCSV.setIdPortariaReferenciada(dados[1]);
                    referenciaCSV.setEhCancelamento(Boolean.parseBoolean(dados[2]));

                    referenciasCSV.add(referenciaCSV);

                    break;
                case "portaria" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    if (dados[0] == "siglaUndId") break;

                    // Aqui colocar os comandos para popular a tabela portaria no Banco de Dados.

                    Portaria portaria = new Portaria();
                    portaria.setSiglaUndId(dados[0]);
                    portaria.setAnoId(Integer.parseInt(dados[1]));
                    portaria.setSeqId(Integer.parseInt(dados[2]));
                    portaria.setStatus(PortariaStatus.valueOf(dados[3]));
                    portaria.setAssunto(dados[4]);
                    portaria.setExpedidor(pessoa);
                    portaria.setUnidadeExpedidora(undAdm);

                    List<Referencia> referencias = null;
                    String idLogicoPortaria = portaria.getSiglaUndId() + portaria.getAnoId() + portaria.getSeqId();

                    for (ReferenciaCSV referencia: referenciasCSV) {
                        if (referencia.getIdPortaria() == idLogicoPortaria) {
                            String idLogicoPortariaReferenciada = referencia.getIdPortariaReferenciada();

                            for (Portaria p : portariasReferenciadas) {
                                String idLogico = p.getSiglaUndId() + p.getAnoId() + p.getSeqId();

                                if (idLogico == idLogicoPortariaReferenciada) {
                                    Referencia r = new Referencia();
                                    r.setReferencia(p);
                                    r.setEhCancelamento(referencia.getEhCancelamento());
                                    referencias.add(r);
                                }
                            }
                        }
                    }

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

        // O parâmetro para a função será a instância de um Portaria (contendo suas referâncias) ou uma
        // lista de referências... Isso será confirmado dia 29/12 com os responsáveis pela expedição de portaria.
        // Apesar disso, os testes abaixo ainda podem ser compreendidos.
        
        controladorCancPortRef.cancelarPortariaReferenciada(portarias.get(0).getId());
        // O cenário acima testa o cancelamento de uma portaria referenciada com indicativo de cancelamento
        // pela portaria em questão. 
        
        controladorCancPortRef.cancelarPortariaReferenciada(portarias.get(1).getId());
        // O cenário acima testa o cancelamento de uma portaria referenciada a partir de uma portaria que possui
        // duas portarias referenciadas, onde apenas uma possui indicativo de cancelamento.

    }

    @Test(expected = Exception.class)
    public void casoTestDadosExcecoes() throws Exception {

        // Grupo de teste DadosExceções.
        
        controladorCancPortRef.cancelarPortariaReferenciada(2);
        // O cenario acima testa parâmetro inválido. A exceção esperada é IllegalArgumentException
        
        // A exeção atribuida para as chamadas abaixo é UnsupportedOperationException, mas um tipo de Error
        // expecífico pode ser implemantado e utilizado posteriormente.
        
        controladorCancPortRef.cancelarPortariaReferenciada((long) 32);
        // O cenario acima testa a primeira exceção da seção de caso de uso, onde a portaria não é localizada na base de dados.

        controladorCancPortRef.cancelarPortariaReferenciada(portarias.get(2).getId());
        // O cenario acima testa a segunda exceção da seção de caso de uso, onde uma das portarias referenciadas para
        // cancelamento possui o status "Cancelada".

        controladorCancPortRef.cancelarPortariaReferenciada(portarias.get(3).getId());
        // O cenario acima testa a segunda exceção da seção de caso de uso, onde uma das portarias referenciadas para
        // cancelamento possui o status "Proposta".
    }

    @AfterClass
    public static void casoTestResultados() throws IOException {
        PortariaDAO portariaDAO = new PortariaDAO();

        // Aqui deve ser verificado os resultados da exceção do Grupo G1 e G2, normalmente aqui
        // irá fica as suas pós-condições.

        String status = "Cancelada";
        // Pega no banco de dados os status das portarias referenciadas com indicativo de cancelamento e verifica
        // se são iguais à "Cancelada".

        Assert.assertEquals(PortariaStatus.CANCELADA, portariaDAO.buscar(portariasReferenciadas.get(0).getId()).getStatus());
        
        Assert.assertEquals(PortariaStatus.ATIVA, portariaDAO.buscar(portariasReferenciadas.get(3).getId()));
        Assert.assertEquals(PortariaStatus.CANCELADA, portariaDAO.buscar(portariasReferenciadas.get(4).getId()).getStatus());

        // Como o caso de uso realiza alterações nos dados manipulados apenas caso exceções não sejam lançadas, verificar
        // a verificação do resultado das chamadas que geram exceção é basicamente averiguar a igualdade das portarias
        // manipuladas antes e depois da chamada do método.
    }

    private static class ReferenciaCSV {
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
