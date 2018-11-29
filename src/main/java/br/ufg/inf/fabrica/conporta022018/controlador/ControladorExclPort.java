package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.modelo.Referencia;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Alunoinf_2 on 25/10/2018.
 */
public class ControladorExclPort {
	
    private Portaria portaria;
    private PortariaDAO portariaDAO = new PortariaDAO();

    public ControladorExclPort() {}

    public boolean validaStatusPortaria(PortariaStatus status) {
            return status == PortariaStatus.Proposta;
    }
	
    public boolean exclPorta(Portaria portaria) throws Exception{
    	this.portaria = portaria;
        
        List<Referencia> listaPortariasReferenciadas = this.portaria.getReferencias();
        List<Designado> listaDesignados = this.portaria.getDesignados();
    	
    	try {
            portariaDAO.abrirTransacao();
            
            if(listaDesignados.size() > 0){
                this.portaria.setDesignados(null);
            }

            if(listaPortariasReferenciadas.size() > 0){
                for (Referencia referencia : listaPortariasReferenciadas) {
                    referencia.setReferencia(null);
                }
                this.portaria.setReferencias(listaPortariasReferenciadas);
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

