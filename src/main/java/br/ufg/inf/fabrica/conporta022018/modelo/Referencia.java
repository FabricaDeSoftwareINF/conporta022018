package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Referencia extends ModeloAbstrato {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private Portaria Referencia;
    private boolean ehCancelamento;
      

    /**
     * @return Portaria return the Referencia
     */
    public Portaria getReferencia() {
        return Referencia;
    }

    /**
     * @param Referencia the Referencia to set
     */
    public void setReferencia(Portaria Referencia) {
        this.Referencia = Referencia;
    }
    /**
     * @return boolean return the ehCancelamento
     */
    public boolean isEhCancelamento() {
        return ehCancelamento;
    }

    /**
     * @param ehCancelamento the ehCancelamento to set
     */
    public void setEhCancelamento(boolean ehCancelamento) {
        this.ehCancelamento = ehCancelamento;
    }

}
