/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.ediTemTim;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorManterUndAdm;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;        

public class ControladorEdiTemTimTest {

    private static ControladorManterUndAdm controladorEdiTemTim;
    //private dadosAcesso[]
    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos do caso de uso.
     */

    @BeforeClass
    public static void casoTestPepararCenario() throws IOException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/editTemTim/ControladorEdiTemTim.csv";
        String REGRA = ";";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dadosUndAdm[];
        String linha;  
        UndAdm undAdm = new UndAdm();

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {
            linha = dadosSoftware.get(index);

            //Definir as tabelas que serão populadas no Banco de Dados.
            if (linha.equals("undAdm")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {               
                case "undAdm" :
                    extrator.setTexto(linha);
                    dadosUndAdm = extrator.getResultado(REGRA);  
                    //undAdm.setSiglaUndAdm(dadosUndAdm[0]);
                    //Aqui colocar comandos para envia ao BD
                    
                    break;         
            }
        }
    }

    @Before
    public void casoTestPrepararExecucao() {

        //Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.
        controladorEdiTemTim = new ControladorManterUndAdm();
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

        //Grupo de teste DadosValidos, exemplo:   
        //Limite máximo do tempo válido uma sessão.
        UndAdm undAdm = new UndAdm();            
        controladorEdiTemTim.editarTimeOut(60, "INF");
        //Limite máximo do tempo válido uma sessão menos um.
        UndAdm undAdm2 = new UndAdm();              
        controladorEdiTemTim.editarTimeOut(59, "INF");
        //Limite mínimo do tempo válido uma sessão.
        UndAdm undAdm3 = new UndAdm();              
        controladorEdiTemTim.editarTimeOut(15, "INF");
        //Limite mínimo do tempo válido uma sessão mais um .
        UndAdm undAdm4 = new UndAdm();              
        controladorEdiTemTim.editarTimeOut(16, "INF");

    }

    @Test
    public void casoTestDadosExcecoes() throws IOException {
        //O cenario abaixo testa que a mudança do tempo de sessão para mais de uma período superior em um ao permitido.       
        controladorEdiTemTim.editarTimeOut(61, "INF");
        //O cenario abaixo testa que a mudança do tempo de sessão para um período inferior em um ao permitido.             
        controladorEdiTemTim.editarTimeOut(14, "INF");                     
        //O cenario abaixo testa que a mudança do tempo válido de sessão para uma unidade academica que não existe na base de dados.             
        controladorEdiTemTim.editarTimeOut(30, "FAV");  
        //O cenario abaixo testa que a mudança do tempo de sessão para um período inferior em um ao permitido de sessão para uma unidade
        // academica que não existe na base de dados.             
        controladorEdiTemTim.editarTimeOut(14, "FAV");  
        //O cenario abaixo testa que a mudança do tempo de sessão para um período superior em um ao permitido de sessão para uma unidade
        // academica que não existe na base de dados.             
        controladorEdiTemTim.editarTimeOut(61, "FAV");  
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

}
