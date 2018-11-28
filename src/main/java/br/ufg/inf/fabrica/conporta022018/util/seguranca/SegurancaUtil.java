/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.util.seguranca;

import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SegurancaUtil {

    public static Pessoa retornarUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof DetalheDoUsuario) {
            DetalheDoUsuario detalheDoUsuario = (DetalheDoUsuario) principal;
            return detalheDoUsuario.getUsuario();
        }
        return null;
    }

    public static String criptografar(String valor) {
        return new BCryptPasswordEncoder().encode(valor);
    }

}