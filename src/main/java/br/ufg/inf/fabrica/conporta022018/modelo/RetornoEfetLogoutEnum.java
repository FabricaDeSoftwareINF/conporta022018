/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.modelo;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorEfetLogout;

/**
 * @author erick
 * @see ControladorEfetLogout#efetuarLogout()
 *
 * Padronizador de Mensagens de Erro especificamente para o ControladorEfetLogout.
 */
public enum RetornoEfetLogoutEnum {

    OK("Sessão encerrada com sucesso!", "OK"),
    ERRO_EXECUCAO("Houve um erro de execução e a sessão não pode ser terminada.", "ERRO"),
    ERRO_INESPERADO("Houve um erro inesperado e não foi possível encerrar a sessão.", "ERRO");

    private final String retorno;
    private final String status;

    RetornoEfetLogoutEnum(final String retorno, final String status) {
        this.retorno = retorno;
        this.status = status;
    }

    /**
     * @return a mensagem de retorno ou erro a ser usada pelo sistema.
     */
    public String getRetorno() {
        return retorno;
    }

    /**
     * @return o status atual da resposta, que identifica seu tipo (ERRO ou OK).
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return retorno formatado para JSON, com espaço para adição de mais campos.
     */
    @Override
    public String toString() {
        /*
         A parte da string que contem $$$ é usada no método replace() para adicionar campos extras ao JSON.
         */
        return String.format(
            "{ \"status\": \"%s\", \"mensagem\": \"%s\" $$$ }",
            this.getStatus(),
            this.getRetorno()
        );
    }
}