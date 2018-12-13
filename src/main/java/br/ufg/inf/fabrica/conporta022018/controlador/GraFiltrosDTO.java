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
   private String siglaUndRec;
   private boolean incluirSubord;


    public Filtro(String siglaUndExp, Date dtInExped, Date dtFimExped, Date dtInVig, 
    Date dtFimVig, String cargoServ, boolean isDiscente, String siglaUndRec, boolean incluirSubord) {
        this.siglaUndExp = siglaUndExp;
        this.dtInExped = dtInExped;
        this.dtFimExped = dtFimExped;
        this.dtInVig = dtInVig;
        this.dtFimVig = dtFimVig;
        this.cargoServ = cargoServ;
        this.isDiscente = isDiscente;
        this.siglaUndRec = siglaUndRec;
        this.incluirSubord  = incluirSubord;
    }
}
