/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Curso;
import br.ufg.inf.fabrica.conporta022018.modelo.Matricula;
import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ControladorDisc {

    private final String JPQL_BUSCAR_USUARIO_POR_CPF = "SELECT u FROM Pessoa u WHERE u.cpfPes = :CPF";
    private Map<String, Object> map = new HashMap<>();
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private Date defaultDate = new Date(01/01/2100);


    public Boolean criarDiscente(String cpfPessoa, int matrDiscCur, Date dtFimMatrCur, Date cdIniMatrCur, Curso curso) {

        Matricula discente = new Matricula();
        discente.setMatrDiscCur(matrDiscCur);
        discente.setCdIniMatrCur(cdIniMatrCur);
        discente.setDtFimMatrCur(dtFimMatrCur);
        discente.setCurso(curso);

        map.put("CPF", cpfPessoa);

        PessoaDAO pessoaDAO = new PessoaDAO();

        Pessoa pessoa = pessoaDAO.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF,map);

        pessoa.setDiscente(discente);

        pessoaDAO.abrirTransacao();
        pessoaDAO.salvar(pessoa);
        pessoaDAO.commitarTransacao();

        return true;
    }

    public Boolean excluirDiscente() {
        return true;
    };

    public Boolean importarDiscente() {
        return true;

    };

    public void alterarDiscente() {

    };

    public void buscarDiscente() {
        
    };

}
