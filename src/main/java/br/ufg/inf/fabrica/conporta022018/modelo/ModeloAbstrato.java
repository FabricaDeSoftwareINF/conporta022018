package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Classe abstrata dos modelos.
 *
 * @author Iago Bruno
 * @since 1.0
 */
@MappedSuperclass
public abstract class ModeloAbstrato {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


}
