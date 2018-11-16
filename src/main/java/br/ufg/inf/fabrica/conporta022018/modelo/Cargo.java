package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TODO: Necessário implementar
 *
 * @author Iago Bruno
 * @since 1.0
 */
@Entity
@Table
public class Cargo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

}
