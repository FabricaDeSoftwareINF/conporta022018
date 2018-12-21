package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table
public class Matricula extends ModeloAbstrato{

    private static final long serialVersionUID = 1343L;

    @ManyToOne(cascade= {CascadeType.ALL})
    @JoinColumn(name ="curso_id")
    private Curso curso;

    @Column
    private Integer matrDiscCur;

    @Temporal(TemporalType.DATE)
    @Column
    private Date dtFimMatrCur;

    @Temporal(TemporalType.DATE)
    @Column
    private Date dtIniMatrCur;


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

    public Date getDtIniMatrCur() {
        return dtIniMatrCur;
    }

    public void setDtIniMatrCur(Date cdIniMatrCur) {
        this.dtIniMatrCur = cdIniMatrCur;
    }
}
