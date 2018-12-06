package br.ufg.inf.fabrica.conporta022018.modelo;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
//import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
//import javax.validation.constraints.PastOrPresent;

/**
 * Modelo da Entidade de Lotação.
 *
 * @author Iago Bruno
 * @since 1.0
 */
@Entity
@Table
public class Lotacao extends ModeloAbstrato {

  private static final long serialVersionUID = 371L;

  @Column
  @Temporal(TemporalType.DATE)
  @NotNull
  @PastOrPresent
  private Date dtIniLotServ;

  @Column
  @Temporal(TemporalType.DATE)
  @NotNull
  @Future
  private Date dtFimLotServ;

  @Column
  @NotBlank
  private String descrCargoServ;

  @Column
  private Cargo cargoServ;

  @JoinColumn
  @ManyToOne
  private UndAdm undAdm;

  public Date getDtIniLotServ() {
    return dtIniLotServ;
  }

  public void setDtIniLotServ(Date dtIniLotServ) {
    this.dtIniLotServ = dtIniLotServ;
  }

  public Date getDtFimLotServ() {
    return dtFimLotServ;
  }

  public void setDtFimLotServ(Date dtFimLotServ) {
    this.dtFimLotServ = dtFimLotServ;
  }

  public String getDescrCargoServ() {
    return descrCargoServ;
  }

  public void setDescrCargoServ(String descrCargoServ) {
    this.descrCargoServ = descrCargoServ;
  }

  public Cargo getCargoServ() {
    return cargoServ;
  }

  public void setCargoServ(Cargo cargoServ) {
    this.cargoServ = cargoServ;
  }

  public UndAdm getUndAdm() {
    return undAdm;
  }

  public void setUndAdm(UndAdm undAdm) {
    this.undAdm = undAdm;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Lotacao)) {
      return false;
    }
    Lotacao lotacao = (Lotacao) o;
    return Objects.equals(getDtIniLotServ(), lotacao.getDtIniLotServ()) &&
        Objects.equals(getDtFimLotServ(), lotacao.getDtFimLotServ()) &&
        Objects.equals(getDescrCargoServ(), lotacao.getDescrCargoServ()) &&
        Objects.equals(getCargoServ(), lotacao.getCargoServ()) &&
        Objects.equals(getUndAdm(), lotacao.getUndAdm());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getDtIniLotServ(), getDtFimLotServ(), getDescrCargoServ(), getCargoServ(),
            getUndAdm());
  }
}
