package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

/**
 * Modelo da Entidade de Lotação.
 *
 * @author Iago Bruno
 * @since 1.0
 */
@Entity
@Table
public class Lotacao {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

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

  @JoinColumn
  @ManyToOne
  private Cargo cargoServ;

  @JoinColumn
  @ManyToOne
  private UndAdm undAdm;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Lotacao lotacao = (Lotacao) o;
    return Objects.equals(id, lotacao.id) &&
        Objects.equals(dtIniLotServ, lotacao.dtIniLotServ) &&
        Objects.equals(dtFimLotServ, lotacao.dtFimLotServ) &&
        Objects.equals(descrCargoServ, lotacao.descrCargoServ) &&
        Objects.equals(cargoServ, lotacao.cargoServ) &&
        Objects.equals(undAdm, lotacao.undAdm);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, dtIniLotServ, dtFimLotServ, descrCargoServ, cargoServ, undAdm);
  }
}
