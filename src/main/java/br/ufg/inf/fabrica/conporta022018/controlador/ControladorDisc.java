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
import com.google.gson.Gson;

import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControladorDisc {

    private final String JPQL_BUSCAR_USUARIO_POR_CPF = "SELECT u FROM Pessoa u WHERE u.cpfPes = :CPF";
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

        Pessoa pessoa = pessoaDAO.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF,map);

        pessoa.setDiscente(discente);

        pessoaDAO.abrirTransacao();
        pessoaDAO.salvar(pessoa);
        pessoaDAO.commitarTransacao();

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

        pessoaDAO.abrirTransacao();
        pessoaDAO.salvar(pessoa);
        pessoaDAO.commitarTransacao();

        return true;

    };

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
        Pessoa usuario;
        Gestao gestao;
        Lotacao lotacao;
        Matricula matricula;
        UndAdm undAdm;
        Curso curso;
        PessoaDAO pessoaDAO = new PessoaDAO();

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);


        for (int index = 0; index < dadosSoftware.size(); index++) {
            linha = dadosSoftware.get(index);

            //Definir as tabelas que serão populadas no Banco de Dados.
            if (linha.equals("pessoa")||linha.equals("perfil")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "pessoa":
                    usuario = new Pessoa();
                    gestao = new Gestao();
                    lotacao = new Lotacao();
                    matricula = new Matricula();
                    curso = new Curso();
                    undAdm = new UndAdm();
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    usuario.setNomePes(dados[0]);
                    usuario.setCpfPes(dados[1]);
                    usuario.setEmailPes(dados[2]);
                    usuario.setSenhaUsu(dados[3]);
                    usuario.setEhUsuAtivo(Boolean.parseBoolean(dados[4]));
                    if (dados[5].equals("null")) {
                        usuario.setGestao(gestao);
                    } else {
                        gestao.setdtInicio(formato.parse(dados[5]));
                        gestao.setdtFim(formato.parse(dados[6]));
                        gestao.setDtIniSubChefe(formato.parse(dados[7]));
                        gestao.setDtFimSubChefe(formato.parse(dados[8]));

                        undAdm.setNomeUnd(dados[9]);
                        undAdm.setSiglaUnAdm(dados[10]);
                        String tipo = dados[11];
                        if (tipo.equals("Curso"))
                            undAdm.setTipoUnd(TipoUnd.CURSO);
                        else if (tipo.equals("Unidade Acadêmica"))
                            undAdm.setTipoUnd(TipoUnd.UNIDADE_ACADEMICA);
                        else if (tipo.equals("Unidade Gestora"))
                            undAdm.setTipoUnd(TipoUnd.UNIDADE_GESTORA);
                        else if (tipo.equals("Conselho"))
                            undAdm.setTipoUnd(TipoUnd.CONSELHO);
                        else if (tipo.equals("Unidade Externa"))
                            undAdm.setTipoUnd(TipoUnd.UNIDADE_EXTERNA);
                        undAdm.setMinInat(Integer.parseInt(dados[12]));
                        undAdm.setUltPort(dados[13]);
                        undAdm.setAnoPort(Integer.parseInt(dados[14]));
                        undAdm.setUltNumExped(Integer.parseInt(dados[15]));
                        undAdm.setUltNumProp(Integer.parseInt(dados[16]));
                        gestao.setUnAdm(undAdm);

                        tipo = dados[17];
                        if (tipo.equals("chefia"))
                            gestao.setFuncao(Tipo.CHEFIA);
                        else if (tipo.equals("coordenador adm"))
                            gestao.setFuncao(Tipo.COORDENADOR_ADM);
                        else if (tipo.equals("substituto"))
                            gestao.setFuncao(Tipo.SUBSTITUTO);
                        usuario.setGestao(gestao);
                    }
                    lotacao = new Lotacao();
                    if (dados[18].equals("null")) {
                        usuario.setServidor(lotacao);
                    } else {
                        lotacao.setDtIniLotServ(formato.parse(dados[18]));
                        lotacao.setDtFimLotServ(formato.parse(dados[19]));
                        lotacao.setDescrCargoServ(dados[20]);
                        lotacao.setCargoServ(Cargo.valueOf(dados[21]));

                        undAdm.setNomeUnd(dados[22]);
                        undAdm.setSiglaUnAdm(dados[23]);
                        undAdm.setTipoUnd(TipoUnd.UNIDADE_ACADEMICA);
                        undAdm.setMinInat(Integer.parseInt(dados[25]));
                        undAdm.setUltPort(dados[26]);
                        undAdm.setAnoPort(Integer.parseInt(dados[27]));
                        undAdm.setUltNumExped(Integer.parseInt(dados[28]));
                        undAdm.setUltNumProp(Integer.parseInt(dados[29]));
                        matricula.setMatrDiscCur(Integer.parseInt(dados[30]));
                        matricula.setDtIniMatrCur(formato.parse(dados[31]));
                        usuario.setDiscente(matricula);

                        lotacao.setUndAdm(undAdm);
                        usuario.setServidor(lotacao);
                    }
                    pessoaDAO.abrirTransacao();
                    try {
                        pessoaDAO.salvar(usuario);
                        pessoaDAO.commitarTransacao();
                    } catch (Exception exc) {
                        pessoaDAO.rollBackTransacao();
                    }
                    break;

            }
        }

        return true;

    };

    public Boolean alterarDiscente(String cpfPessoa, Date dtFinalMatrCur, Date cdIniMatrCur) {

        PessoaDAO pessoaDAO = new PessoaDAO();
        map.put("CPF", cpfPessoa);

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
    }

    public Pessoa buscarDiscente(String cpfPessoa) {

        map.put("CPF", cpfPessoa);
        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa pessoa = pessoaDAO.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_USUARIO_POR_CPF,map);

        return pessoa;
    }
}
