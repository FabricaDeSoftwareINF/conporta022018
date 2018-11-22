package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Matricula extends ModeloAbstrato{

    private static final long serialVersionUID = 1343L;

    @JoinColumn
    @ManyToMany
    private Curso curso;

    @Column
    private Integer matrDiscCur;

    @Temporal(TemporalType.DATE)
    @Column
    private Date dtFimMatrCur;

    @Temporal(TemporalType.DATE)
    @Column
    private Date cdIniMatrCur;


    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

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
