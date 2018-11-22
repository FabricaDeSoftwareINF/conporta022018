package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.*;

/**
 * @author David
 * @since 1.0
 */
@Entity
public class Mensagem extends ModeloAbstrato {
  
  private static final long serialVersionUID = 1L;
  
  private String titulo;
  private String descricao;

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
    public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
}
