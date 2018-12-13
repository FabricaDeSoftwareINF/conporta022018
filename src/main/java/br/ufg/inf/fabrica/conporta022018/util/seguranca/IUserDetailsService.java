package br.ufg.inf.fabrica.conporta022018.util.seguranca;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorConAcess;
import br.ufg.inf.fabrica.conporta022018.modelo.Perfil;
import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class IUserDetailsService implements UserDetailsService {

    private Pessoa usuario;
    private List<Autoridade> papeis;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ControladorConAcess controlador = new ControladorConAcess();

        this.usuario = controlador.buscarPorCPF(username);
        List<Perfil> perfilList = controlador.buscarPerfil(this.usuario);

        for (Perfil perfil : perfilList) {
            Autoridade autoridade = new Autoridade(perfil);
            papeis.add(autoridade);
        }

        return new org.springframework.security.core.userdetails.User(this.usuario.getCpfPes(),
                this.usuario.getSenhaUsu(), this.papeis);
    }

}
