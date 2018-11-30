/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.efetLogout;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorEfetLogout;
import br.ufg.inf.fabrica.conporta022018.modelo.RetornoEfetLogoutEnum;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import br.ufg.inf.fabrica.conporta022018.util.exception.EfetLogoutException;
import org.junit.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControladorEfetLogoutTest {

    private static ControladorEfetLogout controladorEfetLogout;

    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos do caso de uso.
     */

    @BeforeClass
    public static void casoTestPepararCenario() throws IOException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/efetLogout/EfetLogoutTest.csv";
        String REGRA = ";";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dadosPessoas[];
        String dadosSessoes[];
        String linha;
        // Inicializa o array de tabelas conhecidas
        final ArrayList TABELAS_CONHECIDAS = (ArrayList)(Arrays.asList(TabelasConhecidasEnum.values()));

        //Criar as instâncias de todos os objetos DAO's necessários para preparar o cenario.
        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (String dado : dadosSoftware) {
            linha = dado;

            //Definir as tabelas que serão populadas no Banco de Dados.
            if (TABELAS_CONHECIDAS.contains(linha)) {
                tabelaAtual = linha;
                continue;
            }

            switch (tabelaAtual) {
                case "pessoa" :
                    extrator.setTexto(linha);
                    dadosPessoas = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela pessoa no Banco de Dados.
                    break;
                case "sessao" :
                    extrator.setTexto(linha);
                    dadosSessoes = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela sessao no Banco de Dados.
                    break;
            }
        }
    }

    @Before
    public void casoTestPrepararExecucao() {

        //Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.
        controladorEfetLogout = new ControladorEfetLogout();
    }

    @Test
    public void casoTestDadosValidos() throws IOException, EfetLogoutException, ServletException {

        //Grupo de teste DadosValidos:
        String retorno = controladorEfetLogout.efetuarLogout();
        if (!retorno.equals(RetornoEfetLogoutEnum.OK.getRetorno())) {
            throw new EfetLogoutException(retorno);
        }
        // testa se a sessao foi realmente finalizada

    }

    @Test
    public void casoTestDadosExcecoes() throws IOException, EfetLogoutException, ServletException {

        //Grupo de teste DadosExcecoes:
        String retorno = controladorEfetLogout.efetuarLogout();
        if (retorno.equals(RetornoEfetLogoutEnum.ERRO_EXECUCAO.getRetorno())) {
            throw new EfetLogoutException(retorno);
        } else if (retorno.equals(RetornoEfetLogoutEnum.ERRO_JA_ENCERRADA.getRetorno())) {
            throw new EfetLogoutException(retorno);
        } else if (retorno.equals(RetornoEfetLogoutEnum.ERRO_CONEXAO_BD.getRetorno())) {
            throw new EfetLogoutException(retorno);
        } else if (!retorno.equals(RetornoEfetLogoutEnum.OK.getRetorno())) {
            throw new EfetLogoutException(RetornoEfetLogoutEnum.ERRO_INESPERADO.getRetorno());
        }
        //O cenario acima testa a primeira exceção do caso de uso em que a sessao nao pode ser finalizada.
    }

    @AfterClass
    public static void casoTestResultados() throws IOException {

        //Nada a fazer neste caso
    }

    // enum criado para tornar o codigo mais limpo, concentrando os nomes das tabelas
    private enum TabelasConhecidasEnum {

        SESSAO("sessao"),
        PESSOA("pessoa");

        private final String tabela;

        TabelasConhecidasEnum(final String tabela) {
            this.tabela = tabela;
        }

        public String getTabela() {
            return tabela;
        }

    }

}
