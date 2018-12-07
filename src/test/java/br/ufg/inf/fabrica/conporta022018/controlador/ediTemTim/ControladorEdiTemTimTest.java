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

/*
 * Classe tem o intuito de exercitar o controlador referente ao caso de uso EdiTemTim.
 * Para este cenário foi utilizado a estrategia de pontos críticos.
 *  1 - Testando os valores limites inferior;
 *  2 - Testando os valores limites superiores;
 *  3 - Testando os valores limites;
 *  4 - Tentando com valores extrategicos dentro e fora do limite definido.
 *  5 - Para os cenários de excerção serão tratadas no try/catch e validada as excerções.
 *
 *  Limites:  15 <= minInat <=60 minutos, para isso definido valores inteiros sendo:
 *  1 = 1 minuto, 1 = 2 minutos, ..., 60 = 60 minutos.
 */

public class ControladorEdiTemTimTest {

    private static ControladorManterUndAdm controladorEdiTemTim;
    private static final String UNIDADE_INVALIDA = "A Unidade informada não foi localizada.";
    private static final String TEMPO_INVALIDO = "Tempo Inválido! Por favor, Digite um Tempo para " +
            "inatividade entre 15 e 60 segundos, inclusive-os.";


    @Test
    public void casoTestControladorEdiTemTim() throws Exception {

        /*
         * Os comandos abaixo prepara o ambiente para a execução dos testes, ou seja,
         * ele popula a base da dados realiza configuração de variáveis globais.
         */
        String CAMINHO_CSV = "ControladorEdiTemTim.csv";
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

            /*
             * Definir as tabelas que serão populadas.
             */
            if (linha.equals("undAdm")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            /*
             * Inserir os dados na base de dados.
             */
            switch (tabelaAtual) {
                case "undAdm":
                    undAdm = new UndAdm();
                    extrator.setTexto(linha);
                    dadosUndAdm = extrator.getResultado(REGRA);

                    //undAdm.setId(Long.parseLong(dadosUndAdm[0]));
                    undAdm.setNomeUnd(dadosUndAdm[1]);
                    undAdm.setSiglaUnAdm(dadosUndAdm[2]);
                    TipoUnd tipo = TipoUnd.valueOf(dadosUndAdm[3]);
                    undAdm.setTipoUnd(tipo);
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

        /*            -------->>> FINALIZAÇÂO DA PREPARAÇÃO DO AMBIENTE <<<---------                 */



        /*
         * Como as alterações são realizadas na base de dados os testes estão organizados de
         * acordo com a estrutura abaixo:
         *
         * Seção 1 - Exercita os cenários de sucesso do caso de uso.
         * Seção 2 - Exercita os cenários de excerções do caso de uso.
         * Seção 3 - Válida as operações realizadas nas seções anteriores.
         */



        /*                -------->>> SEÇÃO 1 - CENÁRIOS DE SUCESSO  <<<---------                    */

        controladorEdiTemTim = new ControladorManterUndAdm();

        /*
         * Cenário que testa os limites.
         */
        controladorEdiTemTim.editarTimeOut(60, "INF");
        controladorEdiTemTim.editarTimeOut(15, "IME");

        /*
         * Cenário que testa os valores proxímo ao limite válidos.
         */
        controladorEdiTemTim.editarTimeOut(59, "IESA");
        controladorEdiTemTim.editarTimeOut(16,"FD");

        /*
         * Cenário  que testa valores estratégicos válidos.
         */
        controladorEdiTemTim.editarTimeOut(20, "IF");
        controladorEdiTemTim.editarTimeOut(35, "IQ");
        controladorEdiTemTim.editarTimeOut(47, "ICB");
        controladorEdiTemTim.editarTimeOut(53, "FL");


        /*                -------->>> SEÇÃO 2 - CENÁRIOS DE EXCERÇÕES   <<<---------                  */

        /*
         * Cenário que testa os valores limites superiores e inferiores próximo.
         *
         * Espera se "True" no lançamento da exceção.
         */
        try {
            controladorEdiTemTim.editarTimeOut(61,"FF");
        } catch (Exception e) {
            Assert.assertEquals(TEMPO_INVALIDO, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(14, "FM");
        } catch (Exception e) {
            Assert.assertEquals(TEMPO_INVALIDO, e.getMessage());
        }

        /*
         * Cenário  que testa valores estratégicos inválidos.
         *
         * Espera se "True" no lançamento da exceção.
         */
        try {
            controladorEdiTemTim.editarTimeOut(68, "EMC");
        } catch (Exception e) {
            Assert.assertEquals(TEMPO_INVALIDO, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(100, "EECA");
        } catch (Exception e) {
            Assert.assertEquals(TEMPO_INVALIDO, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(75, "EA");
        } catch (Exception e) {
            Assert.assertEquals(TEMPO_INVALIDO, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(10, "EVZ");
        } catch (Exception e) {
            Assert.assertEquals(TEMPO_INVALIDO, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(7, "FED");
        } catch (Exception e) {
            Assert.assertEquals(TEMPO_INVALIDO, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(-1, "FACE");
        } catch (Exception e) {
            Assert.assertEquals(TEMPO_INVALIDO, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(0, "FAFIL");
        } catch (Exception e) {
            Assert.assertEquals(TEMPO_INVALIDO, e.getMessage());
        }

        /*
         * Cenário que testa unidade inválida.
         *
         * Espera se "True" no lançamento da exceção.
         */
        try {
            controladorEdiTemTim.editarTimeOut(60, "EFR");
        } catch (Exception e) {
            Assert.assertEquals(UNIDADE_INVALIDA, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(30, "YRT");
        } catch (Exception e) {
            Assert.assertEquals(UNIDADE_INVALIDA, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(45, "URF");
        } catch (Exception e) {
            Assert.assertEquals(UNIDADE_INVALIDA, e.getMessage());
        }

        /*
         * Cenário que testa os dois valores de entrada inválido.
         *
         * Espera se "True" no lançamento da exceção.
         */
        try {
            controladorEdiTemTim.editarTimeOut(75, "EWQ");
        } catch (Exception e) {
            Assert.assertEquals(UNIDADE_INVALIDA, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(-12, "TFR");
        } catch (Exception e) {
            Assert.assertEquals(UNIDADE_INVALIDA, e.getMessage());
        }
        try {
            controladorEdiTemTim.editarTimeOut(7, "ASD");
        } catch (Exception e) {
            Assert.assertEquals(UNIDADE_INVALIDA, e.getMessage());
        }


        /*                -------->>> SEÇÃO 3 - VÁLIDA OPERAÇÕES   <<<---------                      */

        Map<String, Object> map = new HashMap<String, Object>();
        final String JPQL_BUSCAR_UNIDADE = "SELECT u FROM UndAdm u WHERE u.siglaUnAdm = :sigla";

        /*
         * Válida as operações realizadas na base de dados.
         * Espera se "True" confirmando o novo tempo na base de dados.
         */
        map.put("sigla","INF");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(60), undAdm.getMinInat());

        map.put("sigla","IME");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(15), undAdm.getMinInat());

        map.put("sigla","IESA");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(59), undAdm.getMinInat());

        map.put("sigla","FD");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(16), undAdm.getMinInat());

        map.put("sigla","IF");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(20), undAdm.getMinInat());

        map.put("sigla","IQ");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(35), undAdm.getMinInat());

        map.put("sigla","ICB");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(47), undAdm.getMinInat());

        map.put("sigla","FL");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertEquals(Integer.valueOf(53), undAdm.getMinInat());


        /*
         * Válida as operações realizadas na base de dados.
         * Espera se "False" confirmando o novo tempo não foi armazenado na base de dados.
         */
        map.put("sigla","FF");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertNotEquals(Integer.valueOf(61), undAdm.getMinInat());

        map.put("sigla","FM");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertNotEquals(Integer.valueOf(14), undAdm.getMinInat());

        map.put("sigla","EMC");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertNotEquals(Integer.valueOf(68), undAdm.getMinInat());

        map.put("sigla","EECA");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertNotEquals(Integer.valueOf(100), undAdm.getMinInat());

        map.put("sigla","EA");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertNotEquals(Integer.valueOf(75), undAdm.getMinInat());

        map.put("sigla","EVZ");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertNotEquals(Integer.valueOf(10), undAdm.getMinInat());

        map.put("sigla","FED");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertNotEquals(Integer.valueOf(7), undAdm.getMinInat());

        map.put("sigla","FACE");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertNotEquals(Integer.valueOf(-1), undAdm.getMinInat());

        map.put("sigla","FAFIL");
        undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);
        Assert.assertNotEquals(Integer.valueOf(0), undAdm.getMinInat());

    }

}
