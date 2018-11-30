/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PerfilDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;

import java.util.*;

public class ControladorConAcess {

    private final String JPQL_BUSCAR_USUARIO_POR_CPF = "select u Pessoa u where u.CPF = :cpfPes";
    private final String JPQL_BUSCAR_PERFIL = "select u Perfil u where u.TIPO_PERFIL = :nome";
    private Map<String, Object> map;

    public ControladorConAcess() {
        this.map = new HashMap<String, Object>();
    }

    public boolean checarLogin(String CPF, String senha) {

        return true;
    }

    public Pessoa buscarPorCPF(String CPF) {
        
        Pessoa usuario;
        PessoaDAO dao = new PessoaDAO();

        map.put("cpfPes", CPF);
        usuario = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF, map);

        return usuario;
    }

    public List<Permissao> buscarPermissao(Pessoa usuario) {

        boolean moderado = false;
        boolean restrito = false;
        boolean superRestrito = false;
        List<Permissao> permissoes = new ArrayList();
        PerfilDAO dao = new PerfilDAO();

        for (Matricula matricula : usuario.getDiscente()) {
            Date data = matricula.getDtFimMatrCur();

            if (data == null)
                moderado = true;
        }

        for (Lotacao lotacao : usuario.getServidor()) {
            Date data = lotacao.getDtFimLotServ();
            if (data == null)
                moderado = true;
        } 
    
        for (Gestao gestao : usuario.getGestao()) {
            if (gestao.getDtFimSubChefe() == null) {
                if (gestao.getTipo() == Tipo.COORDENADOR_ADM)
                    restrito = true;
                if (gestao.getTipo() == Tipo.CHEFIA || gestao.getTipo() == Tipo.SUBSTITUTO) {
                    superRestrito = true;
                    restrito = true;
                }
            }
        }

        List<Permissao> temporario = new ArrayList();
        if (moderado) {
            map.put("nome","ROLE_MODERADO");
            temporario = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map).getPermissoes();
            for (Permissao permissao : temporario)
                permissoes.add(permissao);
            map.remove("ROLE_MODERADO");
        }
        if (restrito) {
            map.put("nome","ROLE_RESTRITO");
            temporario = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map).getPermissoes();
            for (Permissao permissao : temporario)
                permissoes.add(permissao);
            map.remove("ROLE_RESTRITO");
        }
        if (superRestrito) {
            map.put("nome","ROLE_RESTRITO_ESPECIFICO");
            temporario = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map).getPermissoes();
            for (Permissao permissao : temporario)
                permissoes.add(permissao);
            map.remove("ROLE_RESTRITO_ESPECIFICO");
        }

        return permissoes;
    }

    public List<Perfil> buscarPerfil(Pessoa usuario) {

        List<Perfil> perfilList = new ArrayList<>();
        PerfilDAO dao = new PerfilDAO();

        for (Matricula matricula : usuario.getDiscente()) {
            Date data = matricula.getDtFimMatrCur();

            if (data == null) {
                map.put("nome","ROLE_MODERADO");
                perfilList.add(dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map));
            }
        }

        for (Lotacao lotacao : usuario.getServidor()) {
            Date data = lotacao.getDtFimLotServ();
            if (data == null) {
                map.put("nome","ROLE_MODERADO");
                perfilList.add(dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map));
            }
        }

        for (Gestao gestao : usuario.getGestao()) {
            if (gestao.getDtFimSubChefe() == null) {
                if (gestao.getTipo() == Tipo.COORDENADOR_ADM) {
                    map.put("nome","ROLE_RESTRITO");
                    perfilList.add(dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map));
                }
                if (gestao.getTipo() == Tipo.CHEFIA || gestao.getTipo() == Tipo.SUBSTITUTO) {
                    map.put("nome","ROLE_RESTRITO_ESPECIFICO");
                    perfilList.add(dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map));
                }
            }
        }

        return perfilList;
    }

}