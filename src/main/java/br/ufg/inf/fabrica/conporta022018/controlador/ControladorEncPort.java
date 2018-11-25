/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;

public class ControladorEncPort {

    private PortariaDAO portariaDAO = new PortariaDAO();

    public boolean encPortariaCiencia(Portaria portaria){
        if ( portaria.getUnidadeExpedidora() == null && (portaria.getStatus() != PortariaStatus.ATIVA || portaria.getStatus() != PortariaStatus.CANCELADA) ){
            throw new UnsupportedOperationException("Portaria em questão não é válida.");
        }

        return true;

    ss}
}
