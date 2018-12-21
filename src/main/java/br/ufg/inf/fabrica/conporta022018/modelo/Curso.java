package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.Entity;

@Entity
public class Curso extends ModeloAbstrato {
    
    private NivelCurso nivel;


    public NivelCurso getNivel() {
        return nivel;
    }

    public void setNivel(NivelCurso nivel) {
        this.nivel = nivel;
    }
}
