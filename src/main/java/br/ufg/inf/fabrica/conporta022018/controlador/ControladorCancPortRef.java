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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ControladorCancPortRef {
    private PortariaDAO portariaDAO = new PortariaDAO();

    public boolean cancelarPortariaReferenciada(Long id) {
        Portaria portaria = portariaDAO.buscar(id);

        // Verifica se a portaria possue valor `null`.
        // Valor `null` siginifica que a portaria não existe na base de dados.
        if (portaria == null) {
            throw new UnsupportedOperationException("Portaria não existe na base de dados.");
        }

        // Verifica se a portaria tem status `ATIVA`.
        if (portaria.getStatus() != PortariaStatus.ATIVA) {
            throw new UnsupportedOperationException("Operação não permitida para portaria não ativa.");
        }

        List<Referencia> referencias = portaria.getReferencias();

        // Obtém as referências com indicativo de cancelamento.
        List<Referencia> portRefCancelamento = getPortRefCancelamento(referencias);

        // Verifica se existem referências com indicativo de cancelamento.
        if (portRefCancelamento == null || portRefCancelamento.size() == 0) {
            return true;
        }

        Iterator<Referencia> iterator = portRefCancelamento.iterator();

        // Variáveis utilizadas no loop.
        Referencia referencia;
        Portaria portariaReferenciada;
        Portaria portariaParaCancelamento;

        try {
            portariaDAO.abrirTransacao();

            while (iterator.hasNext()) {
                referencia = iterator.next();
                portariaReferenciada = referencia.getPortariaReferenciada();
                portariaParaCancelamento = portariaDAO.buscar(portariaReferenciada.getId());

                // Verifica se a portaria possue valor `null`.
                // Valor `null` siginifica que a portaria contida na referência não
                // existe na base de dados.
                if (portariaParaCancelamento == null) {
                    throw new UnsupportedOperationException("Apenas portarias existentes na base de dados podem ser canceladas.");
                }

                // Verifica se a portaria tem status `ATIVA`.
                if (portariaParaCancelamento.getStatus() != PortariaStatus.ATIVA) {
                    throw new UnsupportedOperationException("Apenas portarias ativas podem ser canceladas.");
                }

                // Altera o status da portaria e realiza a persistência na base de dados.
                portariaParaCancelamento.setStatus(PortariaStatus.CANCELADA);
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
        List<Referencia> referenciasParaCancelamento = new ArrayList<>();

        // Verifica se existem referências na portaria.
        if (referencias != null && referencias.size() > 0) {
            Iterator<Referencia> iterator = referencias.iterator();
            Referencia referencia;

            while (iterator.hasNext()) {
                referencia = iterator.next();

                // Adiciona a referência na lista local caso a mesma
                // possua indicativo de canselamento.
                if (referencia.isEhCancelamento() == true) {
                    referenciasParaCancelamento.add(referencia);
                }
            }
        }

        return referenciasParaCancelamento;
    }

}
