package br.ufg.inf.fabrica.conporta022018.controlador;
package br.ufg.inf.fabrica.conporta022018.modelo;


/**
 * Created by Alunoinf_2 on 25/10/2018.
 */
public class ControladorExclPort {
	
	private Portaria portaria;
	
	public ControladorExclPort() {}
	
	public boolean validaStatusPortaria(PortariaStatus status) {
		return status == PortariaStatus.PROPOSTA;
	}
	
    public boolean exclPorta(Portaria portaria) throws Exception{
    	this.portaria = portaria;
    	
    	try {
    		
    	}catch(Exception ex) {
    		
    	}
    	
    }
}
