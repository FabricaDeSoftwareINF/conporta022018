/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.EncPort;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorEncPort;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ControladorEncPortTest {

    private static ControladorEncPort controladorEncPort;
    private static ArrayList<Portaria> portarias = new ArrayList();
    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos da seção de caso de uso.
     */

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

        // Criar as instâncias de todos os objetos DAO's necessários para preparar o cenario.

        PessoaDAO pessoaDAO = new PessoaDAO();

        // Objetos que são usados por externos

        Pessoa pessoa = null;
        UndAdm undAdm = null;

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


            if (linha.equals("UndAdmin")) {
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

            switch (tabelaAtual) {
                case "pessoa":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    //Comandos para popular a tabela pessoa no Banco de Dados.

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
}
//    @Before
//    public void casoTestPrepararExecucao() {
//
//
//        controladorEncPort = new ControladorEncPort();
//    }
//
//    /*
//     * Criar os cenários de testes para a aplicação:
//     * Os cenarios de testes devem obrigatóriamente ser divididos em dois grupos.
//     * DadosValidos : Grupo destinado ao cenatio típico e aos cenarios alternativos do caso de uso.
//     * DadosExcecoes : Grupo destinado as exceções do cenario típico e dos cenarios alternativos.
//     * Cada cenário e cada exceção deve necessáriamente ser testado no minimo uma vez, cada entrada e/ou combinação
//     * de entrada deve ser testadas pelo menos os seus limites quando houver para o G1 e para o G2.
//     */
//
//    @Test
//    public void casoTestDadosValidos() throws IOException {
//
//
//        controladorEncPort.encPortariaCiencia("INF201802");
//
//
//        controladorEncPort.encPortariaCiencia("INF201802");
//
//    }
//
//    @Test
//    public void casoTestDadosExcecoes() throws IOException {
//
//
//        controladorEncPort.encPortariaCiencia("pessoa");
//
//    }
//
//    @AfterClass
//    public static void casoTestResultados() throws IOException {
//
//
//        List emails = new ArrayList();
//        emails.add("keslleyls@exemplo.com");
//
//        // Assert.assertEquals(emails, rodaSQLparaPegarOsEnderecosDeEmailQueDeveriamReceberEmail);
//    }
//
//}
