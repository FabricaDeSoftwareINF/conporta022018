package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;

/**
 *
 * @author João Pedro Salgado
 */
public class Gestao {

    private Date dtInicio;
    private Date dtFim;
    private Date dtIniSubChefe;
    private Date dtFimSubChefe;
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

    public UnAdm getUnAdm() {
        return unAdm;
    }

    public void setFuncao(Tipo funcao) {
        this.funcao = funcao;
     }

    public Tipo getTipo() {
        return tipo;
    }
    

}


