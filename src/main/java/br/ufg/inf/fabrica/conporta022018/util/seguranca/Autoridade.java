/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.util.seguranca;

import br.ufg.inf.fabrica.conporta022018.modelo.Perfil;
import org.springframework.security.core.GrantedAuthority;

public class Autoridade implements GrantedAuthority {

    private final Perfil perfil;

    public Autoridade(Perfil perfil) {
        this.perfil = perfil;
    }
    
    @Override
    public String getAuthority() {
        return perfil.getNome();
    }

}