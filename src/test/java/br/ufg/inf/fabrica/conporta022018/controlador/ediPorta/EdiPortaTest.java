package br.ufg.inf.fabrica.conporta022018.controlador.ediPorta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorEdiPorta;
import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.Recebedora;
import br.ufg.inf.fabrica.conporta022018.modelo.Referencia;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(Arquillian.class)
public class EdiPortaTest {
    ControladorEdiPorta controladorEdiPorta = new ControladorEdiPorta();

    @BeforeClass
    public static void casoTestPepararCenario() throws IOException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/ediPorta/EdiPortaDadosTest.csv";
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

    @Test
    public void ediPortaParametrosValidosTest() {
        boolean newWorld = controladorEdiPorta.validarCampos("assunto", new Date(), "resumo");
        Assert.assertTrue("Message", false);
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
        Assert.assertTrue(isSaved);
    }

}
