/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.exclPorta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorExclPort;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.modelo.Referencia;
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

public class ControladorExclPortaTest {

    private static ControladorExclPort controladorExclPort;
    private static PortariaDAO portariaDAO = new PortariaDAO();
    private static Portaria portaria = new Portaria();
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
            if (linha.equals("pessoa") || linha.equals("portaria") || linha.equals("undAdm") || linha.equals("designado")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "pessoa" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela pessoa no Banco de Dados.
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

        // Tentativa de exclusão de portaria ativa
        //controladorExclPort.excluirPortaria("INF201810");

        // Tentativa de exclusão de portaria cancelada
        //controladorExclPort.excluirPortaria("INF201814");

        // Tentativa de exclusão de portaria expirada
        //controladorExclPort.excluirPortaria("INF201815");

    }

    @AfterClass
    public static void casoTestResultados() throws IOException {

        //Aqui deve ser verificado os resultados da exceção do Grupo G1 e G2, normalmente aqui
        // irá fica as suas pós-condições. Exemplo:

        //Busca a data atual.
        Date hoje = new Date();
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd/MM/yyyy");
        String dataHoje = df.format(hoje);

        //pega a data que foi armazenada no banco de dados e verifica com a data de execução do teste, ou seja,
        // a data de hoje.

        //Assert.assertEquals(dataHoje, rodaSQLparaPegarADataGravadaNoBancoDeDados);
    }
    
    public static Portaria trataDadosDaPortariaParaPersistencia(String dados[]){
        Portaria portaria = new Portaria();
        portaria.setSiglaUndId(dados[2]);
        portaria.setAnoId(Integer.parseInt(dados[3]));
        portaria.setSeqId(Integer.parseInt(dados[4]));
        //portaria.setStatus((PortariaStatus)dados[5]);
        portaria.setAssunto(dados[6]);
        portaria.setDtExped(new Date(dados[7]));
        portaria.setDtIniVig(new Date(dados[9]));
        portaria.setDtFimVig(new Date(dados[10]));
        portaria.setDtPublicDou(new Date(dados[11]));
        portaria.setHorasDesig(Integer.parseInt(dados[12]));
        portaria.setResumo(dados[13]);
        portaria.setTextoCompleto(dados[14]);
        
        return portaria;
    }
    
     public static Designado trataDadosDoDesignadoParaPersistencia(String dados[]){
        Designado designado = new Designado();
        designado.setDtCienciaDesig(new Date(dados[1]));
        designado.setDescrFuncDesig(dados[3]);
        designado.setHorasDefFuncDesig(Integer.parseInt(dados[4]));
        designado.setHorasExecFuncDesig(Integer.parseInt(dados[5]));
        
        return designado;
    }
     
    public static Referencia trataDadosDaPortariaReferenciadaParaPersistencia(String dados[], PortariaDAO portariaDAO){
        
        Portaria portaria = new Portaria();
        Referencia portariaReferenciada = new Referencia();
   
        portaria = portariaDAO.buscar(Long.parseLong(dados[2]));
        
        portariaReferenciada.setReferencia(portaria);
        portariaReferenciada.setEhCancelamento(Boolean.parseBoolean(dados[3]));
        
        return portariaReferenciada;
    } 

}