package br.ufg.inf.fabrica.conporta022018.util.seguranca;

public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);

}
