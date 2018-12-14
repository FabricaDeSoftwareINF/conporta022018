package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pessoa extends ModeloAbstrato {

    private static final long serialVersionUID = 1L;

    private String nomePes;
    private String cpfPes;
    private String emailPes;
    private String senhaUsu;
    private Boolean ehUsuAtivo;
    @ManyToMany(cascade= {CascadeType.ALL})
    @JoinColumn(name ="gestao_id")
    private List<Gestao> gestao = new ArrayList<>();
    @ManyToMany(cascade= {CascadeType.ALL})
    @JoinColumn(name ="servidor_id")
    private List<Lotacao> servidor = new ArrayList<>();
    @ManyToMany(cascade= {CascadeType.ALL})
    @JoinColumn(name ="discente_id")
    private List<Matricula> discente = new ArrayList<>();

    public Pessoa(){
        this.gestao = new ArrayList<>();
        this.servidor = new ArrayList<>();
        this.discente = new ArrayList<>();
    }

    public String getNomePes() {
        return nomePes;
    }

    public void setNomePes(String nomePes) {
        this.nomePes = nomePes;
    }

    public String getCpfPes() {
        return cpfPes;
    }

    public void setCpfPes(String cpfPes) {
        this.cpfPes = cpfPes;
    }

    public String getEmailPes() {
        return emailPes;
    }

    public void setEmailPes(String emailPes) {
        this.emailPes = emailPes;
    }

    public void setSenhaUsu(String senhaUsu) {
        this.senhaUsu = senhaUsu;
    }

    public Boolean getEhUsuAtivo() {
        return ehUsuAtivo;
    }

    public Boolean validarSenha(String credential) {
        if(!credential.isEmpty() && credential.length() >= 6)
            return  true;
        return false;
    }

    public String getSenhaUsu() {
        return this.senhaUsu;
    }

    public void setEhUsuAtivo(Boolean ehUsuAtivo) {
        this.ehUsuAtivo = ehUsuAtivo;
    }

    public Boolean isUsuAtivo() {
        return this.isUsuAtivo();
    }

    public void setGestao(Gestao gestor) {
        this.gestao.add(gestor);
    }

    public List<Gestao> getGestao() {
        return this.gestao;
    }

    public void setServidor(Lotacao servidor) {
        this.servidor.add(servidor);
    }

    public List<Lotacao> getServidor() {
        return this.servidor;
    }

    public void setDiscente(Matricula discente) {
        this.discente.add(discente);
    }

    public List<Matricula> getDiscente() {
        return this.discente;
    }

}