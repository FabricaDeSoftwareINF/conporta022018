package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaReferenciadaDAO;
import br.ufg.inf.fabrica.conporta022018.modelo.Referencia;
import java.util.Iterator;
import java.util.List;

public class ControladorExclPort {
	
    private Portaria portaria;
    private PortariaDAO portariaDAO = new PortariaDAO();
    private PortariaReferenciadaDAO referenciaDAO = new PortariaReferenciadaDAO();

    public ControladorExclPort() {}

    public boolean validaStatusPortaria(PortariaStatus status) {
            return status == PortariaStatus.PROPOSTA;
    }
	
    public boolean excluirPortaria(Portaria portaria) throws Exception{
    	this.portaria = portaria;
        
        List<Referencia> listaReferenciadas = this.portaria.getReferencias();
        List<Designado> listaDesignados = this.portaria.getDesignados();
    	
    	try {
            portariaDAO.abrirTransacao();
            
            if(listaDesignados.size() > 0){
                this.portaria.setDesignados(null);
            }

            if(listaReferenciadas.size() > 0){
                for (Referencia referencia : listaReferenciadas) {
                    referencia.setReferencia(null);
                    referenciaDAO.remover(referencia);
                }
                this.portaria.setReferencias(listaReferenciadas);
            }
            
            this.portariaDAO.salvar(this.portaria);
            portariaDAO.commitarTransacao();
        } catch (Exception e) {
            portariaDAO.rollBackTransacao();
            return false;
        }

        return true;
    }
        	
  }

