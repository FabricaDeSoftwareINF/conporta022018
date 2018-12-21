package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Entity
public class Gestao extends ModeloAbstrato {

    @Column
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dtInicio;
    @Column
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dtFim;
    @Column
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dtIniSubChefe;
    @Column
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dtFimSubChefe;

    @ManyToOne(cascade= {CascadeType.ALL})
    @JoinColumn(name ="unAdm_id")
    private UndAdm unAdm;

    private Tipo funcao;
    private Tipo tipo;

    public Date getdtInicio() {
        return dtInicio;
    }

    public void setdtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getdtFim() {
        return dtFim;
    }

    public void setdtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public void setDtIniSubChefe(Date dtIniSubChefe) {
        this.dtIniSubChefe = dtIniSubChefe;
    }

    public Date getDtIniSubChefe() {
        return dtFim;
    }

    public void setDtFimSubChefe(Date dtFimSubChefe) {
        this.dtFimSubChefe = dtFimSubChefe;
    }

    public Date getDtFimSubChefe() {
        return dtFim;
    }

    public void setUnAdm(UndAdm unAdm) {
        this.unAdm = unAdm;
    }

    public UndAdm getUnAdm() {
        return unAdm;
    }

    public void setFuncao(Tipo funcao) {
        this.funcao = funcao;
     }

    public Tipo getTipo() {
        return tipo;
    }
    

}




