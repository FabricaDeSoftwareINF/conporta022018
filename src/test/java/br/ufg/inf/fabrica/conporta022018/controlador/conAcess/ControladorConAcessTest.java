/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador.conAcess;
import br.ufg.inf.fabrica.conporta022018.controlador.ControladorConAcess;

import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PerfilDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;
import org.mockito.Mockito;
import javax.persistence.NoResultException;

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

/*
 * Classe tem o intuito de exercitar o controlador referente aos casos de uso:
 *   1. ConAcess;
 *   2. RefLogin;
 *   3. IdSoft.
 * Devido a uma particularidade dos casos de uso acima essa classe de teste será
 * dividida em duas partes, sendo elas:
 *   1ª parte testa o controlador;
 *   2ª parte testa chamadas ao Framework.
 * Para os testes a seguinte estrategia foi definida:
 *   1 - será testado a acurácia dos retornos;
 *   2 - somente o cenário que se trata de Login será definido:
 *       2.1 - testes com valores válidos;
 *       2.2 - testes com valores inválidos.
 *
 * A estrategia acima descrita justifica pelo motivo dos casos de uso em sua maioria
 * se tratar de chamadas internas ao softwares.
 */
public class ControladorConAcessTest extends Mockito {

    private static ControladorConAcess controlador;

