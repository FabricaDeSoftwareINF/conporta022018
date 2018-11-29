/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.modelo.Sessao;
import br.ufg.inf.fabrica.conporta022018.modelo.Permissao;

public void ControladorConAcess {

    private final String JPQL_BUSCAR_USUARIO_POR_CPF = "select u UndAdm u where u.CPF = :cpfPes";
    private Map<String, Object> map;

    public ControladorConAcess() {
        this.map = new HashMap<String, Object>();
    }

    public boolean checarLogin(String CPF, String senha) {

    }

    public Pessoa buscarPorCPF(String CPF) {
        
        Pessoa usuario;
        PessoaDAO dao = new PessoaDAO();

        map.put("cpfPes", CPF);
        usuario = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCA_USUARIO_POR_CPF, map);

        return usuario;
    }

    public List<Permissao> buscarPerfil(Pessoa usuario) {

        boolean moderado = restrito = superRestrito = false;
        List<Permissao> permissoes = new ArrayList();

        for (Date data : usuario.getDiscente.getDtFimMatrCur() && usuario.getDiscente() != null) {
            if (data == null)
                moderado = true;
        }

        for (Date data : usuario.getServidor.getDtFimLotServ() && usuario.getServidor() != null) {
            if (data == null)
                moderado = true;
        } 
    
        for (Gestao gestao : usuario.getGestao() && usuario.getGestao() != null) {
            if (gestao.getDtFimSubChefe() == null) {
                if (gestao.getFuncao() == Funcao.COORDENADOR_ADM)
                    restrito == true;
                if (gestao.getFuncao() == Funcao.CHEFIA || gestao.getFuncao() == Funcao.SUBSTITUTO)
                    superRestrito == true;
            }
        }

        //Criar a sessão para pegar as permissoes.

    }

}