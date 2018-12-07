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
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static br.ufg.inf.fabrica.conporta022018.modelo.RetornoEfetLogoutEnum.*;

public class ControladorEfetLogoutTest extends Mockito {

    private static ControladorEfetLogout controladorEfetLogout;

    @Before
    public void casoTestPrepararExecucao() {
        controladorEfetLogout = mock(ControladorEfetLogout.class);
    }

    @Test
    public void casoTestDadosValidos() {
        String resposta = controladorEfetLogout.efetuarLogout();
        Assert.assertNull(resposta);
        if (resposta != null) Assert.assertTrue(resposta.contains(OK.getStatus()));
    }

    @Test(expected = ServletException.class)
    public void casoTestDadosExcecoes() {
        String resposta = controladorEfetLogout.efetuarLogout();
        Assert.assertNull(resposta);
        if (resposta != null) Assert.assertTrue(resposta.contains(ERRO_EXECUCAO.getStatus()));
    }

}