    @Test
    public void casoTestControladorConAcess() throws IOException, ParseException {

        /*
         * Os comandos abaixo prepara o ambiente para a execução dos testes, ou seja,
         * ele popula a base da dados e realiza configuração de variáveis globais.
         */
        String CAMINHO_CSV = "ControladorConAcess.csv";
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
                        List<UndAdm> undAdms = new ArrayList<>();
                        //undAdms.add(undAdm);
                        //undAdm.setSubordinadas(undAdms);

                        lotacao.setUndAdm(undAdm);
                        usuario.setServidor(lotacao);
                    }
                    if (dados[30].equals("null")) {
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
                case "perfil":
                    Perfil perfilDB = new Perfil();
                    List<Permissao> permissaoList = new ArrayList<>();
                    Permissao permissao = new Permissao();
                    PerfilDAO perfilDAO = new PerfilDAO();
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    permissao.setUrlFuncionalidade(dados[0]);
                    permissao.setOperacao(dados[1]);
                    permissaoList.add(permissao);

                    perfilDB.setPermissoes(permissaoList);
                    perfilDB.setNome(dados[2]);
                    perfilDB.setDescricao(dados[3]);

                    perfilDAO.abrirTransacao();
                    try {
                        perfilDAO.salvar(perfilDB);
                        perfilDAO.commitarTransacao();
                    } catch (Exception exc) {
                        perfilDAO.rollBackTransacao();
                    }
                    break;
            }
        }

        /*            -------->>> FINALIZAÇÂO DA PREPARAÇÃO DO AMBIENTE <<<---------                 */




        /*             -------->>> 1ª Parte - Testes do Controlador  <<<---------                   */



        /*
         * Como os casos de uso consomem dados da base de dados os testes estão organizados de
         * acordo com a estrutura abaixo:
         *
         * Seção 1 - Exercita os cenários de sucesso do caso de uso e valida os retornos;
         * Seção 2 - Exercita os cenários de excerções do caso de uso e valida os retornos.
         */



        /*                -------->>> SEÇÃO 1 - CENÁRIOS DE SUCESSO  <<<---------                    */

        controlador = new ControladorConAcess();

        /*
         * Cenário testa chamada da busca por cpf com cpf válido.
         *
         * Espera-se "True" como retorno da válidação dos dados tragos da base de dados.
         */
        usuario = controlador.buscarPorCPF("785.123.543-78"); 
        Assert.assertEquals("785.123.543-78", usuario.getCpfPes());
        Assert.assertEquals("José Carlos de Souza", usuario.getNomePes());
        Assert.assertEquals("jose@exemplo.com", usuario.getEmailPes());

        usuario = controlador.buscarPorCPF("342.897.456-34");
        Assert.assertEquals("342.897.456-34", usuario.getCpfPes());
        Assert.assertEquals("Fermanda Maria da Silva", usuario.getNomePes());
        Assert.assertEquals("fernanda@exemplo.com", usuario.getEmailPes());

        usuario = controlador.buscarPorCPF("764.098.612-09");
        Assert.assertEquals("764.098.612-09", usuario.getCpfPes());
        Assert.assertEquals("Pedro da Silva", usuario.getNomePes());
        Assert.assertEquals("pedro@exemple.com", usuario.getEmailPes());

        usuario = controlador.buscarPorCPF("456.095.124-98");
        Assert.assertEquals("456.095.124-98", usuario.getCpfPes());
        Assert.assertEquals("Maria José de Oliveira", usuario.getNomePes());
        Assert.assertEquals("maria@exemple.com", usuario.getEmailPes());


        /*
         * Cenário testa chamada a busca de perfil.
         *
         * Espera-se "True" como retorno da válidação dos dados tragos da base de dados.
         */
        List<Perfil> perfilList = new ArrayList<>();
        List<Perfil> retornoBaseDados;

        //Teste de busca por perfil moderado.
        Perfil perfilModerado = new Perfil();
        perfilModerado.setNome("ROLE_MODERADO");
        perfilModerado.setDescricao("Perfil de Designados.");
        List<Permissao> permissaoList = new ArrayList<>();
        Permissao permissaoModerado = new Permissao();
        permissaoModerado.setUrlFuncionalidade("/Moderado");
        permissaoModerado.setOperacao("Ciencia");
        permissaoList.add(permissaoModerado);
        perfilModerado.setPermissoes(permissaoList);
        perfilList.add(perfilModerado);

        usuario = controlador.buscarPorCPF("777.876.908-98");
        retornoBaseDados = controlador.buscarPerfil(usuario);
        Assert.assertEquals(perfilList, retornoBaseDados);
        Assert.assertEquals(1, perfilList.size());
        Assert.assertEquals("ROLE_MODERADO", retornoBaseDados.get(0).getNome());
        Assert.assertEquals("/Moderado", retornoBaseDados.get(0).getPermissoes().get(0).getUrlFuncionalidade());

        //Teste busca por perfil restrito.
        Perfil perfilRestrito = new Perfil();
        perfilRestrito.setNome("ROLE_RESTRITO");
        perfilRestrito.setDescricao("Perfil de Coordenador Administrativo");
        List<Permissao> permissaoRestrito = new ArrayList<>();
        Permissao permissaoRest = new Permissao();
        permissaoRest.setUrlFuncionalidade("/Restrito");
        permissaoRest.setOperacao("Relátorios");
        permissaoRestrito.add(permissaoRest);
        perfilRestrito.setPermissoes(permissaoRestrito);
        perfilList.add(perfilRestrito);

        usuario = controlador.buscarPorCPF("456.987.231-99");
        retornoBaseDados = controlador.buscarPerfil(usuario);
        Assert.assertEquals(perfilList, retornoBaseDados);
        Assert.assertEquals(2, perfilList.size());
        Assert.assertEquals("ROLE_RESTRITO", retornoBaseDados.get(1).getNome());
        Assert.assertEquals("/Restrito", retornoBaseDados.get(1).getPermissoes().get(0).getUrlFuncionalidade());

        //Teste busca por perfil restrito especifico.
        Perfil perfilEspecifico = new Perfil();
        perfilEspecifico.setNome("ROLE_RESTRITO_ESPECIFICO");
        perfilEspecifico.setDescricao("Chefe e Substituto de Chefe");
        List<Permissao> permissaoEspecifico = new ArrayList<>();
        Permissao permissaoEsp = new Permissao();
        permissaoEsp.setUrlFuncionalidade("/Especifico");
        permissaoEsp.setOperacao("Todas");
        permissaoEspecifico.add(permissaoEsp);
        perfilEspecifico.setPermissoes(permissaoEspecifico);
        perfilList.add(perfilEspecifico);

        usuario = controlador.buscarPorCPF("345.185.165-97");
        retornoBaseDados = controlador.buscarPerfil(usuario);
        Assert.assertEquals(perfilList, retornoBaseDados);
        Assert.assertEquals(3, perfilList.size());
        Assert.assertEquals("ROLE_RESTRITO_ESPECIFICO", retornoBaseDados.get(2).getNome());
        Assert.assertEquals("/Especifico", retornoBaseDados.get(2).getPermissoes().get(0).getUrlFuncionalidade());



        /*                -------->>> SEÇÃO 2 - CENÁRIOS DE EXCERÇÕES   <<<---------                  */

        /*
         * Cenário que testa o controlador quando o usuário não existe na base de dados.
         *
         * Para não parar o codigo será tratado as excerções no try/catch.
         *
         * Espera-se o usuário como null na válidação da saída.
         */
        usuario = null;
        try {
            usuario = controlador.buscarPorCPF("000.000.000-00");
        } catch (Exception e) {
            //Tratamento apenas para o caso de teste não para.
        }
        Assert.assertEquals(null, usuario);

        try {
            usuario = controlador.buscarPorCPF("999.999.999-99");
        } catch (Exception e) {
            //Tratamento apenas para o caso de teste não para.
        }
        Assert.assertEquals(null, usuario);

        try {
            usuario = controlador.buscarPorCPF("888.888.888-88");
        } catch (Exception e) {
            //Tratamento apenas para o caso de teste não para.
        }
        Assert.assertEquals(null, usuario);

        try {
            usuario = controlador.buscarPorCPF("777.777.777-77");
        } catch (Exception e) {
            //Tratamento apenas para o caso de teste não para.
        }
        Assert.assertEquals(null, usuario);

    }

}
