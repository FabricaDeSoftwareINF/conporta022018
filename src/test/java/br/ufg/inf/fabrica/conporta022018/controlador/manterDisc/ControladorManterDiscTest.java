/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador.manterDisc;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorDisc;
import br.ufg.inf.fabrica.conporta022018.modelo.*;

import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;


import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
 * Classe tem o intuito de exercitar o controlador referente aos casos de uso:
 *   1. ManterDisc;
 */
public class ControladorManterDiscTest {

    private static ControladorDisc controladorDisc = new ControladorDisc();
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeClass
    public static void casoTestImportacaoDiscente() throws IOException, ParseException {

        String CAMINHO_CSV = "ControladorManterDisc.csv";
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
    }

    @Test
    public void casoTestBuscaDeDiscente() throws Exception {

        // Verifica a consistência de um dos dados criados acima e testa a Busca de Discente
        Pessoa pessoa2 = controladorDisc.buscarDiscentePorCpf("123.123.123-12");
        Assert.assertEquals("123.123.123-12", pessoa2.getCpfPes());
    }


    @Test
    public void casoTestCriacaoDeDiscente() throws IOException, ParseException {

        Curso curso = new Curso();
        Date dtIniMatrCur = formato.parse("08/08/2018");

        // Verifica a criação de uma Matrícula/Discente a uma pessoa existente
        Assert.assertTrue(controladorDisc.criarDiscente("123.123.123-12", 69, dtIniMatrCur, curso));

    }

    @Test
    public void casoTestExclusaoDeDiscente() throws IOException, ParseException {

        // Verifica a exclusão lógica de um Discente
        Date dtFinMatrCur = formato.parse("14/12/2018");
        Assert.assertTrue(controladorDisc.excluirDiscente("123.123.123-12", dtFinMatrCur));
    }

    @Test
    public void casoTestAlteracaoDeDiscente() throws IOException, ParseException {

        Date dtIniMatrCur = formato.parse("08/12/2018");
        Date dtFinMatrCur = formato.parse("08/12/2020");
        Assert.assertTrue(controladorDisc.alterarDiscente("123.123.123-12", dtIniMatrCur, dtFinMatrCur));

    }

}
