/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.visuporta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorVisuPorta;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisuPortTest {

    private static ControladorVisuPorta controladorVisuPorta;

    /*
     * PreparaÃ§Ã£o do ambiente para teste.
     * PopulaÃ§Ã£o do banco de Dados para atendam os prÃ©-requisitos do caso de uso.
     */

    @BeforeClass
    public static void casoTestPepararCenario() throws IOException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/visuporta/VisuPortTest.csv";
        String REGRA = ";";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;
        //Criar as instÃ¢ncias de todos os objetos DAO's necessÃ¡rios para preparar o cenario.

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {
            linha = dadosSoftware.get(index);

            //Definir as tabelas que serÃ£o populadas no Banco de Dados.
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
                    //Aqui colocar os comandos para popular a tabela portaria no Banco de Dados.
                    break;
                case "undAdm" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela Unidade Administrativa no Banco de Dados.
                    break;
                case "designado" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
            }
        }
    }

    @Before
    public void casoTestPrepararExecucao() {

        //Neste Grupo ficarÃ¡ tudo que Ã© necessÃ¡rio para a execuÃ§Ã£o dos cenarios definidos para os testes.
        controladorVisuPorta = new ControladorVisuPorta();
    }

    /*
     * Criar os cenÃ¡rios de testes para a aplicaÃ§Ã£o:
     * Os cenarios de testes devem obrigatÃ³riamente ser divididos em dois grupos.
     * DadosValidos : Grupo destinado ao cenatio tÃ­pico e aos cenarios alternativos do caso de uso.
     * DadosExcecoes : Grupo destinado as exceÃ§Ãµes do cenario tÃ­pico e dos cenarios alternativos.
     * Cada cenÃ¡rio e cada exceÃ§Ã£o deve necessÃ¡riamente ser testado no minimo uma vez, cada entrada e/ou combinaÃ§Ã£o
     * de entrada deve ser testadas pelo menos os seus limites quando houver para o G1 e para o G2.
     */

    @Test
    public void casoTestDadosValidos() throws IOException {

        //Grupo de teste DadosValidos, exemplo:
        ;

    }

//    @Test
//    public void casoTestDadosExcecoes() throws IOException {
//
//        //Grupo de teste DadosExcecoes, exemplo:
//        ControladorVisuPorta.
//        //O cenario acima testa a primeira exceÃ§Ã£o do caso de uso a unidade acadÃªmica nÃ£o Ã© localizada.
//    }

    @AfterClass
    public static void casoTestResultados() throws IOException {

        //Aqui deve ser verificado os resultados da exceÃ§Ã£o do Grupo G1 e G2, normalmente aqui
        // irÃ¡ fica as suas pÃ³s-condiÃ§Ãµes. Exemplo:

        //Busca a data atual.
        Date hoje = new Date();
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd/MM/yyyy");
        String dataHoje = df.format(hoje);

        //pega a data que foi armazenada no banco de dados e verifica com a data de execuÃ§Ã£o do teste, ou seja,
        // a data de hoje.

        //Assert.assertEquals(dataHoje, rodaSQLparaPegarADataGravadaNoBancoDeDados);
    }

}