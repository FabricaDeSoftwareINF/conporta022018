package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Matricula extends ModeloAbstrato{
    private static final long serialVersionUID = 1L;
    private Curso curso;
    private int matrDiscCur;
    private Date dtFimMatrCur;
    private Date cdIniMatrCur;
    
    public int getMatrDiscCur() {
        return matrDiscCur;
    }

    public void setMatrDiscCur(int matrDiscCur) {
        this.matrDiscCur = matrDiscCur;
    }

    public Date getDtFimMatrCur() {
        return dtFimMatrCur;
    }

    public void setDtFimMatrCur(Date dtFimMatrCur) {
        this.dtFimMatrCur = dtFimMatrCur;
    }

    public Date getCdIniMatrCur() {
        return cdIniMatrCur;
    }

    public void setCdIniMatrCur(Date cdIniMatrCur) {
        this.cdIniMatrCur = cdIniMatrCur;
    }
}
