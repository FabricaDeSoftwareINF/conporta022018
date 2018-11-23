/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.conporta022018.persistencia;

import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import java.util.HashMap;
import static javafx.scene.input.KeyCode.T;

/**
 *
 * @author Breno Gomes
 */
public class UndAdmDAO extends GenericoDAO<UndAdm> {
    
    private final int tempoMinimo = 1;
    private final int tempoMaximo = 60;       

    public UndAdmDAO() {
    }
    
    public void editarTimeOut(int timeOut) {
        UndAdm undAdm = new UndAdm();
        if(timeOut < tempoMinimo || timeOut > tempoMaximo){
            System.out.println("Esse intervalo de tempo não é permitido");
        }else{            
            undAdm.setMinInat(timeOut);
            this.salvar(undAdm);            
        }       
   }   
    
   public UndAdm pesquisarUndAdm(String siglaUnAdm){
       HashMap mapa = new HashMap();
       mapa.put("siglaUnAdm", siglaUnAdm);
       StringBuilder jpql = new StringBuilder();       
       jpql.append("select u from UndAdm u where u.siglaUndAdm = :siglaUnAdm");       
       return this.pesquisarUmJPQLCustomizada(jpql.toString(), mapa);
   }
    
    
    
   
    
   
    
}
