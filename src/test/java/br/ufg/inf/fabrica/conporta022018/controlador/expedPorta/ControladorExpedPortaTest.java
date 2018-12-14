/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.expedPorta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorExpedPorta;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.*;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorExpedPortaTest {

    private static ControladorExpedPorta controladorExpedPorta;
    private static PortariaDAO portariaDAO = new PortariaDAO();
    private static DesignadoDAO designadoDAO = new DesignadoDAO();
    private static PessoaDAO pessoaDAO = new PessoaDAO();
    private static LotacaoDAO lotacaoDAO = new LotacaoDAO();
    private static ReferenciaDAO referenciaDAO = new ReferenciaDAO();
    private static UndAdmDAO undAdmDAO = new UndAdmDAO();
    private static List<Long[]> designadoList = new ArrayList<>();
    private static List<Long[]> referenciasList = new ArrayList<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private static List<Pessoa> pessoas = new ArrayList<>();
    private static List<Portaria> portarias = new ArrayList<>();
    private static List<UndAdm> undAdms = new ArrayList<>();
    private static List<Designado> designados = new ArrayList<>();
    private static List<Referencia> referencias = new ArrayList<>();
    private static List<Lotacao> lotacoes = new ArrayList<>();

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
            if (linha.equals("portaria") || linha.equals("designado") || linha.equals("pessoa") ||
                    linha.equals("undAdm") || linha.equals("lotacao") || linha.equals("referencia") ||
                    linha.equals("portariasExpedidasOuReferenciadas") || linha.equals("portariasPropostas")) {

                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "portariasExpedidasOuReferenciadas":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDaPortariaParaPersistencia(dados);
                    break;
                case "portariasPropostas":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDaPortariaParaPersistencia(dados);
                    break;
                case "designado":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDoDesignadoParaPersistencia(dados);
                    break;
                case "pessoa":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDaPessoaParaPersistencia(dados);
                    break;
                case "undAdm":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDaUndAdmParaPersistencia(dados);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
                case "lotacao":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDaLotacaoParaPersistencia(dados);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
                case "referencia":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDaReferenciaParaPersistencia(dados);
                    //Aqui colocar os comandos para popular a tabela designados no Banco de dados.
                    break;
            }
        }


        undAdmDAO.abrirTransacao();
        for (UndAdm undAdm : undAdms) {
            undAdmDAO.salvar(undAdm);
        }
        undAdmDAO.commitarTransacao();
        lotacaoDAO.abrirTransacao();
        for (Lotacao lotacao : lotacoes) {
            lotacaoDAO.salvar(lotacao);
        }
        lotacaoDAO.commitarTransacao();
        pessoaDAO.abrirTransacao();
        for (Pessoa pessoa : pessoas) {
            pessoaDAO.salvar(pessoa);
        }
        pessoaDAO.commitarTransacao();
        portariaDAO.abrirTransacao();
        for (int i = 0; i < 5; i++) {
            portariaDAO.salvar(portarias.get(i));
        }
        portariaDAO.commitarTransacao();
        referenciaDAO.abrirTransacao();
        for (Referencia referencia : referencias) {
            referenciaDAO.salvar(referencia);
        }
        referenciaDAO.commitarTransacao();
        designadoDAO.abrirTransacao();
        for (Designado designado : designados) {
            designadoDAO.salvar(designado);
        }
        designadoDAO.commitarTransacao();
        portariaDAO.abrirTransacao();
        for (int i = 5; i < portarias.size(); i++) {
            portariaDAO.salvar(portarias.get(i));
        }
        portariaDAO.commitarTransacao();

        // aqui
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

        // Portaria sem designados, sem referências e sem data final de vigência:
        Assert.assertEquals(1, controladorExpedPorta.expedPorta(2, 1));

        // Portaria sem designados, sem referências e com data final de vigência:
        Assert.assertEquals(1, controladorExpedPorta.expedPorta(1, 1));

        // Portaria sem designados, com referências e sem data final de vigência:
        Assert.assertEquals(1, controladorExpedPorta.expedPorta(9, 1));

        // Portaria sem designados, com referências e com data final de vigência:
        Assert.assertEquals(1, controladorExpedPorta.expedPorta(10, 1));

        // Portaria com designados, sem referências e sem data final de vigência:
        Assert.assertEquals(1, controladorExpedPorta.expedPorta(12, 1));

        // Portaria com designados, sem referências e com data final de vigência:
        Assert.assertEquals(1, controladorExpedPorta.expedPorta(6, 1));

        // Portaria com designados, com referências e sem data final de vigência:
        Assert.assertEquals(1, controladorExpedPorta.expedPorta(13, 1));

        // Portaria com designados, com referências e com data final de vigência:
        Assert.assertEquals(1, controladorExpedPorta.expedPorta(15, 1));

    }

    @Test
    public void casoTesteAssinaturaValidos() {
        // Todos os casos abaixo são de sucesso, eles precisam apenas apresentarem a assinatura esperada
        Assert.assertEquals(controladorExpedPorta.assinar(new Long[]{20376l, 372469l}),
                new char[]{'A', '7', '4', 'C', '8', '2', '5', '7', 'F', 'A'});
        Assert.assertEquals(controladorExpedPorta.assinar(new Long[]{15487l, 156546l}),
                new char[]{'1', 'E', 'B', 'F', '0', '1', 'B', '1', '4', '1'});
        Assert.assertEquals(controladorExpedPorta.assinar(new Long[]{2514l, 21548l}),
                new char[]{'0', 'A', '2', 'F', '0', '0', '2', 'A', '1', '6'});
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
        Assert.assertEquals(4, controladorExpedPorta.expedPorta(8, 1));

        // Portaria sem data inicial de vigência:
        Assert.assertEquals(3, controladorExpedPorta.expedPorta(3, 1));

        // Portaria com data final de vigência expirada:
        Assert.assertEquals(3, controladorExpedPorta.expedPorta(4, 1));

        // Portaria expedida
        Assert.assertEquals(2, controladorExpedPorta.expedPorta(7, 1));

        // Portaria cancelada
        Assert.assertEquals(2, controladorExpedPorta.expedPorta(5, 1));

        // Portaria inexistente
        Assert.assertEquals(5, controladorExpedPorta.expedPorta(2469, 1));

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

    /**
     * Recebe os dados da linha lida da massa de dados no arquivo .csv e persiste os dados da Pessoa identificada, para
     * que seja possível realizar os testes.
     *
     * @param dados Linha da massa de dados contendo as informações da Pessoa a ser persistida.
     */
    public static void trataDadosDaPessoaParaPersistencia(String[] dados){
        Pessoa pessoa = new Pessoa();

        pessoa.setId(Long.parseLong(dados[0]));
        pessoa.setNomePes(dados[1]);
        pessoa.setCpfPes(dados[2]);
        pessoa.setEmailPes(dados[3]);
        pessoa.setSenhaUsu(dados[4]);
        pessoa.setEhUsuAtivo(Boolean.parseBoolean(dados[5]));

        for(Lotacao lotacao : lotacoes){
            if(lotacao.getId().equals(Long.parseLong(dados[6]))){
                pessoa.setServidor(lotacao);
                break;
            }
        }
        //pessoa.setServidor(lotacaoDAO.buscar(Long.parseLong(dados[6])));

        pessoas.add(pessoa);
        /*
        pessoaDAO.abrirTransacao();
        pessoaDAO.salvar(pessoa);
        pessoaDAO.commitarTransacao();*/
    }

    /**
     * Recebe os dados da linha lida da massa de dados no arquivo .csv e persiste os dados do Designado identificado,
     * para que seja possível realizar os testes.
     *
     * @param dados Linha da massa de dados contendo as informações do Designado a ser persistido.
     */
    public static void trataDadosDoDesignadoParaPersistencia(String[] dados){
        Designado designado = new Designado();

        try {
            designado.setId(Long.parseLong(dados[0]));
            if(!dados[1].isEmpty()) designado.setDtCienciaDesig(sdf.parse(dados[1]));
            designado.setTipFuncDesig(retornaFuncaoDesig(dados[2]));
            designado.setDescrFuncDesig(dados[3]);
            designado.setHorasDefFuncDesig(Integer.parseInt(dados[4]));
            if(!dados[5].isEmpty()) designado.setHorasExecFuncDesig(Integer.parseInt(dados[5]));

            for(Pessoa pessoa : pessoas){
                if(pessoa.getId().equals(Long.parseLong(dados[7]))){
                    designado.setDesignado(pessoa);
                    break;
                }
            }
            //designado.setDesignado(pessoaDAO.buscar(Long.parseLong(dados[7])));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        designados.add(designado);
        designadoList.add(new Long[]{Long.parseLong(dados[0]), Long.parseLong(dados[6])});
/*
        designadoDAO.abrirTransacao();
        designadoDAO.salvar(designado);
        designadoDAO.commitarTransacao();*/
    }

    /**
     * Recebe os dados da linha lida da massa de dados no arquivo .csv e persiste os dados da Unidade Administrativa
     * identificada, para que seja possível realizar os testes.
     *
     * @param dados Linha da massa de dados contendo as informações da Unidade Administrativa a ser persistida.
     */
    public static void trataDadosDaUndAdmParaPersistencia(String[] dados){
        UndAdm undAdm = new UndAdm();
        // Tipo tipo = retornaTipoUndAdm(dados[3]);

        undAdm.setId(Long.parseLong(dados[0]));
        undAdm.setNomeUnd(dados[1]);
        undAdm.setSiglaUnAdm(dados[2]);
        // undAdm.setTipoUnd(Integer.parseInt(tipo);
        undAdm.setMinInat(Integer.parseInt(dados[4]));
        undAdm.setUltPort(dados[5]);
        undAdm.setAnoPort(Integer.parseInt(dados[6]));
        undAdm.setUltNumExped(Integer.parseInt(dados[7]));
        undAdm.setUltNumProp(Integer.parseInt(dados[8]));

        undAdms.add(undAdm);
        /*
        undAdmDAO.abrirTransacao();
        undAdmDAO.salvar(undAdm);
        undAdmDAO.commitarTransacao();*/
    }

    /**
     * Recebe os dados da linha lida da massa de dados no arquivo .csv e persiste os dados da Lotacao identificada, para
     * que seja possível realizar os testes.
     *
     * @param dados Linha da massa de dados contendo as informações da Lotacao a ser persistida.
     */
    public static void trataDadosDaLotacaoParaPersistencia(String[] dados){
        Lotacao lotacao = new Lotacao();
        Cargo cargo = retornaCargoServid(dados[4]);

        try {
            lotacao.setId(Long.parseLong(dados[0]));
            if(!dados[1].isEmpty()) lotacao.setDtIniLotServ(sdf.parse(dados[1]));
            if(!dados[2].isEmpty()) lotacao.setDtFimLotServ(sdf.parse(dados[2]));
            lotacao.setDescrCargoServ(dados[3]);
            lotacao.setCargoServ(cargo);
            for(UndAdm unidade : undAdms){
                if(unidade.getId().equals(Long.parseLong(dados[5]))){
                    lotacao.setUndAdm(unidade);
                    break;
                }
            }
            //lotacao.setUndAdm(undAdmDAO.buscar(Long.parseLong(dados[5])));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        lotacoes.add(lotacao);
        /*
        lotacaoDAO.abrirTransacao();
        lotacaoDAO.salvar(lotacao);
        lotacaoDAO.commitarTransacao();*/
    }

    /**
     * Recebe os dados da linha lida da massa de dados no arquivo .csv e persiste os dados da Referencia identificada,
     * para que seja possível realizar os testes.
     *
     * @param dados Linha da massa de dados contendo as informações da Referencia a ser persistida.
     */
    private static void trataDadosDaReferenciaParaPersistencia(String[] dados) {
        Referencia referencia = new Referencia();

        referenciasList.add(new Long[]{Long.parseLong(dados[0]), Long.parseLong(dados[1])});
        referencia.setId(Long.parseLong(dados[0]));
        for(Portaria portaria : portarias){
            if(portaria.getId().equals(Long.parseLong(dados[2]))){
                referencia.setReferencia(portaria);
                break;
            }
        }
        //referencia.setReferencia(portariaDAO.buscar(Long.parseLong(dados[2])));
        referencia.setEhCancelamento(Boolean.parseBoolean(dados[3]));
        referencias.add(referencia);

        /*
        referenciaDAO.abrirTransacao();
        referenciaDAO.salvar(referencia);
        referenciaDAO.commitarTransacao();*/
    }

    /**
     * Recebe os dados da linha lida da massa de dados no arquivo .csv e persiste os dados da Portaria identificada,
     * para que seja possível realizar os testes.
     *
     * @param dados Linha da massa de dados contendo as informações da Portaria a ser persistida.
     */
    public static void trataDadosDaPortariaParaPersistencia(String[] dados){
        Portaria portaria = new Portaria();
        PortariaStatus status = retornaStatusPortaria(dados[4]);

        try {
            portaria.setId(Long.parseLong(dados[0]));
            portaria.setSiglaUndId(dados[1]);
            portaria.setAnoId(Integer.parseInt(dados[2]));
            portaria.setSeqId(Integer.parseInt(dados[3]));
            portaria.setStatus(status);
            portaria.setAssunto(dados[5]);
            if(!dados[6].isEmpty()) portaria.setDtExped(sdf.parse(dados[6]));
            if(!dados[7].isEmpty()) portaria.setDtIniVig(sdf.parse(dados[7]));
            if(!dados[8].isEmpty()) portaria.setDtFimVig(sdf.parse(dados[8]));
            if(!dados[9].isEmpty()) portaria.setDtPublicDou(sdf.parse(dados[9]));
            if(!dados[10].isEmpty()) portaria.setHorasDesig(Integer.parseInt(dados[10]));
            portaria.setResumo(dados[11]);
            portaria.setTextoCompleto(dados[12]);

            for(Pessoa pessoa : pessoas){
                if(pessoa.getId().equals(Long.parseLong(dados[13]))){
                    portaria.setExpedidor(pessoa);
                    break;
                }
            }
            //portaria.setExpedidor(pessoaDAO.buscar(Long.parseLong(dados[13])));
        } catch (ParseException e) {

            e.printStackTrace();
        }

        portaria.setDesignados(new ArrayList<>());
        if(!designados.isEmpty()) {
            for (int i = 0; i < designados.size(); i++) {
                if (designadoList.get(i)[1].equals(portaria.getId())) {
                    portaria.getDesignados().add(designados.get(i));
                }
            /*if(designados.get(i)[1].equals(portaria.getId())){
                portaria.getDesignados().add(designadoDAO.buscar((long)designados.get(i)[0]));
            }*/
            }
        }
        portaria.setReferencias(new ArrayList<>());
        if(!referencias.isEmpty()) {
            for (int i = 0; i < referencias.size(); i++) {
                if (referenciasList.get(i)[1].equals(portaria.getId())) {
                    portaria.getReferencias().add(referencias.get(i));
                }
            /*if(referencias.get(i)[1].equals(portaria.getId())){
                Referencia referencia = referenciaDAO.buscar(referencias.get(i)[0]);
                portaria.getReferencias().add(referenciaDAO.buscar((long)referencias.get(i)[0]));
            }*/
            }
        }
        portarias.add(portaria);
/*
        portariaDAO.abrirTransacao();
        portariaDAO.salvar(portaria);
        portariaDAO.commitarTransacao();*/
    }


    public static PortariaStatus retornaStatusPortaria(String dado){
        switch (dado){
            case "Proposta":
                return PortariaStatus.PROPOSTA;
            case "Ativa":
                return PortariaStatus.ATIVA;
            case "Cancelada":
                return PortariaStatus.CANCELADA;
            case "Expirada":
                return  PortariaStatus.EXPIRADA;
            default:
                return PortariaStatus.ATIVA;
        }
    }

    public static FuncaoDesig retornaFuncaoDesig(String dado){
        switch (dado){
            case "Presidente":
                return FuncaoDesig.PRESIDENTE;
            case "Coordenador":
                return FuncaoDesig.COORDENADOR;
            case "Vice Coordenador":
                return FuncaoDesig.VICECOORDENADOR;
            case "Membro":
                return  FuncaoDesig.MEMBRO;
            case "Suplente":
                return  FuncaoDesig.SUPLENTE;
            default:
                return FuncaoDesig.OUTRO;
        }
    }

    private static Cargo retornaCargoServid(String dado) {
        // deve ser atualizado quando houver valores na enum Cargo
        return null;
    }
}