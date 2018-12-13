package br.ufg.inf.fabrica.conporta022018.controlador;

import java.util.Date;


class GraFiltrosDTO {
   private String siglaUndExp;
   private Date dtInExped;
   private Date dtFimExped;
   private Date dtInVig;
   private Date dtFimVig;
   private String cargoServ;
   private boolean isDiscente;
   private boolean siglaUndRec;
   private boolean incluirSubord;


    public Filtro(siglaUndExp, dtInExped, dtFimExped, dtInVig, dtFimVig, 
                    cargoServ, isDiscente, siglaUndRec, incluirSubord) {
        this.siglaUndExp = siglaUndExp;
        this.dtInExped = dtInExped;
        this.dtFimExped = dtFimExped;
        this.dtInVig = dtInVig;
        this.dtFimVig = dtFimVig;
        this.cargoServ = cargoServ;
        this.isDiscente = isDiscente;
        this.siglaUndRec = siglaUndRec;
        this.incluirSubord  = incluirSubord
    }
}
