/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.exclPorta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorExclPort;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaReferenciadaDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorExclPortTest {

    private static ControladorExclPort controladorExclPort;
    private static PortariaDAO portariaDAO = new PortariaDAO();
    private static Portaria portaria = new Portaria();
    private static Pessoa pessoa = new Pessoa();
    private static PessoaDAO pessoaDAO = new PessoaDAO();
    private static DesignadoDAO designadoDAO = new DesignadoDAO();
    private static Designado designado = new Designado();
    private static Referencia portariaReferenciada = new Referencia();
    private static PortariaReferenciadaDAO portariaReferenciadaDAO = new PortariaReferenciadaDAO();

    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos do caso de uso.
     */

    @BeforeClass
    public static void casoTestPepararCenario() throws IOException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/exclPorta/ExclPortaDadosTest.csv";
        String REGRA = ";";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;

        //Criar as instâncias de todos os objetos DAO's necessários para preparar o cenario.

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {
            linha = dadosSoftware.get(index);

            //Definir as tabelas que serão populadas no Banco de Dados.
            if (linha.equals("pessoa") || linha.equals("portaria") || linha.equals("undAdm") || linha.equals("designado")|| linha.equals("portariaReferenciada")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "pessoa" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    pessoa = trataDadosDePessoaParaPersistencia(dados);
                    pessoaDAO.salvar(pessoa);
                    break;
                case "portaria" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    portaria = trataDadosDaPortariaParaPersistencia(dados);
                    portariaDAO.salvar(portaria);

                    break;
                case "portariaReferenciada" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    portariaReferenciada = trataDadosDaPortariaReferenciadaParaPersistencia(dados, portariaDAO);
                    portariaReferenciadaDAO.salvar(portariaReferenciada);
                    break;
                case "portariaDesignada" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela portaria designadas no Banco de dados.
                    break;
                case "undAdm" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela unidade administrativa no Banco de Dados.
                    break;
                case "designado" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    designado = trataDadosDoDesignadoParaPersistencia(dados);
                    designadoDAO.salvar(designado);
                    break;
            }
        }
    }

    @Before
    public void casoTestPrepararExecucao() {

        //Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.
        controladorExclPort = new ControladorExclPort();
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

        //ainda será complementado, o parâmetro de excluirPortaria será uma instância de Portaria e não uma string.
        Portaria portaria = new Portaria();
        //o parâmetro de excluirPortaria será uma instância de Portaria e não uma string.
        try{
            // Portaria sem data final de vigência, proposta, sem portarias referenciadas e sem designados:
            portaria = portariaDAO.buscar(Long.parseLong("INF201813"));
            controladorExclPort.excluirPortaria(portaria);

        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta, sem referencias e designados");
        }

        try{
            // Portaria sem data final de vigência, proposta, com portarias referenciadas e sem designados:
            portaria = portariaDAO.buscar(Long.parseLong("INF201800"));
            controladorExclPort.excluirPortaria(portaria);

        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com portarias referenciadas");
        }

        try{
            // Portaria sem data final de vigência, proposta, sem portarias referenciadas e com designados:
            portaria = portariaDAO.buscar(Long.parseLong("INF201803"));
            controladorExclPort.excluirPortaria(portaria);
        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com designados");
        }
    }

    @Test
    public void casoTestDadosExcecoes() throws IOException {

        try{
            // Tentativa de exclusão de portaria ativa
            portaria = portariaDAO.buscar(Long.parseLong("INF201810"));
            controladorExclPort.excluirPortaria(portaria);
        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com portarias referenciadas");
        }

        try{
            // Tentativa de exclusão de portaria cancelada
            portaria = portariaDAO.buscar(Long.parseLong("INF201810"));
            controladorExclPort.excluirPortaria(portaria);
            // Tentativa de exclusão de portaria ativa
            controladorExclPort.excluirPortaria(portaria);
        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com portarias referenciadas");
        }

        try{
            // Tentativa de exclusão de portaria expirada
            portaria = portariaDAO.buscar(Long.parseLong("INF201815"));
            controladorExclPort.excluirPortaria(portaria);
        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com portarias referenciadas");
        }
    }
