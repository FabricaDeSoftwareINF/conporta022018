package br.ufg.inf.fabrica.conporta022018.controlador.ediPorta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorEdiPorta;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.*;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EdiPortaTest {
    ControladorEdiPorta controladorEdiPorta = new ControladorEdiPorta();
    private static Extrator extrator = new ExtratorCSV();
    private static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeClass
    public static void casoTestPrepararCenario() throws IOException, ParseException {
        final String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/ediPorta/EdiPortaDadosTest.csv";
        final String REGRA = ";";
        List<String> dadosSoftware = new ArrayList<>();
        extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {

            linha = dadosSoftware.get(index);

            if (linha.equals("pessoa") || linha.equals("portaria") || linha.equals("undAdm") || linha.equals("designado") || linha.equals("recebedora")){
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual){
                case "pessoa" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosPessoa(dados);
                    break;
                case "undAdm" :
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

    @Test
    public void ediPortaParametrosValidosTest() {
        boolean newWorld = controladorEdiPorta.validarCampos("assunto", new Date(), "resumo");
        Assert.assertTrue("Message", newWorld);
    }

    @Test
    public void buscarPortariaDesejadaTest() {
        Long idPortaria = null;
        Portaria portaria = controladorEdiPorta.editarPortaria(idPortaria);
        Assert.assertEquals(idPortaria, portaria.getId());
    }

    @Test
    public  void salvarPortariaSemReferenciaSemDesignadoTest() throws IOException {
        Portaria portaria = new Portaria();
        boolean isSaved = controladorEdiPorta.salvar(portaria.getAssunto(), portaria.getDtIniVig(),
                portaria.getDtFimVig(), portaria.getDtPublicDou(),portaria.getHorasDesig(), portaria.getResumo(),
                null, portaria.getDesignados(), portaria.getReferencias(), portaria.getUndRecebedora());
        Assert.assertTrue(isSaved);
    }

    @Test
    public void salvarPortariaComReferenciaSemDesignadoTest() throws IOException {
        Portaria portaria = new Portaria();
        boolean isSaved = controladorEdiPorta.salvar(portaria.getAssunto(), portaria.getDtIniVig(),
                portaria.getDtFimVig(), portaria.getDtPublicDou(),portaria.getHorasDesig(), portaria.getResumo(),
                null, portaria.getDesignados(), portaria.getReferencias(), portaria.getUndRecebedora());
        Assert.assertTrue(isSaved);
    }

    @Test
    public  void salvarPortariaSemReferenciaComDesignadoTest() throws IOException {
        Portaria portaria = new Portaria();
        boolean isSaved = controladorEdiPorta.salvar(portaria.getAssunto(), portaria.getDtIniVig(),
                portaria.getDtFimVig(), portaria.getDtPublicDou(),portaria.getHorasDesig(), portaria.getResumo(),
                null, portaria.getDesignados(), portaria.getReferencias(), portaria.getUndRecebedora());
        Assert.assertTrue(isSaved);
    }

    @Test
    public void salvarPortariaComReferenciaComDesignadoTest() throws IOException {
        Portaria portaria = new Portaria();
        boolean isSaved = controladorEdiPorta.salvar(portaria.getAssunto(), portaria.getDtIniVig(),
                portaria.getDtFimVig(), portaria.getDtPublicDou(),portaria.getHorasDesig(), portaria.getResumo(),
                null, portaria.getDesignados(), portaria.getReferencias(), portaria.getUndRecebedora());
        Assert.assertTrue(isSaved);
    }

    @Test
    public void salvarPortariaDadosInvalidos() throws IOException {
        Portaria portaria = new Portaria();
        boolean isSaved = controladorEdiPorta.salvar(portaria.getAssunto(), portaria.getDtIniVig(),
                portaria.getDtFimVig(), portaria.getDtPublicDou(),portaria.getHorasDesig(), portaria.getResumo(),
                null, portaria.getDesignados(), portaria.getReferencias(), portaria.getUndRecebedora());
        Assert.assertTrue(isSaved);
    }

    @Test
    public void ediPortaDesignadosInvalidosTest() throws IOException {
        Portaria portaria = new Portaria();
        boolean isSaved = controladorEdiPorta.salvar(portaria.getAssunto(), portaria.getDtIniVig(),
                portaria.getDtFimVig(), portaria.getDtPublicDou(),portaria.getHorasDesig(), portaria.getResumo(),
                null, portaria.getDesignados(), portaria.getReferencias(), portaria.getUndRecebedora());
        Assert.assertFalse(isSaved);
    }

    private static void trataDadosPessoa(String dados[]){
        // Instancia os objetos
        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa pessoa = new Pessoa();

        // Preenche o objeto
        pessoa.setId( Long.parseLong(dados[0]) );
        pessoa.setNomePes(dados[1]);
        pessoa.setCpfPes(dados[2]);
        pessoa.setEmailPes(dados[3]);
        pessoa.setSenhaUsu(dados[4]);
        if(dados[5].equals("true")){
            pessoa.setEhUsuAtivo(true);
        }else{
            pessoa.setEhUsuAtivo(false);
        }

        // Salva o objeto no banco
        pessoaDAO.salvar(pessoa);
    }

    private static void trataDadosUndAdm(String dados[]){
        // Instancia os objetos
        UndAdmDAO undAdmDAO = new UndAdmDAO();
        UndAdm undAdm = new UndAdm();

        // Preenche o objeto
        undAdm.setId( Long.parseLong(dados[0]) );
        undAdm.setSiglaUnAdm(dados[1]);
        undAdm.setMinInat( Integer.parseInt(dados[2]) );
        undAdm.setNomeUnd(dados[3]);
        undAdm.setTipoUnd(1);
        undAdm.setUltPort(dados[5]);
        undAdm.setAnoPort( Integer.parseInt(dados[6]) );
        undAdm.setUltNumExped( Integer.parseInt(dados[7]) );
        undAdm.setUltNumProp( Integer.parseInt(dados[8]) );

        // Salva o objeto no banco
        undAdmDAO.salvar(undAdm);
    }

    private static void trataDadosRecebedora(String dados[]) throws ParseException {
        // Instancia os objetos
        RecebedoraDAO recebedoraDAO = new RecebedoraDAO();
        Recebedora recebedora = new Recebedora();

        // Preenche o objeto
        recebedora.setId( Long.parseLong(dados[0]) );

        if(!dados[1].isEmpty()){
            Date dtCienciaReeb = formatter.parse(dados[1]);
            recebedora.setDtCienciaReeb(dtCienciaReeb);
        }

        UndAdmDAO undAdmDAO = new UndAdmDAO();
        UndAdm undRecebedora = undAdmDAO.buscar( Long.parseLong(dados[2]) );
        recebedora.setUnidadeRecebedora(undRecebedora);

        // Salva o objeto no banco
        recebedoraDAO.salvar(recebedora);
    }

    private static void trataDadosDesignado(String dados[]) throws ParseException{
        // Instancia os objetos
        DesignadoDAO designadoDAO = new DesignadoDAO();
        Designado designado = new Designado();

        // Preenche o objeto
        designado.setId( Long.parseLong(dados[0]) );

        if(!dados[1].isEmpty()){
            Date dtCienciaDesig = formatter.parse(dados[1]);
            designado.setDtCienciaDesig(dtCienciaDesig);
        }

        designado.setDescrFuncDesig(dados[2]);

        if(!dados[3].isEmpty()){
            designado.setHorasDefFuncDesig( Integer.parseInt(dados[3]) );
        }

        if(!dados[4].isEmpty()){
            designado.setHorasExecFuncDesig( Integer.parseInt(dados[4]) );
        }

        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa pessoa = pessoaDAO.buscar( Long.parseLong(dados[5]) );
        designado.setPessoa(pessoa);

        designado.setFuncaoDesig(FuncaoDesig.valueOf(dados[6]));

        // Salva o objeto no banco
        designadoDAO.salvar(designado);
    }

    private static void trataDadosPortaria(String dados[]) throws ParseException {
        // Instancia os objetos
        PortariaDAO portariaDAO = new PortariaDAO();
        Portaria portaria = new Portaria();

        // Preenche o objeto
        portaria.setId( Long.parseLong(dados[0]) );
        portaria.setAnoId( Integer.parseInt(dados[1]) );
        portaria.setSeqId( Integer.parseInt(dados[2]) );
        portaria.setAssunto(dados[3]);

        if(!dados[4].isEmpty()){
            Date dtExped = formatter.parse(dados[4]);
            portaria.setDtExped(dtExped);
        }

        Date dtIniVig = formatter.parse(dados[5]);
        portaria.setDtIniVig(dtIniVig);

        if(!dados[6].isEmpty()){
            Date dtFimVig = formatter.parse(dados[6]);
            portaria.setDtFimVig(dtFimVig);
        }

        if(!dados[7].isEmpty()){
            Date dtPublicDou = formatter.parse(dados[7]);
            portaria.setDtPublicDou(dtPublicDou);
        }

        if(!dados[8].isEmpty()){
            portaria.setHorasDesig( Integer.parseInt(dados[8]) );
        }

        portaria.setResumo(dados[9]);

        portaria.setTextoCompleto(dados[10]);

        // Verifica se existe arquivo pdf
        if(!dados[11].isEmpty()){
            // Pega o path do projeto
            String dir = System.getProperty("user.dir");
            // Cria o arquivo
            File arquivo = new File(dir+"/src/test/resources/"+dados[11]);
            if(arquivo.exists()){
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

        if(!dados[14].isEmpty()){
            PessoaDAO pessoaDAO = new PessoaDAO();
            Pessoa expeditor = pessoaDAO.buscar( Long.parseLong(dados[14]) );
            portaria.setExpedidor(expeditor);
        }

        if(!dados[15].isEmpty()){
            UndAdmDAO undAdmDAO = new UndAdmDAO();
            UndAdm undAdmExpedidora = undAdmDAO.buscar( Long.parseLong(dados[15]) );
            portaria.setUnidadeExpedidora(undAdmExpedidora);
        }

        if(!dados[16].isEmpty()){
            extrator.setTexto(dados[16]);
            String recebedorasId[] = extrator.getResultado("-");
            List<Recebedora> listaRecebedoras = new ArrayList<>();
            RecebedoraDAO recebedoraDAO = new RecebedoraDAO();

            for(int i = 0; i < recebedorasId.length; i++){
                Recebedora recebedora = recebedoraDAO.buscar( Long.parseLong(recebedorasId[i]) );
                listaRecebedoras.add(recebedora);
            }

            portaria.setUndRecebedora(listaRecebedoras);
        }

        if(!dados[17].isEmpty()){
            extrator.setTexto(dados[17]);
            String designadosId[] = extrator.getResultado("-");
            List<Designado> listaDesignados = new ArrayList<>();
            DesignadoDAO designadoDAO = new DesignadoDAO();

            for(int i = 0; i < designadosId.length; i++){
                Designado designado = designadoDAO.buscar( Long.parseLong(designadosId[i]) );
                listaDesignados.add(designado);
            }

            portaria.setDesignados(listaDesignados);
        }

        // Salva o objeto no banco
        portariaDAO.salvar(portaria);
    }

}
