/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador.manterDisc;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorDisc;
import br.ufg.inf.fabrica.conporta022018.modelo.*;

import org.junit.*;
import org.mockito.Mockito;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/*
 * Classe tem o intuito de exercitar o controlador referente aos casos de uso:
 *   1. ManterDisc;
 */
public class ControladorManterDiscTest extends Mockito {

    private static ControladorDisc controladorDisc = new ControladorDisc();
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");




    @BeforeClass
    public static void casoTestImportacaoDiscente() throws IOException, ParseException {

        // Verifica a importação e criação de Discentes a partir de um arquivo CSV
        Assert.assertTrue(controladorDisc.importarDiscente("ControladorManterDisc.csv"));

    }


    @Test
    public void casoTestBuscaDeDiscente() throws IOException {

        // Verifica a consistência de um dos dados criados acima e testa a Busca de Discente
        Pessoa pessoa2 = controladorDisc.buscarDiscente("123.123.123-12");
        Assert.assertEquals("123.123.123-12", pessoa2.getCpfPes());

    }

    @Test
    public void casoTestCriacaoDeDiscente() throws IOException, ParseException {

        Curso curso = new Curso();
        Date dtIniMatrCur = formato.parse("08/08/2018");

        // Verifica a criação de uma Matrícula/Discente a uma pessoa existente
        Assert.assertTrue(controladorDisc.criarDiscente("123.123.123-12", 69, dtIniMatrCur, curso));

    }

    @Test
    public void casoTestExclusaoDeDiscente() throws IOException, ParseException {

        // Verifica a exclusão lógica de um Discente
        Date dtFinMatrCur = formato.parse("14/12/2018");
        Assert.assertTrue(controladorDisc.excluirDiscente("123.123.123-12", dtFinMatrCur));
    }

    @Test
    public void casoTestAlteracaoDeDiscente() throws IOException, ParseException {

        Date dtIniMatrCur = formato.parse("08/12/2018");
        Date dtFinMatrCur = formato.parse("08/12/2020");
        Assert.assertTrue(controladorDisc.alterarDiscente("123.123.123-12", dtIniMatrCur, dtFinMatrCur));

    }

}
