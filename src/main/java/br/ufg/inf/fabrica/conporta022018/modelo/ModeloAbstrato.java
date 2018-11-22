package br.ufg.inf.fabrica.conporta022018.modelo;

import java.io.Serializable;
import java.util.Objects;
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
public abstract class ModeloAbstrato implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ModeloAbstrato)) {
      return false;
    }
    ModeloAbstrato that = (ModeloAbstrato) o;
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
