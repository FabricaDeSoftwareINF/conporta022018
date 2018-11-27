/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.modelo.Referencia;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.Iterator;
import java.util.List;

public class ControladorCancPortRef {
    private PortariaDAO portariaDAO = new PortariaDAO();

    public boolean cancelarPortariaReferenciada(Portaria portaria) {
        if (portaria.getStatus() != PortariaStatus.Ativa) {
            throw new UnsupportedOperationException("Operação não permitida para portaria não ativa.");
        }

        List<Referencia> referencias = portaria.getReferencias();
        List<Referencia> portRefCancelamento = getPortRefCancelamento(referencias);

        if (portRefCancelamento.size() == 0) {
//            throw new UnsupportedOperationException("Não existem portarias para cancelamento.");
            return true;
        }

        Iterator<Referencia> iterator = portRefCancelamento.iterator();

        Referencia referencia;
        Portaria portariaReferenciada;
        Portaria portariaParaCancelamento;

        try {
            portariaDAO.abrirTransacao();

            while (iterator.hasNext()) {
                referencia = iterator.next();
                portariaReferenciada = referencia.getReferencia();
                portariaParaCancelamento = portariaDAO.buscar(portariaReferenciada.getId());

                if (portariaParaCancelamento == null) {
                    throw new UnsupportedOperationException("Apenas portarias existentes na base de dados podem ser canceladas.");
                }

                if (portariaParaCancelamento.getStatus() != PortariaStatus.Ativa) {
                    throw new UnsupportedOperationException("Apenas portarias ativas podem ser canceladas.");
                }

                portariaParaCancelamento.setStatus(PortariaStatus.Cancelada);
                portariaDAO.salvar(portariaParaCancelamento);
            }

            portariaDAO.commitarTransacao();
        } catch (Exception e) {
            portariaDAO.rollBackTransacao();
            return false;
        }

        return true;
    }

    public List<Referencia> getPortRefCancelamento(List<Referencia> referencias) {
        Iterator<Referencia> iterator = referencias.iterator();
        Referencia referencia;

        while (iterator.hasNext()) {
            referencia = iterator.next();

            if (referencia.isEhCancelamento() != true) {
                referencias.remove(referencia);
            }
        }

        return referencias;
    }

}
