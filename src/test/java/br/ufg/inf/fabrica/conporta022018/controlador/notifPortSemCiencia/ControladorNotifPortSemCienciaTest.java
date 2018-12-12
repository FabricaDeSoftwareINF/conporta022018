/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador.notifPortSemCiencia;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorNotifPortSemCiencia;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
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

public class ControladorNotifPortSemCienciaTest {

    private static ControladorNotifPortSemCiencia controladorNotifPortSemCiencia;


    @BeforeClass
    public static void casoTestPepararCenario() throws IOException, ParseException {

        String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/notifPortSemCiencia/NotifPortSemCienciaTest.csv";
        String REGRA = ",";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;

        PessoaDAO pessoaDAO = new PessoaDAO();
        DesignadoDAO designadoDAO = new DesignadoDAO();
        PortariaDAO portariaDAO =new PortariaDAO();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        /*
         * Preparação do ambiente para teste.
         * População do banco de Dados para atendam os pré-requisitos do caso de uso.
         */

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {
            linha = dadosSoftware.get(index);

            //Definir as tabelas que serão populadas no Banco de Dados.
            if (linha.equals("pessoa") || linha.equals("portaria") || linha.equals("undAdm") || linha.equals("designado")) {
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual) {
                case "pessoa" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    Pessoa pessoa = new Pessoa();

                    pessoa.setId(Long.parseLong(dados[0]));
                    pessoa.setNomePes(dados[1]);
                    pessoa.setCpfPes(dados[2]);
                    pessoa.setEmailPes(dados[3]);
                    pessoa.setSenhaUsu(dados[4]);
                    pessoa.setEhUsuAtivo(Boolean.getBoolean(dados[5]));
                    pessoaDAO.abrirTransacao();
                    pessoaDAO.salvar(pessoa);
                    pessoaDAO.commitarTransacao();

                    break;
                case "portaria":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    Portaria portaria = new Portaria();
                    portaria.setId(Long.parseLong(dados[0]));
                    portaria.setAnoId(Integer.valueOf(dados[1]));
                    portaria.setSeqId(Integer.valueOf(dados[2]));
                    portaria.setStatus(PortariaStatus.valueOf(dados[3]));
                    portaria.setAssunto(dados[4]);
                    portaria.setDtExped(formato.parse(dados[5]));
                    portaria.setDtIniVig(formato.parse(dados[7]));
                    portaria.setDtFimVig(formato.parse(dados[8]));
                    portariaDAO.abrirTransacao();
                    portariaDAO.salvar(portaria);
                    portariaDAO.commitarTransacao();
                    break;
                case "designado" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);

                    Designado designado = new Designado();
                    designado.setId( Long.parseLong(dados[0]) );

                    if(!dados[1].isEmpty()){
                        Date dtCienciaDesig = formato.parse(dados[1]);
                        designado.setDtCienciaDesig(dtCienciaDesig);
                    }

                    designado.setTipFuncDesig(FuncaoDesig.COORDENADOR);

                    designado.setDescrFuncDesig(dados[3]);

                    if(!dados[4].isEmpty()){
                        designado.setHorasDefFuncDesig( Integer.parseInt(dados[4]) );
                    }

                    if(!dados[5].isEmpty()){
                        designado.setHorasExecFuncDesig( Integer.parseInt(dados[5]) );
                    }

                    pessoa = pessoaDAO.buscar( Long.parseLong(dados[6]) );
                    designado.setDesignado(pessoa);

                    designado.setTipFuncDesig(FuncaoDesig.COORDENADOR);

                    // Salva o objeto no banco
                    designadoDAO.abrirTransacao();
                    designadoDAO.salvar(designado);
                    designadoDAO.commitarTransacao();
                    break;
            }
        }
    }

    @Before
    public void casoTestPrepararExecucao() {

        //Neste Grupo ficará tudo que é necessário para a execução dos cenarios definidos para os testes.
        controladorNotifPortSemCiencia = new ControladorNotifPortSemCiencia();
    }



    /**
     *  Devido a natureza do caso de uso, foi-se escolhido que a melhor forma de realizar os testes seria por
     *  teste de valores limites.
     *  No contexto desse caso de uso, foi-se criado duas funções com o mesmo objetivo no controlador, sendo que uma
     *  recebe um parâmetro, sendo assim possível modificar a data que servirá como base da busca e evita que os
     *  resultados dos testes variem dependendo do momento que forem realizados.
     *
     *  Serão realizados 3 testes no total:
     *  1- Nenhum designado estpa com a ciência atrasada;
     *  2- Apenas um designado está com a ciência atrasada;
     *  3- Mais de um designado estão com a ciência atrasada
     *
     *  Com esses três teste, acredita-se que são satisfeitas a necessidade do caso de uso ser testado.
     *
     */

    @Test
    public void casoTestVerifcarCiencia() throws ParseException {

        /**
         * Testa o cenário onde não há designados com ciência atrasada.
         */
        controladorNotifPortSemCiencia.verificarCiencia("10/12/2018");

        /**
         * Testa o cenário onde há um designados com ciência atrasada.
         */
        controladorNotifPortSemCiencia.verificarCiencia("16/12/2018");

        /**
         * Testa o cenário onde há vários designados com ciência atrasada.
         */
        controladorNotifPortSemCiencia.verificarCiencia("25/12/2018");

    }


}
