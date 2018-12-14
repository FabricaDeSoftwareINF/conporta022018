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
import java.util.*;

public class ControladorDisc {

    private final String JPQL_BUSCAR_USUARIO_POR_CPF = "SELECT u FROM Pessoa u WHERE u.cpfPes = :CPF";
    private Map<String, Object> map = new HashMap<>();
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private Date defaultDate = new Date(01/01/2100);


    public Boolean criarDiscente(String cpfPessoa, int matrDiscCur, Date cdIniMatrCur, Curso curso) {

        Matricula discente = new Matricula();
        discente.setMatrDiscCur(matrDiscCur);
        discente.setCdIniMatrCur(cdIniMatrCur);
        discente.setCurso(curso);

        map.put("CPF", cpfPessoa);

        PessoaDAO pessoaDAO = new PessoaDAO();

        Pessoa pessoa = pessoaDAO.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF,map);

        pessoa.setDiscente(discente);

        pessoaDAO.abrirTransacao();
        pessoaDAO.salvar(pessoa);
        pessoaDAO.commitarTransacao();

        System.out.println(discente.getMatrDiscCur());

        return true;
    }

    public Boolean excluirDiscente(String cpfPessoa, Date dtFinalMatrCur) {

        PessoaDAO pessoaDAO = new PessoaDAO();
        map.put("CPF", cpfPessoa);

        List<Matricula> discente = new ArrayList<>();

        Pessoa pessoa = pessoaDAO.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF,map);
        discente = pessoa.getDiscente();

        Matricula matricula = discente.get(discente.size() - 1);
        matricula.setDtFimMatrCur(dtFinalMatrCur);

        pessoaDAO.salvar(pessoa);

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
