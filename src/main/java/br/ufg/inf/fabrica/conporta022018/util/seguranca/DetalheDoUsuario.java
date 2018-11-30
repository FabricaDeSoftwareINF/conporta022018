/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.util.seguranca;

import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DetalheDoUsuario implements UserDetails {

    private Pessoa usuario;
    private List<Autoridade> papeis;

    public DetalheDoUsuario(Pessoa usuario){
        if(usuario==null){
            throw new InstantiationError("Required User in Instantiation of UserDetails in Security Library");
        }
        this.usuario = usuario;
        this.papeis = new ArrayList<>();

        //Buscar os papeis do usuário.
        //for (Papel papel : usuario.getPapeis()) {
            //Autoridade autoridade = new Autoridade(papel);
            //papeis.add(autoridade);
        //}
    }
    
    public Pessoa getUsuario(){
        return this.usuario;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.papeis;
    }

    @Override
    public String getPassword() {
        return this.usuario.getSenhaUsu();
    }

    @Override
    public String getUsername() {
        return usuario.getCpfPes();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return this.usuario.getTimeStampExclusaoLogica() != null;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}