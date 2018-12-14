/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControladorDisc {

    private final String JPQL_BUSCAR_USUARIO_POR_CPF = "SELECT u FROM Pessoa u WHERE u.cpfPes = :CPF";
    private final String JPQL_BUSCAR_USUARIO_POR_NOME = "SELECT u FROM Pessoa u WHERE u.nomePes = :NOME";
    private Map<String, Object> map = new HashMap<>();
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private Date defaultDate = new Date(01/01/2100);


    public Boolean criarDiscente(String cpfPessoa, int matrDiscCur, Date dtIniMatrCur, Curso curso) {

        Matricula discente = new Matricula();
        discente.setMatrDiscCur(matrDiscCur);
        discente.setDtIniMatrCur(dtIniMatrCur);
        discente.setCurso(curso);

        map.put("CPF", cpfPessoa);

        PessoaDAO pessoaDAO = new PessoaDAO();

        try {
            Pessoa pessoa = pessoaDAO.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF,map);

            pessoa.setDiscente(discente);

            pessoaDAO.abrirTransacao();
            pessoaDAO.salvar(pessoa);
            pessoaDAO.commitarTransacao();

            return true;
        } catch (Exception e) {
            System.out.println("Exceção capturada: " + e);
            return false;
        }
    }

    public Boolean excluirDiscente(String cpfPessoa, Date dtFinalMatrCur) {

        PessoaDAO pessoaDAO = new PessoaDAO();
        map.put("CPF", cpfPessoa);

        List<Matricula> discente;

        try {
            Pessoa pessoa = pessoaDAO.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF,map);

            if (pessoa.getDiscente() == null) {
                return null;
            }

            discente = pessoa.getDiscente();

            Matricula matricula = discente.get(discente.size() - 1);
            matricula.setDtFimMatrCur(dtFinalMatrCur);

            pessoaDAO.abrirTransacao();
            pessoaDAO.salvar(pessoa);
            pessoaDAO.commitarTransacao();

            return true;
        } catch (Exception e) {
            System.out.println("Exceção capturada: " + e);
            return false;
        }
    }

    public Boolean importarDiscente(String caminhoDoArquivo) throws IOException, ParseException {

        String CAMINHO_CSV = caminhoDoArquivo;
        String REGRA = ",";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        Date defaultDate = new Date(99/99/9999);
        String dados[];
        String linha;

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        Matricula matricula;
        Curso curso;

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);


        for (int index = 0; index < dadosSoftware.size(); index++) {
            linha = dadosSoftware.get(index);

            //Definir as tabelas que serão populadas no Banco de Dados.
            if (linha.equals("pessoa")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "pessoa":


                    try {
                        dados = extrator.getResultado(REGRA);

                        matricula = new Matricula();
                        curso = new Curso();
                        matricula.setCurso(curso);

                        criarDiscente(dados[0], Integer.parseInt(dados[1]), formato.parse(dados[2]), curso);

                    } catch (Exception e) {
                        System.out.println("Exceção capturada: " + e);
                    }
                    break;

            }
        }

        return true;

    };

    public Boolean alterarDiscente(String cpfPessoa, Date dtFinalMatrCur, Date cdIniMatrCur) {

        PessoaDAO pessoaDAO = new PessoaDAO();
        map.put("CPF", cpfPessoa);


        try {
            Pessoa pessoa = pessoaDAO.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF,map);

            List<Matricula> discente = new ArrayList<>();

            discente = pessoa.getDiscente();

            Matricula matricula = discente.get(discente.size() - 1);

            matricula.setDtFimMatrCur(dtFinalMatrCur);
            matricula.setDtFimMatrCur(dtFinalMatrCur);

            pessoaDAO.abrirTransacao();
            pessoaDAO.salvar(pessoa);
            pessoaDAO.commitarTransacao();

            return true;

        } catch (Exception e) {
            System.out.println("Exceção capturada: " + e);
            return false;

        }
    }

    public Pessoa buscarDiscentePorCpf(String cpfPessoa) throws Exception{

        map.put("CPF", cpfPessoa);
        PessoaDAO pessoaDAO = new PessoaDAO();

        try {
            Pessoa pessoa = pessoaDAO.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF, map);

            if (pessoa.getDiscente() == null) {
                return null;
            }

            return pessoa;

        } catch (Exception e) {
            System.out.println("Exceção capturada: " + e);
            return null;
        }
    }

}
