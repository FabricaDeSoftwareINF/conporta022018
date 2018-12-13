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
        String REGRA = ";";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;

        PessoaDAO pessoaDAO = new PessoaDAO();
        DesignadoDAO designadoDAO = new DesignadoDAO();
        PortariaDAO portariaDAO =new PortariaDAO();

        Portaria portaria = new Portaria();
        Pessoa pessoa = new Pessoa();
        UndAdm undAdm = new UndAdm();
        Designado designado = new Designado();
        List<Designado> designados = new ArrayList<>();
        List<Referencia> referencias = new ArrayList<>();
        List<Recebedora> recebedoras = new ArrayList<>();

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
                case "portaria":

                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    portaria.setAnoId(0);
                    portaria.setSeqId(0);
                    portaria.setAssunto("Lorem Ipsum");
                    portaria.setDtExped(formato.parse(dados[0]));
                    portaria.setDtIniVig(formato.parse(dados[1]));
                    portaria.setDtFimVig(formato.parse(dados[2]));
                    portaria.setDtPublicDou(formato.parse(dados[3]));
                    portaria.setArqPdf(null);
                    portaria.setSiglaUndId("UndId");
                    portaria.setAssinatura("Assinatura");
                    if (dados[4] == "Ativo"){
                        portaria.setStatus(PortariaStatus.ATIVA);
                    }else{
                        portaria.setStatus(PortariaStatus.EXPIRADA);
                    }
                    portaria.setReferencias(referencias);
                    portaria.setUndRecebedora(recebedoras);

                    //Primeiro discente da lista
                    pessoa.setCpfPes("");
                    pessoa.setDiscente(null);
                    pessoa.setEhUsuAtivo(true);
                    pessoa.setEmailPes(dados[5]);
                    pessoa.setGestao(null);
                    pessoa.setNomePes("");
                    pessoa.setSenhaUsu("");
                    pessoa.setServidor(null);

                    designado.setDesignado(pessoa);
                    designado.setDescrFuncDesig("");
                    designado.setDtCienciaDesig(formato.parse(dados[6]));
                    designado.setHorasDefFuncDesig(10);
                    designado.setTipFuncDesig(FuncaoDesig.COORDENADOR);

                    designados.add(designado);

                    //Segundo discente da lista
                    pessoa.setCpfPes("");
                    pessoa.setDiscente(null);
                    pessoa.setEhUsuAtivo(true);
                    pessoa.setEmailPes(dados[7]);
                    pessoa.setGestao(null);
                    pessoa.setNomePes("");
                    pessoa.setSenhaUsu("");
                    pessoa.setServidor(null);

                    designado.setDesignado(pessoa);
                    designado.setDescrFuncDesig("");
                    designado.setDtCienciaDesig(formato.parse(dados[8]));
                    designado.setHorasDefFuncDesig(10);
                    designado.setTipFuncDesig(FuncaoDesig.COORDENADOR);

                    designados.add(designado);

                    //Terceiro discente da lista
                    pessoa.setCpfPes("");
                    pessoa.setDiscente(null);
                    pessoa.setEhUsuAtivo(true);
                    pessoa.setEmailPes(dados[9]);
                    pessoa.setGestao(null);
                    pessoa.setNomePes("");
                    pessoa.setSenhaUsu("");
                    pessoa.setServidor(null);

                    designado.setDesignado(pessoa);
                    designado.setDescrFuncDesig("");
                    designado.setDtCienciaDesig(formato.parse(dados[10]));
                    designado.setHorasDefFuncDesig(10);
                    designado.setTipFuncDesig(FuncaoDesig.COORDENADOR);

                    designados.add(designado);

                    //Quarto discente da lista
                    pessoa.setCpfPes("");
                    pessoa.setDiscente(null);
                    pessoa.setEhUsuAtivo(true);
                    pessoa.setEmailPes(dados[11]);
                    pessoa.setGestao(null);
                    pessoa.setNomePes("");
                    pessoa.setSenhaUsu("");
                    pessoa.setServidor(null);

                    designado.setDesignado(pessoa);
                    designado.setDescrFuncDesig("");
                    designado.setDtCienciaDesig(formato.parse(dados[12]));
                    designado.setHorasDefFuncDesig(10);
                    designado.setTipFuncDesig(FuncaoDesig.COORDENADOR);

                    designados.add(designado);

                    portaria.setDesignados(designados);
                    pessoa.setEmailPes(dados[13]);
                    portaria.setExpedidor(pessoa);
                    portaria.setUnidadeExpedidora(undAdm);

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
     *  Com esses três casos de teste, acredita-se que são satisfeitas a necessidade do caso de uso ser testado.
     *
     */

    @Test
    public void casoSemNenhumaPendenciaDeCiencia() throws ParseException {

        /**
         * Testa o cenário onde não há designados com ciência atrasada.
         */
        controladorNotifPortSemCiencia.verificarCiencia("10/12/2018");
    }

    @Test
    public void casoComUmaPendenciaDeCiencia() throws ParseException {

        /**
         * Testa o cenário onde há um designados com ciência atrasada.
         */
        controladorNotifPortSemCiencia.verificarCiencia("13/12/2018");
    }

    @Test
    public void casoComVariasPendenciaDeCiencia() throws ParseException {

        /**
         * Testa o cenário onde há varios designados com ciência atrasada.
         */
        controladorNotifPortSemCiencia.verificarCiencia("20/12/2018");
    }


}
