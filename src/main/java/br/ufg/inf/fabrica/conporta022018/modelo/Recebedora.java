package br.ufg.inf.fabrica.conporta022018.modelo;
import java.util.Date;

public class Recebedora {
     
    private Date dtCienciaReeb;
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