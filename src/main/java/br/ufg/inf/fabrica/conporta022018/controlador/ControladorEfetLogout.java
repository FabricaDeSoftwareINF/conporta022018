/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import com.google.gson.Gson;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;

import static br.ufg.inf.fabrica.conporta022018.modelo.RetornoEfetLogoutEnum.*;

public class ControladorEfetLogout {

    private Gson gson;

    public ControladorEfetLogout() {
        this.gson = new Gson();
    }

    public String efetuarLogout() {
        String resposta = gson.toJson(OK).replace("$$$", "");

        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/logout");
            dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
            FacesContext.getCurrentInstance().responseComplete();
        } catch (ServletException | IOException excEsperada) {
            resposta = gson.toJson(ERRO_EXECUCAO).replace(
                    "$$$",
                    String.format("\"excecao\": \"%s%n%s\"", excEsperada.getMessage(), excEsperada.getCause())
            );
        } catch (Exception exceptionNaoPlanejada) {
            resposta = gson.toJson(ERRO_INESPERADO).replace(
                    "$$$",
                    String.format("\"excecao\": \"%s%n%s\"",
                            exceptionNaoPlanejada.getMessage(), exceptionNaoPlanejada.getCause())
            );
        }

        return resposta;
    }

}
