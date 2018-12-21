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

/**
 * @author erick
 * @see org.mockito.Mockito
 * @see ControladorEfetLogout#efetuarLogout()
 *
 * Classe de testes "mockada" do ControladorEfetLogout.
 * Essa classe não representa fielmente uma classe de testes ideal para seu controlador.
 * Na verdade, o desenrolar do código a baixo nada mais é que um conjunto de situações hipotéticas
 * que representam as potenciais situações que podem vir a acontecer durante a execução de tal
 * controlador.
 * Isso se dá, conforme detalhado no javadoc do controlador, pelo fato de não haver ainda uma
 * implementação da camada de visão, o que impossibilita a execução do código do cotrolador.
 *
 * TODO: reimplementar casos de teste do logout (quando houver uma implementação JSF com Spring Security).
 */
public class ControladorEfetLogoutTest extends Mockito {

    private static ControladorEfetLogout controladorEfetLogout;

    @Before
    public void casoTestPrepararExecucao() {
        // Preparação do caso de teste
        // FIXME: remover mockagem do ControladorEfetLogout, é necessário ter um novo objeto aqui.
        controladorEfetLogout = mock(ControladorEfetLogout.class);
    }

    // FIXME: retirar a mockagem de casoTestRespostaServidorOK, em ControladorEfetLogoutTest
    @Test
    public void casoTestRespostaServidorOK() {
        // Mockagem do caso de teste cuja resposta é OK: bem-sucedido / logout efetuado.
        when(controladorEfetLogout.efetuarLogout()).thenReturn(OK.toString());

        String resposta = controladorEfetLogout.efetuarLogout();
        Assert.assertNotNull(resposta);
        Assert.assertTrue(resposta.contains(OK.getStatus()));

        log(
                "casoTestRespostaServidorOK",
                OK.toString().replace("$$$", ""),
                OK.toString().replace("$$$", ""),
                resposta.replace("$$$", "")
        );
    }

    // FIXME: retirar a mockagem de casoTestRespostaServidorErroExecucao, em ControladorEfetLogoutTest
    @Test
    public void casoTestRespostaServidorErroExecucao() {
        // Mockagem do caso de teste cuja resposta é um erro de execução, causado geralmente por ServletException.
        when(controladorEfetLogout.efetuarLogout()).thenReturn(ERRO_EXECUCAO.toString());

        String resposta = controladorEfetLogout.efetuarLogout();
        Assert.assertNotNull(resposta);
        Assert.assertTrue(resposta.contains(ERRO_EXECUCAO.getStatus()));

        log(
                "casoTestRespostaServidorErroExecucao",
                OK.toString().replace("$$$", ""),
                ERRO_EXECUCAO.toString().replace("$$$", ""),
                resposta.replace("$$$", "")
        );
    }

    // FIXME: retirar a mockagem de casoTestRespostaServidorErroInesperado, em ControladorEfetLogoutTest
    @Test
    public void casoTestRespostaServidorErroInesperado() {
        // Mockagem do caso de teste cuja resposta é um erro de execução inesperado, que pode ter inúmeras causas.
        when(controladorEfetLogout.efetuarLogout()).thenReturn(ERRO_INESPERADO.toString());

        String resposta = controladorEfetLogout.efetuarLogout();
        Assert.assertNotNull(resposta);
        Assert.assertTrue(resposta.contains(ERRO_INESPERADO.getStatus()));

        log(
                "casoTestRespostaServidorErroInesperado",
                OK.toString().replace("$$$", ""),
                ERRO_INESPERADO.toString().replace("$$$", ""),
                resposta.replace("$$$", "")
        );
    }

    // Relatório do status atual da execução dos testes para acompanhamento em tempo real
    private void log(
            final String cenario, final String esperadoCasUs,
            final String esperadoCen, final String obtidoExec
    ) {
        System.out.println("====================================================");
        System.out.printf("- Cenário de testes atual:\t[ %s ]\n", cenario);
        System.out.printf("- Resultado Esperado pelo caso de uso:\n\t[json] %s\n", esperadoCasUs);
        System.out.printf("- Resultado Esperado pelo cenário:\n\t[json] %s\n", esperadoCen);
        System.out.printf("- Resultado Obtido na execução:\n\t[json] %s\n", obtidoExec);
        System.out.println("====================================================");
    }

}
