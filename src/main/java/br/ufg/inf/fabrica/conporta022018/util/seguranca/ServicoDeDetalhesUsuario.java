/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.util.seguranca;

import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ServicoDeDetalhesUsuario implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            return null;
        }

        //Chamar o controlador.
        //UsuarioController controller = new UsuarioController();
        //Usuario usuario = controller.buscarPorCPF(username);
        if(usuario!=null){
            return new DetalheDoUsuario(usuario);
        }
        return null;
    }

}