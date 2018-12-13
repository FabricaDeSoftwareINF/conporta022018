/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.EncPort;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorEncPort;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.RecebedoraDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.GestaoDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ControladorEncPortTest {

    private static ControladorEncPort controladorEncPort;
    private static List<Designado> designados = new ArrayList();
    private static  List<Recebedora>  recebedoras = new ArrayList();
    private static ArrayList<Portaria> portarias = new ArrayList();
    private static ArrayList<Gestao> gestaos = new ArrayList();
    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos da seção de caso de uso.
     */

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

        // Criar as instâncias de todos os objetos DAO's necessários para preparar o cenario.

        PessoaDAO pessoaDAO = new PessoaDAO();
        PortariaDAO portariaDAO = new PortariaDAO();
        DesignadoDAO designadoDAO = new DesignadoDAO();
        RecebedoraDAO recebedoraDAO = new RecebedoraDAO();
        GestaoDAO gestaoDAO = new GestaoDAO();

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


            if (linha.equals("recebedora")) {
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
                    portaria.setStatus(PortariaStatus.valueOf(dados[3])); ////Essencial para o caso de uso ter o status da portaria
                    portaria.setAssunto(dados[4]);
                    portaria.setUndRecebedora(recebedoras); //Essencial para o caso de uso ter as UndRecebedora da portaria
                    portaria.setExpedidor(pessoa);
                    portaria.setDesignados(designados); //Essencial para o caso de uso ter os designados da portaria
                    portaria.setUnidadeExpedidora(undAdm);

                    portariaDAO.abrirTransacao();

                    try {
                        Portaria portariaDoBanco = portariaDAO.salvar(portaria);
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
                    designado.setDescrFuncDesig(dados[0]);
                    designado.setDesignado(pessoa); //Essencial para o caso de uso ter os designados da portaria

                    designadoDAO.abrirTransacao();

                    try {
                        Designado designaDoBanco = designadoDAO.salvar(designado);
                        designados.add(designaDoBanco);
                        designadoDAO.commitarTransacao();
                    } catch (Exception ex) {
                        portariaDAO.rollBackTransacao();
                    }

                    break;

                case "recebedora":
                    extrator.setTexto(linha);

                    Recebedora recebedora = new Recebedora();
                    recebedora.setUnidadeRecebedora(undAdm); //Essencial para o caso de uso ter as UndRecebedora da portaria

                    recebedoraDAO.abrirTransacao();

                    try {
                        Recebedora recebedoraDoBanco = recebedoraDAO.salvar(recebedora);
                        recebedoras.add(recebedoraDoBanco);
                        recebedoraDAO.commitarTransacao();
                    } catch (Exception ex) {
                        portariaDAO.rollBackTransacao();
                    }

                    break;

                case "gestao":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    String dataString = dados[0];
                    SimpleDateFormat formataData=new SimpleDateFormat(dataString);
                    Date date1=formataData.parse(dataString);

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

            }
        }
    }

    

}

