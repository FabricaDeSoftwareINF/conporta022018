/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.expedPorta;

        import br.ufg.inf.fabrica.conporta022018.controlador.ControladorExpedPorta;
        import br.ufg.inf.fabrica.conporta022018.util.Extrator;
        import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
        import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
        import org.junit.*;
        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;

        import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;


public class ControladorExpedPortaTest {

    private static ControladorExpedPorta controladorExpedPorta;

    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos do caso de uso.
     */

    @BeforeClass
    public static void casoTestPepararCenario() throws IOException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/expedPorta/ExpedPortaDadosTest.csv";
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
            if (linha.equals("portaComFinalVig") || linha.equals("portaDesigExist") || linha.equals("portaSemFinalVig")
                    || linha.equals("portaDesigInexist") || linha.equals("portaSemIniVig") || linha.equals("portaVigExpir")
                    || linha.equals("portaExpedida") || linha.equals("portaCancelada") || linha.equals("desigExist")
                    || linha.equals("desigInexist") || linha.equals("pessoa") || linha.equals("undAdm")) {

                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "portaComFinalVig" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela pessoa no Banco de Dados.
                    break;
                case "portaDesigExist" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela portaria no Banco de Dados.
                    break;
                case "portaSemFinalVig" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela Unidade Administrativa no Banco de Dados.
                    break;
                case "portaDesigInexist" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
                case "portaSemIniVig" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
                case "portaVigExpir" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
                case "portaExpedida" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
                case "portaCancelada" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
                case "desigExist" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
                case "desigInexist" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
                case "pessoa" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
                case "undAdm" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
            }
        }
    }

    @Before
    public void casoTestPrepararExecucao() {

        //Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.
        controladorExpedPorta = new ControladorExpedPorta();
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

        // O código retornado para um caso de sucesso é 1

        // Portaria com data final de vigência, sem designados:
        Assert.assertEquals(controladorExpedPorta.expedPorta(1), 1);

        // Portaria com data final de vigência, com designados existentes:
        Assert.assertEquals(controladorExpedPorta.expedPorta(6), 1);

        // Portaria sem data final de vigência, sem designados:
        Assert.assertEquals(controladorExpedPorta.expedPorta(2), 1);

        /* ---------- testes a serem feitos posteriormente ---------- */
        // Portaria com data final de vigência e com referências a portarias existentes e ativas:

    }

    @Test
    public void casoTestDadosExcecoes() throws IOException {

        /* Os códigos de erro são os seguintes:
        *
        * 2 - Portaria inválida - Cancelada ou Expedida
        * 3 - Portaria inválida - Período de vigência sem início ou expirado
        * 4 - Designado inválido - Inexistente
        * 5 - Portaria inexistente
        * 6 - Portaria referenciada - Inexistentes, canceladas ou propostas
        * */

        // Portaria com designado inexistente:
        Assert.assertEquals(controladorExpedPorta.expedPorta(8), 4);

        // Portaria sem data inicial de vigência:
        Assert.assertEquals(controladorExpedPorta.expedPorta(3), 3);

        // Portaria com data final de vigência expirada:
        Assert.assertEquals(controladorExpedPorta.expedPorta(4), 3);

        // Portaria expedida
        Assert.assertEquals(controladorExpedPorta.expedPorta(7), 2);

        // Portaria cancelada
        Assert.assertEquals(controladorExpedPorta.expedPorta(5), 2);

        // Portaria inexistente
        Assert.assertEquals(controladorExpedPorta.expedPorta(2469), 5);

        /* ---------- testes a serem feitos posteriormente ---------- */

        // Portaria com referências a portarias canceladas:

        // Portaria com referências a portarias propostas:

        // Portaria com referências a portarias inexistentes:
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