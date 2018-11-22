package br.ufg.inf.fabrica.conporta022018.modelo;

/**
 * @author David
 * @since 1.0
 */
public class Mensagem extends ModeloAbstrato {

  private String titulo;

  private String descricao;

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
}
