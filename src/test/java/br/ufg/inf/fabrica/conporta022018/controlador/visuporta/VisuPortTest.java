/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador.visuporta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorVisuPorta;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.*;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisuPortTest {

    private static ControladorVisuPorta controladorVisuPorta;
    private static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static Extrator extrator = new ExtratorCSV();

    /*
     * PreparaÃ§Ã£o do ambiente para teste.
     * PopulaÃ§Ã£o do banco de Dados para atendam os prÃ©-requisitos do caso de uso.
     */
    @BeforeClass
    public static void casoTestPepararCenario() throws IOException, ParseException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/visuporta/VisuPortTest.csv";
        String REGRA = ";";
        List<String> dadosSoftware = new ArrayList<>();
        extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;
        //Criar as instÃ¢ncias de todos os objetos DAO's necessÃ¡rios para preparar o cenario.

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {
            linha = dadosSoftware.get(index);

            if (linha.equals("pessoa") || linha.equals("portaria") || linha.equals("undAdm") || linha.equals("designado") || linha.equals("recebedora")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "pessoa":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosPessoa(dados);
                    break;
                case "undAdm":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosUndAdm(dados);
                    break;
                case "recebedora":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosRecebedora(dados);
                    break;
                case "designado":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDesignado(dados);
                    break;
                case "portaria":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosPortaria(dados);
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
    public void casoTestDadosValidos() throws IOException, Exception {
        PortariaDAO portariaDAO = new PortariaDAO();
        List<Portaria> lista = portariaDAO.buscarTodos();

        Portaria portaria = lista.get(0);

        //Grupo de teste DadosValidos, exemplo
        String retorno = controladorVisuPorta.conversorPortariaJson(portaria.getId());

        Assert.assertNotNull(portaria);
        
        
        String sb = "{\"anoId\":2018,\"dtExped\":\"Nov 12, 2018 12:00:00 AM\",\"dtIniVig\":\"Jan 1, 2019 12:00:00 AM\",\"dtFimVig\":\"Dec 31, 2021 12:00:00 AM\",\"dtPublicDou\":\"Dec 12, 2018 12:00:00 AM\",\"resumo\":\"Representa um colaborador que trabalha em uma unidade administrativa independentemente da forma de contrato deste trabalho.\",\"textoCompleto\":\"Representa uma unidade que compÃµe a estrutura acadÃªmica e administrativa da universidade. Conforme o Art. 7Âº do Estatuto da UFG\",\"siglaUndId\":\"INF\",\"status\":\"Ativa\",\"undRecebedora\":[null],\"designados\":[null,null],\"expedidor\":{\"nomePes\":\"JosÃ© Carlos Santos\",\"cpfPes\":\"123.456.789-12\",\"emailPes\":\"jose@exemplo.com\",\"senhaUsu\":\"123456\",\"ehUsuAtivo\":true,\"id\":1},\"id\":26}";
        
        Assert.assertEquals(sb, retorno);
        
        System.out.println(retorno);
    }
    
       @Test (expected = Exception.class)
        public void casoTestDadosExcecoes() throws IOException, Exception {
        PortariaDAO portariaDAO = new PortariaDAO();
        List<Portaria> lista = portariaDAO.buscarTodos();

        Portaria portaria = lista.get(0);

        //Grupo de teste DadosValidos, exemplo
        String retorno = controladorVisuPorta.conversorPortariaJson((long) -1);
       
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

    private static void trataDadosPessoa(String dados[]) {
        // Instancia os objetos
        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa pessoa = new Pessoa();

        // Preenche o objeto
        pessoa.setId(Long.parseLong(dados[0]));
        pessoa.setNomePes(dados[1]);
        pessoa.setCpfPes(dados[2]);
        pessoa.setEmailPes(dados[3]);
        pessoa.setSenhaUsu(dados[4]);
        if (dados[5].equals("true")) {
            pessoa.setEhUsuAtivo(true);
        } else {
            pessoa.setEhUsuAtivo(false);
        }

        // Salva o objeto no banco
        pessoaDAO.abrirTransacao();
        pessoaDAO.salvar(pessoa);
        pessoaDAO.commitarTransacao();
    }

    private static void trataDadosUndAdm(String dados[]) {
        // Instancia os objetos
        UndAdmDAO undAdmDAO = new UndAdmDAO();
        UndAdm undAdm = new UndAdm();

        // Preenche o objeto
        undAdm.setId(Long.parseLong(dados[0]));
        undAdm.setSiglaUnAdm(dados[1]);
        undAdm.setMinInat(Integer.parseInt(dados[2]));
        undAdm.setNomeUnd(dados[3]);
        undAdm.setTipoUnd(1);
        undAdm.setUltPort(dados[5]);
        undAdm.setAnoPort(Integer.parseInt(dados[6]));
        undAdm.setUltNumExped(Integer.parseInt(dados[7]));
        undAdm.setUltNumProp(Integer.parseInt(dados[8]));

        // Salva o objeto no banco
        undAdmDAO.abrirTransacao();
        undAdmDAO.salvar(undAdm);
        undAdmDAO.commitarTransacao();
    }

    private static void trataDadosRecebedora(String dados[]) throws ParseException {
        // Instancia os objetos
        RecebedoraDAO recebedoraDAO = new RecebedoraDAO();
        Recebedora recebedora = new Recebedora();

        // Preenche o objeto
        recebedora.setId(Long.parseLong(dados[0]));

        if (!dados[1].isEmpty()) {
            Date dtCienciaReeb = formatter.parse(dados[1]);
            recebedora.setDtCienciaReeb(dtCienciaReeb);
        }

        UndAdmDAO undAdmDAO = new UndAdmDAO();
        UndAdm undRecebedora = undAdmDAO.buscar(Long.parseLong(dados[2]));
        recebedora.setUnidadeRecebedora(undRecebedora);

        // Salva o objeto no banco
        recebedoraDAO.abrirTransacao();
        recebedoraDAO.salvar(recebedora);
        recebedoraDAO.commitarTransacao();
    }

    private static void trataDadosDesignado(String dados[]) throws ParseException {
        // Instancia os objetos
        DesignadoDAO designadoDAO = new DesignadoDAO();
        Designado designado = new Designado();

        // Preenche o objeto
        designado.setId(Long.parseLong(dados[0]));

        if (!dados[1].isEmpty()) {
            Date dtCienciaDesig = formatter.parse(dados[1]);
            designado.setDtCienciaDesig(dtCienciaDesig);
        }

        designado.setDescrFuncDesig(dados[2]);

        if (!dados[3].isEmpty()) {
            designado.setHorasDefFuncDesig(Integer.parseInt(dados[3]));
        }

        if (!dados[4].isEmpty()) {
            designado.setHorasExecFuncDesig(Integer.parseInt(dados[4]));
        }

        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa pessoa = pessoaDAO.buscar(Long.parseLong(dados[5]));
        designado.setPessoa(pessoa);

        designado.setFuncaoDesig(FuncaoDesig.valueOf(dados[6]));

        // Salva o objeto no banco
        designadoDAO.abrirTransacao();
        designadoDAO.salvar(designado);
        designadoDAO.commitarTransacao();
    }

    private static void trataDadosPortaria(String dados[]) throws ParseException {
        // Instancia os objetos
        PortariaDAO portariaDAO = new PortariaDAO();
        Portaria portaria = new Portaria();

        // Preenche o objeto
        portaria.setId(Long.parseLong(dados[0]));
        portaria.setAnoId(Integer.parseInt(dados[1]));
        portaria.setSeqId(Integer.parseInt(dados[2]));
        portaria.setAssunto(dados[3]);

        if (!dados[4].isEmpty()) {
            Date dtExped = formatter.parse(dados[4]);
            portaria.setDtExped(dtExped);
        }

        Date dtIniVig = formatter.parse(dados[5]);
        portaria.setDtIniVig(dtIniVig);

        if (!dados[6].isEmpty()) {
            Date dtFimVig = formatter.parse(dados[6]);
            portaria.setDtFimVig(dtFimVig);
        }

        if (!dados[7].isEmpty()) {
            Date dtPublicDou = formatter.parse(dados[7]);
            portaria.setDtPublicDou(dtPublicDou);
        }

        if (!dados[8].isEmpty()) {
            portaria.setHorasDesig(Integer.parseInt(dados[8]));
        }

        portaria.setResumo(dados[9]);

        portaria.setTextoCompleto(dados[10]);

        // Verifica se existe arquivo pdf
        if (!dados[11].isEmpty()) {
            // Pega o path do projeto
            String dir = System.getProperty("user.dir");
            // Cria o arquivo
            File arquivo = new File(dir + "/src/test/resources/" + dados[11]);
            if (arquivo.exists()) {
                // Caso o arquivo exista, tenta gravar os bytes
                try {
                    byte[] fileContent = Files.readAllBytes(arquivo.toPath());
                    portaria.setArqPdf(fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        portaria.setSiglaUndId(dados[12]);

        portaria.setStatus(PortariaStatus.valueOf(dados[13]));

        if (!dados[14].isEmpty()) {
            PessoaDAO pessoaDAO = new PessoaDAO();
            Pessoa expeditor = pessoaDAO.buscar(Long.parseLong(dados[14]));
            portaria.setExpedidor(expeditor);
        }

        if (!dados[15].isEmpty()) {
            UndAdmDAO undAdmDAO = new UndAdmDAO();
            UndAdm undAdmExpedidora = undAdmDAO.buscar(Long.parseLong(dados[15]));
            portaria.setUnidadeExpedidora(undAdmExpedidora);
        }

        if (!dados[16].isEmpty()) {
            extrator.setTexto(dados[16]);
            String recebedorasId[] = extrator.getResultado("-");
            List<Recebedora> listaRecebedoras = new ArrayList<>();
            RecebedoraDAO recebedoraDAO = new RecebedoraDAO();

            for (int i = 0; i < recebedorasId.length; i++) {
                Recebedora recebedora = recebedoraDAO.buscar(Long.parseLong(recebedorasId[i]));
                listaRecebedoras.add(recebedora);
            }

            portaria.setUndRecebedora(listaRecebedoras);
        }

        if (!dados[17].isEmpty()) {
            extrator.setTexto(dados[17]);
            String designadosId[] = extrator.getResultado("-");
            List<Designado> listaDesignados = new ArrayList<>();
            DesignadoDAO designadoDAO = new DesignadoDAO();

            for (int i = 0; i < designadosId.length; i++) {
                Designado designado = designadoDAO.buscar(Long.parseLong(designadosId[i]));
                listaDesignados.add(designado);
            }

            portaria.setDesignados(listaDesignados);
        }

        // Salva o objeto no banco
        portariaDAO.abrirTransacao();
        portariaDAO.salvar(portaria);
        portariaDAO.commitarTransacao();
    }

}
