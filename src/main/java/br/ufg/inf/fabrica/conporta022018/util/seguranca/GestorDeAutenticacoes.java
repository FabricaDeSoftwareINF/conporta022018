/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.util.seguranca;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class GestorDeAutenticacoes implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        boolean isModerado = false;
        boolean isRestrito = false;
        boolean isRestritoMaster = false;
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_MOD")) {
                isModerado = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_REST")) {
                isRestrito = true;
                break;
            }
            else if (grantedAuthority.getAuthority().equals("ROLE_REST_MASTER")) {
                isRestritoMaster = true;
                break;
            }
        }
        
        String targetUrl = null;
        //Aqui indicar as páginas principais de cada perfil.
        if (isRestritoMaster) {
            targetUrl = "/homepage.html";
        } else if (isRestrito) {
            targetUrl = "/homepage.html";
        }
        else if (isModerado) {
            targetUrl = "/homepage.html";
        }
        
        Pessoa usuario = ((DetalheDoUsuario)authentication.getPrincipal()).getUsuario();
        
        targetUrl = "/index.xhtml";

        if (response.isCommitted()) {
            System.out.println( "Response has already been committed. Unable to redirect to "
                    + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);

        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        
    }

}