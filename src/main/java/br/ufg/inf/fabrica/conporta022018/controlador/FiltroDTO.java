
package br.ufg.inf.fabrica.conporta022018.controlador;

import java.util.Date;


class FiltroDTO {
   private Long codigoDesignado;
   private Long codigoUnidadeAdm;
   private Integer anoPortaria;
   private Date inicioVigencia;
   private Date fimVigencia;

    public FiltroDTO(Long codigoDesignado, Long codigoUnidadeAdm, Integer anoPortaria, Date inicioVigencia, Date fimVigencia) {
        this.codigoDesignado = codigoDesignado;
        this.codigoUnidadeAdm = codigoUnidadeAdm;
        this.anoPortaria = anoPortaria;
        this.inicioVigencia = inicioVigencia;
        this.fimVigencia = fimVigencia;
    }
   
    
    
    
    
}