//
//    @AfterClass
//    public static void casoTestResultados() throws IOException {
//
//        //Aqui deve ser verificado os resultados da exceção do Grupo G1 e G2, normalmente aqui
//        // irá fica as suas pós-condições.
//
//        //resultados devem ser nulos visto que nestes casos as portarias devem ter sido excluídas
//        Assert.assertNull(portariaDAO.buscar(Long.parseLong("INF201813")));
//        Assert.assertNull(portariaDAO.buscar(Long.parseLong("INF201800")));
//        Assert.assertNull(portariaDAO.buscar(Long.parseLong("INF201803")));
//
//         //resultados devem retornar um objeto visto que nestes casos as portarias não devem ter sido excluídas
//        Assert.assertNotNull(portariaDAO.buscar(Long.parseLong("INF201810")));
//        Assert.assertNotNull(portariaDAO.buscar(Long.parseLong("INF201810")));
//        Assert.assertNotNull(portariaDAO.buscar(Long.parseLong("INF201815")));
//    }

    public static Pessoa trataDadosDePessoaParaPersistencia(String dados[]){
        Pessoa pessoa = new Pessoa();
        pessoa.setId(Long.parseLong(dados[0]));
        pessoa.setNomePes(dados[1]);
        pessoa.setCpfPes(dados[2]);
        pessoa.setEmailPes(dados[3]);
        pessoa.setSenhaUsu(dados[4]);
        pessoa.setEhUsuAtivo(Boolean.parseBoolean(dados[5]));

        return pessoa;
    }

    public static Portaria trataDadosDaPortariaParaPersistencia(String dados[]){
        Portaria portaria = new Portaria();
        PortariaStatus status = retornaStatusPortaria(dados[5]);
        System.out.println(status);
        portaria.setSiglaUndId(dados[2]);
        portaria.setAnoId(Integer.parseInt(dados[3]));
        portaria.setSeqId(Integer.parseInt(dados[4]));
        portaria.setStatus(status);
        portaria.setAssunto(dados[6]);
        portaria.setDtExped(new Date(dados[7]));
        portaria.setDtIniVig(new Date(dados[9]));
        portaria.setDtFimVig(new Date(dados[10]));
        portaria.setDtPublicDou(new Date(dados[11]));
        portaria.setHorasDesig(Integer.parseInt(dados[12]));
        portaria.setResumo(dados[13]);
        //portaria.setTextoCompleto(dados[14]);

        return portaria;
    }

    public static Designado trataDadosDoDesignadoParaPersistencia(String dados[]){
        Designado designado = new Designado();
        Pessoa pessoa;
        pessoa = pessoaDAO.buscar(Long.parseLong(dados[6]));

        designado.setId(Long.parseLong(dados[0]));
        designado.setDtCienciaDesig(new Date(dados[1]));
        designado.setDescrFuncDesig(dados[2]);
        designado.setDescrFuncDesig(dados[3]);
        designado.setHorasDefFuncDesig(Integer.parseInt(dados[4]));
       // designado.setHorasExecFuncDesig(Integer.parseInt(dados[5]));
        designado.setDesignado(pessoa);

        return designado;
    }

    public static Referencia trataDadosDaPortariaReferenciadaParaPersistencia(String dados[], PortariaDAO portariaDAO){

        Portaria portaria = new Portaria();
        Referencia referenciada = new Referencia();

        portaria = portariaDAO.buscar(Long.parseLong(dados[2]));
        referenciada.setReferencia(portaria);
        referenciada.setEhCancelamento(Boolean.parseBoolean(dados[3]));

        return referenciada;
    }

    public static PortariaStatus retornaStatusPortaria(String dado){
        switch (dado){
            case "Proposta":
                return PortariaStatus.PROPOSTA;
            case "Ativa":
                return PortariaStatus.ATIVA;
            case "Cancelada":
                return PortariaStatus.CANCELADA;
            case "Expirada":
                return  PortariaStatus.EXPIRADA;
            default:
                return PortariaStatus.ATIVA;
        }
    }
}
