/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.manterDisc;

import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorManterDiscTest {

    private static ControladorDisc controladorDisc;

    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos do caso de uso.
     */

    @BeforeClass
    public static void casoTestPepararCenario() throws IOException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/manterDisc/ManterDiscTest.csv";
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
            if (linha.equals("discente")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "discente" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela discente no Banco de Dados.
                    break;
            }
        }
    }

    @Before
    public void casoTestPrepararExecucao() {

        //Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.
        controladorDisc = new ControladorDisc();
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
//        controladorDisc.regCiencDesig("123.456.789-12", "INF", 2018, 0001);

    }

    @Test
    public void casoTestDadosExcecoes() throws IOException {

        //Grupo de teste DadosExcecoes, exemplo:
//        controladorDisc.regCiencDesig("123.456.789-12", "FACE", 2018, 0001);
        //O cenario acima testa a primeira exceção do caso de uso a unidade acadêmica não é localizada.
    }

    @AfterClass
    public static void casoTestResultados() throws IOException {

        //Aqui deve ser verificado os resultados da exceção do Grupo G1 e G2, normalmente aqui
        // irá fica as suas pós-condições. Exemplo:

        //Busca a data atual.
//        Date hoje = new Date();
//        SimpleDateFormat df;
//        df = new SimpleDateFormat("dd/MM/yyyy");
//        String dataHoje = df.format(hoje);

        //pega a data que foi armazenada no banco de dados e verifica com a data de execução do teste, ou seja,
        // a data de hoje.

        //Assert.assertEquals(dataHoje, rodaSQLparaPegarADataGravadaNoBancoDeDados);
    }

}
