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

public class ControladorEfetLogoutTest extends Mockito {

    private static ControladorEfetLogout controladorEfetLogout;

    @Before
    public void casoTestPrepararExecucao() {
        //Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.
        controladorEfetLogout = new ControladorEfetLogout();
    }

    @Test
    public void testServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("username")).thenReturn("me");
        when(request.getParameter("password")).thenReturn("secret");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        controladorEfetLogout.efetuarLogout();

        verify(request, atLeast(1)).getParameter("username");
        writer.flush();
        Assert.assertTrue(stringWriter.toString().contains("My expected string"));
    }

}
