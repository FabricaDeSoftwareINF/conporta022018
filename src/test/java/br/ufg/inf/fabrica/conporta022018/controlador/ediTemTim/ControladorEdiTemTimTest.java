/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.ediTemTim;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorManterUndAdm;
import br.ufg.inf.fabrica.conporta022018.modelo.TipoUnd;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import java.io.IOException;
import java.util.*;

import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import org.junit.*;

public class ControladorEdiTemTimTest {

    private static ControladorManterUndAdm controladorEdiTemTim;

    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos do caso de uso.
     */

    @BeforeClass
    public static void casoTestPepararCenario() throws IOException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/editTemTim/ControladorEdiTemTim.csv";
        String REGRA = ",";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dadosUndAdm[];
        String linha;  
        UndAdm undAdm;
        UndAdmDAO dao = new UndAdmDAO();

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
                    undAdm = new UndAdm();
                    extrator.setTexto(linha);
                    dadosUndAdm = extrator.getResultado(REGRA);  

                    undAdm.setId(Long.parseLong(dadosUndAdm[0]));
                    undAdm.setNomeUnd(dadosUndAdm[1]);
                    undAdm.setSiglaUnAdm(dadosUndAdm[2]);
                    String tipoUnd = dadosUndAdm[3];
                    if (tipoUnd.equals("Curso"))
                        undAdm.setTipoUnd(TipoUnd.CURSO);
                    else if (tipoUnd.equals("Unidade Acadêmica"))
                        undAdm.setTipoUnd(TipoUnd.UNIDADE_ACADEMICA);
                    else if (tipoUnd.equals("Unidade Gestora"))
                        undAdm.setTipoUnd(TipoUnd.UNIDADE_GESTORA);
                    else if (tipoUnd.equals("Conselho"))
                        undAdm.setTipoUnd(TipoUnd.CONSELHO);
                    else if (tipoUnd.equals("Unidade Externa"))
                        undAdm.setTipoUnd(TipoUnd.UNIDADE_EXTERNA);
                    undAdm.setMinInat(Integer.parseInt(dadosUndAdm[4]));
                    undAdm.setUltPort(dadosUndAdm[5]);
                    undAdm.setAnoPort(Integer.parseInt(dadosUndAdm[6]));
                    undAdm.setUltNumExped(Integer.parseInt(dadosUndAdm[7]));
                    undAdm.setUltNumProp(Integer.parseInt(dadosUndAdm[8]));
                    dao.abrirTransacao();
                    try {
                        dao.salvar(undAdm);
                        dao.commitarTransacao();
                    } catch (Exception exc) {
                        dao.rollBackTransacao();
                    }
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
    public void casoTestDadosValidos() throws Exception {

        //Grupo de teste DadosValidos, exemplo:   
        //Limite máximo do tempo válido uma sessão.
        UndAdm undAdm = new UndAdm();            
        controladorEdiTemTim.editarTimeOut(60, "INF");

        //Limite máximo do tempo válido uma sessão menos um.
        UndAdm undAdm2 = new UndAdm();              
        controladorEdiTemTim.editarTimeOut(59, "EMC");

        //Limite mínimo do tempo válido uma sessão.
        UndAdm undAdm3 = new UndAdm();              
        controladorEdiTemTim.editarTimeOut(15, "FAV");

        //Limite mínimo do tempo válido uma sessão mais um .
        UndAdm undAdm4 = new UndAdm();              
        controladorEdiTemTim.editarTimeOut(16, "IME");

    }

    @Test
    public void casoTestDadosExcecoes() throws Exception {

        //O cenario abaixo testa que a mudança do tempo de sessão para mais de uma período superior em um ao permitido.       
        controladorEdiTemTim.editarTimeOut(61, "INF");

        //O cenario abaixo testa que a mudança do tempo de sessão para um período inferior em um ao permitido.             
        controladorEdiTemTim.editarTimeOut(14, "INF");

        //O cenario abaixo testa que a mudança do tempo válido de sessão para uma unidade academica que não existe na 
        //base de dados.             
        controladorEdiTemTim.editarTimeOut(30, "FACE");

        //O cenario abaixo testa que a mudança do tempo de sessão para um período inferior em um ao permitido de sessão 
        //para uma unidade academica que não existe na base de dados.             
        controladorEdiTemTim.editarTimeOut(14, "FD");

        //O cenario abaixo testa que a mudança do tempo de sessão para um período superior em um ao permitido de sessão 
        para uma unidade academica que não existe na base de dados.             
        controladorEdiTemTim.editarTimeOut(61, "FAV");  
    }

    @AfterClass
    public static void casoTestResultados() throws IOException {

        UndAdm undAdm;
        UndAdmDAO dao = new UndAdmDAO();

        final String JPQL_BUSCAR_UNIDADE = "select u UndAdm u where u.siglaUnd = :sigla";
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("sigla","INF");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(60), undAdm.getMinInat());

        map.put("sigla","EMC");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(59), undAdm.getMinInat());

        map.put("sigla","FAV");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(15), undAdm.getMinInat());

        map.put("sigla","IME");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(16), undAdm.getMinInat());
    }

}
