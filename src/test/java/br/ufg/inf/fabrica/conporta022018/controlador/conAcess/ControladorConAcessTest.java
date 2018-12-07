/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador.conAcess;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorConAcess;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.*;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorConAcessTest extends Mockito {

    /*
    * PARA O CENÁRIO DE CONACESS E IDSOFTWARE SERÁ REALIZADO TESTES NOS MÉTODOS DO CONTROLADOR
    * QUE O FRAMEWORK USA.
    *
    *
    * Para o cenário de login e controle de acesso será feito os testes atraves de mocki,
    * pois não possui um servlet dessa forma não possui escuta da url para obter o retorno.
    *
    * Os testes aqui realizado apresenta o conceito para os casos de uso
    *     - ConAcess
    *     - IdSoft
     */
    private static ControladorConAcess controlador;


    /*
     * Preparação do ambiente para teste.
     * População do banco de Dados para atendam os pré-requisitos do caso de uso.
     */
    @BeforeClass
    public static void casoTestPepararCenario() throws IOException, ParseException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/consAcess/ControladorConAcess.csv";
        String REGRA = ",";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
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
            if (linha.equals("pessoa")) {
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
                    SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy");
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    usuario.setNomePes(dados[0]);
                    usuario.setCpfPes(dados[1]);
                    usuario.setEmailPes(dados[2]);
                    usuario.setSenhaUsu(dados[3]);
                    usuario.setEhUsuAtivo(Boolean.parseBoolean(dados[4]));
                    if (dados[5].equals(null)) {
                        usuario.setGestao(gestao);
                    } else {
                        gestao.setdtInicio(formato.parse(dados[5]));
                        gestao.setdtFim(dados[6].equals(null) ? null : formato.parse(dados[6]));
                        gestao.setDtIniSubChefe(dados[7].equals(null) ? null : formato.parse(dados[7]));
                        gestao.setDtFimSubChefe(dados[8].equals(null) ? null : formato.parse(dados[8]));

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
                    if (dados[18].equals(null)) {
                        usuario.setServidor(lotacao);
                    } else {
                        lotacao.setDtIniLotServ(formato.parse(dados[18]));
                        lotacao.setDtFimLotServ(dados[19].equals(null) ? null : formato.parse(dados[19]));
                        lotacao.setDescrCargoServ(dados[20]);
                        lotacao.setCargoServ(Cargo.valueOf(dados[21]));

                        undAdm.setNomeUnd(dados[22]);
                        undAdm.setSiglaUnAdm(dados[23]);
                        TipoUnd tipo = TipoUnd.valueOf(dados[24]);
                        undAdm.setTipoUnd(tipo);
                        undAdm.setMinInat(Integer.parseInt(dados[25]));
                        undAdm.setUltPort(dados[26]);
                        undAdm.setAnoPort(Integer.parseInt(dados[27]));
                        undAdm.setUltNumExped(Integer.parseInt(dados[28]));
                        undAdm.setUltNumProp(Integer.parseInt(dados[29]));

                        lotacao.setUndAdm(undAdm);
                        usuario.setServidor(lotacao);
                    }
                    if (dados[30].equals(null)) {
                        usuario.setDiscente(matricula);
                    } else {

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
    public void casoTestDadosValidos() throws Exception {

        Pessoa usuario;

        //Testa um usuário que existe no banco de dados.
        usuario = controlador.buscarPorCPF("123.123.123-12");

        //Verifica se o usuário é o mesmo que está atrelado a esse cpf na base de dados.
        Assert.assertEquals("José Paulo Souza", usuario.getNomePes());
        Assert.assertEquals("123.123.123-12", usuario.getCpfPes());
        Assert.assertEquals("jose@example.com", usuario.getEmailPes());

        //Testa outro usuário que existe na base de dados.
        usuario = controlador.buscarPorCPF("789.789.789-89");

        //Verifica se o usuário é o mesmo que está atrelado a esse cpf na base de dados.
        Assert.assertEquals("Fernanda Paula de Souza", usuario.getNomePes());
        Assert.assertEquals("789.789.789-89", usuario.getCpfPes());
        Assert.assertEquals("fernanda@example.com", usuario.getEmailPes());

    }

    @Test
    public void casoTestDadosExcecoes() throws Exception {

        Pessoa usuario;

        //Testa usuário que não existe na base de dados.
        usuario = controlador.buscarPorCPF("789.789.789-12");

        //Os dados deve retornar null.
        Assert.assertEquals(null, usuario.getNomePes());
        Assert.assertEquals(null, usuario.getCpfPes());
        Assert.assertEquals(null, usuario.getEmailPes());

        //Testa outro usuário que não existe na base de dados.
        usuario = controlador.buscarPorCPF("789.789.789-56");

        //Os dados deve retornar null.
        Assert.assertEquals(null, usuario.getNomePes());
        Assert.assertEquals(null, usuario.getCpfPes());
        Assert.assertEquals(null, usuario.getEmailPes());

        //Como não existe usuário espera se que venha null as permissões.
        Assert.assertEquals(true, controlador.buscarPermissao(usuario).equals(null));

        //Como não existe usuário espera se que venha null a lista de perfil.
        Assert.assertEquals(true, controlador.buscarPerfil(usuario).equals(null));

    }

    @Test
    public void TestControlador() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("username")).thenReturn("me");
        when(request.getParameter("password")).thenReturn("secret");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        verify(request, atLeast(1)).getParameter("username");
        writer.flush();
        Assert.assertTrue(stringWriter.toString().contains("My expected string"));
    }
}
