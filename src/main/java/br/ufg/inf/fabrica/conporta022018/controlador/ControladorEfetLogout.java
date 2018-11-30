/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;

import static br.ufg.inf.fabrica.conporta022018.modelo.RetornoEfetLogoutEnum.*;

public class ControladorEfetLogout {

    public String efetuarLogout() throws ServletException, IOException {
        String retorno = OK.getRetorno();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/logout");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        return retorno;
    }

}
