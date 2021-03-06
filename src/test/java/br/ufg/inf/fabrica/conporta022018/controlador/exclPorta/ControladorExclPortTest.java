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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorExclPortTest {

    private static ControladorExclPort controladorExclPort;
    private static Portaria portaria = new Portaria();
    private static PortariaDAO portariaDAO = new PortariaDAO();
    private static PessoaDAO pessoaDAO = new PessoaDAO();
    private static DesignadoDAO designadoDAO = new DesignadoDAO();
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
            if (linha.equals("pessoa") || linha.equals("portaria") || linha.equals("designado")|| linha.equals("referencia")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "pessoa" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDePessoaParaPersistencia(dados);
                    break;
                case "portaria" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDaPortariaParaPersistencia(dados);
                    break;
                case "referencia" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDaPortariaReferenciadaParaPersistencia(dados);                    
                    break;
                case "designado" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDoDesignadoParaPersistencia(dados);
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
            // Portaria proposta, sem portarias referenciadas e sem designados (INF201802):
            portaria = portariaDAO.buscar((long) 5);
            controladorExclPort.excluirPortaria(portaria);

        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta, sem referencias e designados");
        }

        try{
            // Portaria proposta, com portarias referenciadas e sem designados (INF201800):
            portaria = portariaDAO.buscar((long) 4);
            controladorExclPort.excluirPortaria(portaria);

        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com portarias referenciadas");
        }

        try{
            // Portaria proposta, sem portarias referenciadas e com designados (INF201813):
            portaria = portariaDAO.buscar((long) 8);
            controladorExclPort.excluirPortaria(portaria);
        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com designados");
        }
        
         try{
            // Portaria proposta, com portarias referenciadas e com designados (INF201816):
            portaria = portariaDAO.buscar((long) 11);
            controladorExclPort.excluirPortaria(portaria);
        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com designados");
        }
    }

    @Test
    public void casoTestDadosExcecoes() throws IOException {

        try{
            // Tentativa de exclusão de portaria ativa(INF201810)
            portaria = portariaDAO.buscar((long) 6);
            controladorExclPort.excluirPortaria(portaria);
        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com portarias referenciadas");
        }

        try{
            // Tentativa de exclusão de portaria cancelada (INF201814)
            portaria = portariaDAO.buscar((long)9);
            controladorExclPort.excluirPortaria(portaria);
        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com portarias referenciadas");
        }

        try{
            // Tentativa de exclusão de portaria expirada (INF201815)
            portaria = portariaDAO.buscar((long)10);
            controladorExclPort.excluirPortaria(portaria);
        }catch(Exception ex){
            System.out.println("Erro ao excluir portaria proposta com portarias referenciadas");
        }
    }

    @AfterClass
    public static void casoTestResultados() throws IOException {

        //Aqui deve ser verificado os resultados da exceção do Grupo G1 e G2, normalmente aqui
        // irá fica as suas pós-condições.

        //resultados devem ser nulos visto que nestes casos as portarias devem ter sido excluídas       
        Assert.assertNull(portariaDAO.buscar((long) 5));
        Assert.assertNull(portariaDAO.buscar((long) 4));
        Assert.assertNull(portariaDAO.buscar((long) 8));

         //resultados devem retornar um objeto visto que nestes casos as portarias não devem ter sido excluídas
        Assert.assertNotNull(portariaDAO.buscar((long) 6));
        Assert.assertNotNull(portariaDAO.buscar((long)9));
        Assert.assertNotNull(portariaDAO.buscar((long) 10));
    }

    public static void trataDadosDePessoaParaPersistencia(String dados[]){
        Pessoa pessoa = new Pessoa();
        pessoa.setId(Long.parseLong(dados[0]));
        pessoa.setNomePes(dados[1]);
        pessoa.setCpfPes(dados[2]);
        pessoa.setEmailPes(dados[3]);
        pessoa.setSenhaUsu(dados[4]);
        pessoa.setEhUsuAtivo(Boolean.parseBoolean(dados[5]));

        pessoaDAO.abrirTransacao();
        pessoaDAO.salvar(pessoa);
        pessoaDAO.commitarTransacao();
    }

    public static void trataDadosDaPortariaParaPersistencia(String dados[]){
        Portaria portaria = new Portaria();
        PortariaStatus status = retornaStatusPortaria(dados[5]);

        portaria.setId(Long.parseLong(dados[0]));
        portaria.setSiglaUndId(dados[2]);
        portaria.setAnoId(Integer.parseInt(dados[3]));
        portaria.setSeqId(Integer.parseInt(dados[4]));
        portaria.setStatus(status);
        portaria.setAssunto(dados[6]);
        portaria.setDtExped(new Date(dados[7]));
        portaria.setDtIniVig(new Date(dados[8]));
        portaria.setDtFimVig(new Date(dados[9]));
        portaria.setDtPublicDou(new Date(dados[10]));
        portaria.setHorasDesig(Integer.parseInt(dados[11]));
        portaria.setResumo(dados[12]);

        portariaDAO.abrirTransacao();
        portariaDAO.salvar(portaria);
        portariaDAO.commitarTransacao();
    }

    public static void trataDadosDoDesignadoParaPersistencia(String dados[]){
        Designado designado = new Designado();
        Pessoa pessoa = pessoaDAO.buscar(Long.parseLong(dados[6]));
        Portaria portaria = portariaDAO.buscar(Long.parseLong(dados[5]));
        List<Designado> designados = portaria.getDesignados() == null ? new ArrayList<Designado>() : portaria.getDesignados();

        designado.setId(Long.parseLong(dados[0]));
        designado.setDtCienciaDesig(new Date(dados[1]));
        designado.setDescrFuncDesig(dados[2]);
        designado.setDescrFuncDesig(dados[3]);
        designado.setHorasDefFuncDesig(Integer.parseInt(dados[4]));
        designado.setDesignado(pessoa);
        
        designadoDAO.abrirTransacao();
        designadoDAO.salvar(designado);
        designadoDAO.commitarTransacao();
        
        designados.add(designado);
        portaria.setDesignados(designados);
       
        portariaDAO.abrirTransacao();
        portariaDAO.salvar(portaria);
        portariaDAO.commitarTransacao();
    }

    public static void trataDadosDaPortariaReferenciadaParaPersistencia(String dados[]){
        Referencia referenciada = new Referencia();
        Portaria portaria = portariaDAO.buscar(Long.parseLong(dados[2]));
        List<Referencia> listaReferencias = portaria.getReferencias() == null ? new ArrayList<Referencia>() : portaria.getReferencias();
        
        referenciada.setId(Long.parseLong(dados[0]));
        referenciada.setReferencia(portaria);
        referenciada.setEhCancelamento(Boolean.parseBoolean(dados[3]));
        
        portariaReferenciadaDAO.abrirTransacao();
        portariaReferenciadaDAO.salvar(referenciada);
        portariaReferenciadaDAO.commitarTransacao();
       
        listaReferencias.add(referenciada);
        portaria.setReferencias(listaReferencias);
        
        portariaDAO.abrirTransacao();
        portariaDAO.salvar(portaria);
        portariaDAO.commitarTransacao();
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
