/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PerfilDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;

import java.text.SimpleDateFormat;
import java.util.*;

public class ControladorConAcess {

    private final String JPQL_BUSCAR_USUARIO_POR_CPF = "SELECT u FROM Pessoa u WHERE u.cpfPes = :CPF";
    private final String JPQL_BUSCAR_PERFIL = "SELECT u FROM Perfil u WHERE u.nome = :nome";
    private Map<String, Object> map;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private Date defaultDate = new Date(01/01/2100);

    public ControladorConAcess() {
        this.map = new HashMap<String, Object>();
    }

    public Pessoa buscarPorCPF(String CPF) {
        
        Pessoa usuario;
        PessoaDAO dao = new PessoaDAO();

        map.put("CPF", CPF);
        usuario = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF, map);

        return usuario;
    }

    public List<Perfil> buscarPerfil(Pessoa usuario) {

        List<Perfil> perfilList = new ArrayList<>();
        PerfilDAO dao = new PerfilDAO();

        if (!usuario.getDiscente().equals(null)) {
            for (Matricula matricula : usuario.getDiscente()) {
                Date data = matricula.getDtFimMatrCur();

                if (data == null || data == defaultDate) {
                    map.put("nome", "ROLE_MODERADO");
                    perfilList.add(dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map));
                }
            }
        }

        if (!usuario.getServidor().equals(null)) {
            for (Lotacao lotacao : usuario.getServidor()) {
                Date data = lotacao.getDtFimLotServ();
                if (data == null || data == defaultDate) {
                    map.put("nome", "ROLE_MODERADO");
                    perfilList.add(dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map));
                }
            }
        }

        if (!usuario.getGestao().equals(null)) {
            for (Gestao gestao : usuario.getGestao()) {
                if (gestao.getDtFimSubChefe() == null || gestao.getDtFimSubChefe() == defaultDate) {
                    if (gestao.getTipo() == Tipo.COORDENADOR_ADM) {
                        map.put("nome", "ROLE_RESTRITO");
                        perfilList.add(dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map));
                    }
                    if (gestao.getTipo() == Tipo.CHEFIA || gestao.getTipo() == Tipo.SUBSTITUTO) {
                        map.put("nome", "ROLE_RESTRITO_ESPECIFICO");
                        perfilList.add(dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_PERFIL, map));
                    }
                }
            }
        }

        return perfilList;
    }

}