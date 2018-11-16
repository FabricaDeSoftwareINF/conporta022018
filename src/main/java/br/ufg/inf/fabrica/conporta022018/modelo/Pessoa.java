package br.ufg.inf.fabrica.conporta022018.modelo;

public class Pessoa {

    private String nomePes;
    private String cpfPes;
    private String emailPes;
    private String senhaUsu;
    private Boolean ehUsuAtivo;
    private long ultimoLogin;

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

    public String getSenhaUsu() {
        return senhaUsu;
    }

    public void setSenhaUsu(String senhaUsu) {
        this.senhaUsu = senhaUsu;
    }

    public Boolean getEhUsuAtivo() {
        return ehUsuAtivo;
    }

    public void setEhUsuAtivo(Boolean ehUsuAtivo) {
        this.ehUsuAtivo = ehUsuAtivo;
    }

    public long getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(long ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

}
