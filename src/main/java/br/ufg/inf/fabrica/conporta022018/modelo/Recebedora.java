package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Recebedora extends ModeloAbstrato {
     
    private static final long serialVersionUID = 97L; 
    
    @Temporal(TemporalType.DATE)
    @Column
    private Date dtCienciaReeb;
    
    @JoinColumn
    @ManyToOne
    private UndAdm unidadeRecebedora;

    public void setDtCienciaReeb(Date dtCienciaReeb){
        this.dtCienciaReeb = dtCienciaReeb;
    }  

    public Date getDtCienciaReeb(){
        return this.dtCienciaReeb;
    }

    public void setUnidadeRecebedora(UndAdm unidadeRecebedora){
        this.unidadeRecebedora = unidadeRecebedora;
    }  

    public UndAdm getUnidadeRecebedora(){
        return this.unidadeRecebedora;
    }
}
