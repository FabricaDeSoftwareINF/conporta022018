package br.ufg.inf.fabrica.conporta022018.controlador.proPorta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorProPorta;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorProPortaTest{
    private static ControladorProPorta controladorProPorta;

    @BeforeClass
    public static void casoTestPrepararCenario() throws IOException{
        final String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/proPorta/ProPortaTest.csv";
        final String REGRA = ";";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {

            linha = dadosSoftware.get(index);

            if (linha.equals("pessoa")||linha.equals("portaria")||linha.equals("undAdm")||linha.equals("designado")){
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual){
                case "pessoa" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    break;
                case "portaria" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    break;
                case "undAdm" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    break;
                case "designado" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    break;
            }
        }
    }

    @Before
    public void casoTestPrepararExecucao(){
        controladorProPorta = new ControladorProPorta();
    }

    @Test
    public void casoTestDadosValidos()throws ParseException {
        DateFormat f = DateFormat.getDateInstance();
        Date dtIniVig = f.parse("12/01/2010");
        Date dtFimVig = f.parse("30/10/2019");
        controladorProPorta.ProPorta("Controle de estagio", dtIniVig, dtFimVig, 8, "" +
                "Portaria que cuida dos estagiarios na UFG", "Portaria com a responsabilidade de cuidar" +
                "dos estagiarios e manter o funcionamento dos processos de estagio");
    }

    @Test
    public void casoTestDadosInvalidos () throws ParseException{
        DateFormat f = DateFormat.getDateInstance();
        Date dtIniVig = f.parse("12/05/2019");
        Date dtFimVig = f.parse("30/10/2019");
        controladorProPorta.ProPorta("Controle de estagio", dtIniVig, dtFimVig, 28, "" +
                "Portaria que cuida dos estagiarios na UFG", "Portaria com a responsabilidade de cuidar" +
                "dos estagiarios e manter o funcionamento dos processos de estagio");
    }

    @Test
    public void ediPortaUsuarioNaoAdministradorTest() {

    }

    @Test
    public  void ediPortaStatusNaoPropostaTest() {

    }

    @Test
    public void ediPortaDadosObrigatoriosNaoPreenchidosTest() {

    }

    @Test
    public  void ediPortaReferenciaInvalidaTest() {

    }

    @Test
    public void ediPortaReferenciaValidaTest() {

    }

    @Test
    public void ediPortaDesignadosValidosTest() {

    }

    @Test
    public void ediPortaDesignadosInvalidosTest() {

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



