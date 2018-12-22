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

/**
 * @author erick
 *
 * Controlador de finalização de sessão.
 */
public class ControladorEfetLogout {

    /**
     * @see br.ufg.inf.fabrica.conporta022018.modelo.RetornoEfetLogoutEnum
     * @return mensagem de retorno do servidor em formato JSON.
     *
     * O método efetuarLogout() nada mais é que uma requisição via Servlet para o endpoint "/logout" do
     * Spring Security; que se encontra acessível a partir do contexto da camada de visão (FacesContext).
     * O método não possui parâmetros (como idSessao ou idUsuario, por exemplo), pois o gerenciamento
     * dessas informações não cabe a nenhuma entidade do sistema (conporta022018) desenvolvido aqui, mas
     * sim do próprio Spring Security.
     * Portanto, tais informações são mantidas ou obtidas pelo controlador interno da biblioteca de
     * sessão do framework, cabendo a ele matar a sessão do usuário solicitante, por meio das informações
     * de disponíveis na camada de visão do software.
     * Camada, essa, não presente no projeto até a data da implementação deste método, 07/12/2018.
     * Ou seja, o bom funcionamento deste método está terminantemente condicionado à implementação
     * (futura à data supracitada) de uma camada de visão, por meio de JSF.
     */
    public String efetuarLogout() {
        String resposta = OK.toString().replace("$$$", "");

        try {
            // Sem uma camada de visão, provida pelo JSF, o "FacesContext", a abixo, não é atingível.
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/logout");
            dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
            FacesContext.getCurrentInstance().responseComplete();
        } catch (ServletException | IOException excEsperada) {
            // Tratamento das exceções previsíveis para dar feedback ao usuário.
            resposta = ERRO_EXECUCAO.toString().replace(
                    "$$$",
                    String.format(", \"excecao\": \"%s%n%s\"", excEsperada.getMessage(), excEsperada.getCause())
            );
        } catch (Exception exceptionNaoPlanejada) {
            // Tratamento das possíveis exceções não previstas anteriormente.
            resposta = ERRO_INESPERADO.toString().replace(
                    "$$$",
                    String.format(", \"excecao\": \"%s%n%s\"",
                            exceptionNaoPlanejada.getMessage(), exceptionNaoPlanejada.getCause())
            );
        }

        // O retorno do método se dá em formado de String JSON.
        return resposta;
    }

}
