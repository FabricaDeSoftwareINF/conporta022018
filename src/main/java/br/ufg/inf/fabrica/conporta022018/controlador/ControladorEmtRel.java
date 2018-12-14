package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaExpedida;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaRecebida;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import java.util.Iterator;
import java.util.List;

public class ControladorEmtRel {

//   private PortariaDAO portariaDAO = new PortariaDAO();
//
//    public boolean cancelarPortarias(String idPortaria) {
//        Portaria portaria = portariaDAO.buscarPort("IdentificadorPortaria:" + idPortaria);
//
//
//         List<PortariaExpedida> portariasExpedidas = portaria.getPortariasExpedidas();
//         List<PortariaRecebida> portariasRecebidas = portaria.getPortariasRecebidas();
//
//
//         if (portariasExpedidas.equals(0)) {
//            throw new UnsupportedOperationException("Não existem portarias Expedidas");
//        }
//
//        if (portariasRecebidas.equals(0)) {
//            throw new UnsupportedOperationException("Não existem portarias Recebidas");
//        }
//
//        Iterator<PortariaRecebida> iterator = portariasRecebidas.iterator();
//        Iterator<PortariaExpedida> iterators = portariasExpedidas.iterator();
//
//        PortariaRecebida portariaRecebida;
//        PortariaExpedida portariaExpedida;
//        Portaria portarias;
//
//        while (iterator.hasNext()) {
//            portariaRecebida = iterator.next();
//            portariaExpedida = iterators.next();
//            String idPortariaRecebida;
//
//            portarias = portariaDAO.buscarPort("idPortariaRecebida = " + portariaRecebida.getIdPortariaRecebida());
//            portarias = portariaDAO.buscarPort("idPortariaRecebida = " + portariaExpedida.getIdPortariaExpedida());
//
//            if (portarias.getPortariasExpedidas() == null) {
//                throw new UnsupportedOperationException("Apenas portarias Expedidas podem gerar relatorio!");
//            }
//
//            if (portarias.getPortariasRecebidas() == null) {
//                throw new UnsupportedOperationException("Apenas portarias Recebidas podem gerar relatorio!");
//            }
//
//            portarias.setPortariasExpedidas(portariasExpedidas);
//            portarias.setPortariasRecebidas(portariasRecebidas);
//            portariaDAO.atualizar(portarias);
//        }
//
//        return true;
//    }
//
//    public List<PortariaExpedida> getPortExped(List<PortariaExpedida> portariaExpedidas) {
//        Iterator<PortariaExpedida> iterator = portariaExpedidas.iterator();
//        PortariaExpedida portariaExpedida;
//
//        while (iterator.hasNext()) {
//
//            portariaExpedida = iterator.next();
//
//            if (portariaExpedida.isIsCancelada()!= true) {
//                portariaExpedidas.remove(portariaExpedida);
//            }
//            }
//            return portariaExpedidas;
//    }
//
//        public List<PortariaRecebida> getPortRec(List<PortariaRecebida> portariaRecebidas) {
//        Iterator<PortariaRecebida> iterator = portariaRecebidas.iterator();
//        PortariaRecebida portariaRecebida;
//
//        while (iterator.hasNext()) {
//
//            portariaRecebida = iterator.next();
//
//            if (portariaRecebida.isIsCancelada()!= true) {
//                portariaRecebidas.remove(portariaRecebida);
//            }
//            }
//            return portariaRecebidas;
//    }
}
        



