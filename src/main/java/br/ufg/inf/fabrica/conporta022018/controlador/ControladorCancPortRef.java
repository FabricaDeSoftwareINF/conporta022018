/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaReferenciada;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;

import java.util.Iterator;
import java.util.List;

public class ControladorCancPortRef {
    private PortariaDAO portariaDAO = new PortariaDAO();

    public boolean cancelarPortariaReferenciada(String idPortaria) {
        Portaria portaria = portariaDAO.buscarUm("idPotaria = " + idPortaria);

        if (portaria.getStatus() != PortariaStatus.Ativa) {
            throw new UnsupportedOperationException("Operação não permitida para portaria não ativa.");
        }

        List<PortariaReferenciada> portariasReferenciadas = portaria.getPortariasReferenciadas();

        if (portariasReferenciadas.size() == 0) {
            throw new UnsupportedOperationException("Não existem portarias para cancelamento.");
        }

        List<PortariaReferenciada> portRefCancelamento = getPortRefCancelamento(portariasReferenciadas);
        Iterator<PortariaReferenciada> iterator = portRefCancelamento.iterator();

        PortariaReferenciada portariaReferenciada;
        Portaria portariaParaCancelamento;

        while (iterator.hasNext()) {
            portariaReferenciada = iterator.next();
            portariaParaCancelamento = portariaDAO.buscarUm("idPortaria = " + portariaReferenciada.getIdPortariaReferenciada());

            if (portariaParaCancelamento.getStatus() != PortariaStatus.Ativa) {
                throw new UnsupportedOperationException("Apenas portarias ativas podem ser canceladas");
            }

            portariaParaCancelamento.setStatus(PortariaStatus.Cancelada);
            portariaDAO.atualizar(portariaParaCancelamento);
        }


        return true;
    }

    public List<PortariaReferenciada> getPortRefCancelamento(List<PortariaReferenciada> portariasReferenciadas) {
        Iterator<PortariaReferenciada> iterator = portariasReferenciadas.iterator();
        PortariaReferenciada portariaReferenciada;

        while (iterator.hasNext()) {
            portariaReferenciada = iterator.next();

            if (portariaReferenciada.getEhCancelamento() != true) {
                portariasReferenciadas.remove(portariaReferenciada);
            }
        }

        return portariasReferenciadas;
    }

}
