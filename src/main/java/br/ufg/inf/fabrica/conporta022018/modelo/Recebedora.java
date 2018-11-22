package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;
import java.io.Serializable;

@Entity
@Table
public class Recebedora extends ModeloAbstrato {
     
    private static final long serialVersionUID = 97L; 
    
    @Temporal(TemporalType.DATE)
    @Column
    private Date dtCienciaReeb;
    
    @JoinColumn
    @ManyToMany
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
