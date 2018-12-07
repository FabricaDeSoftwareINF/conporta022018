/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.efetLogout;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorEfetLogout;
import org.junit.*;
import org.mockito.Mockito;

import static br.ufg.inf.fabrica.conporta022018.modelo.RetornoEfetLogoutEnum.*;

public class ControladorEfetLogoutTest extends Mockito {

    private static ControladorEfetLogout controladorEfetLogout;

    @Before
    public void casoTestPrepararExecucao() {
        controladorEfetLogout = mock(ControladorEfetLogout.class);
    }

    @Test
    public void casoTestRespostaServidorOK() {
        when(controladorEfetLogout.efetuarLogout()).thenReturn(OK.toString());

        String resposta = controladorEfetLogout.efetuarLogout();
        Assert.assertNotNull(resposta);
        Assert.assertTrue(resposta.contains(OK.getStatus()));
    }

    @Test
    public void casoTestRespostaServidorErroExecucao() {
        when(controladorEfetLogout.efetuarLogout()).thenReturn(ERRO_EXECUCAO.toString());

        String resposta = controladorEfetLogout.efetuarLogout();
        Assert.assertNotNull(resposta);
        Assert.assertTrue(resposta.contains(ERRO_EXECUCAO.getStatus()));
    }

    @Test
    public void casoTestRespostaServidorErroInesperado() {
        when(controladorEfetLogout.efetuarLogout()).thenReturn(ERRO_INESPERADO.toString());

        String resposta = controladorEfetLogout.efetuarLogout();
        Assert.assertNotNull(resposta);
        Assert.assertTrue(resposta.contains(ERRO_INESPERADO.getStatus()));
    }

}
