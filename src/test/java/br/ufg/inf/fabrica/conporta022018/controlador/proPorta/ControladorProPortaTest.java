package br.ufg.inf.fabrica.conporta022018.controlador.proPorta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorProPorta;
import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorProPortaTest{
    private static ControladorProPorta controladorProPorta;

    @BeforeClass
    public static void casoTestPrepararCenario() throws IOException{
        final String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/proPorta/ProPortaTest.csv";
        final String REGRA = ";";
        List<String> dadosSoftware = new ArrayList<>();
        Extrator extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;

        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa pessoa;

        UndAdmDAO undAdmDAO = new UndAdmDAO();
        UndAdm undAdm;

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {

            linha = dadosSoftware.get(index);

            if (linha.equals("pessoa")||linha.equals("portaria")||linha.equals("undAdm")||linha.equals("designado")){
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual){
                case "pessoa" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    // Cria o objeto
                    pessoa = new Pessoa();
                    pessoa.setId( Long.parseLong(dados[0]) );
                    pessoa.setNomePes(dados[1]);
                    pessoa.setCpfPes(dados[2]);
                    pessoa.setEmailPes(dados[3]);
                    pessoa.setSenhaUsu(dados[4]);
                    if(dados[5].equals("true")){
                        pessoa.setEhUsuAtivo(true);
                    }else{
                        pessoa.setEhUsuAtivo(false);
                    }
                    // Salva o objeto no banco
                    pessoaDAO.salvar(pessoa);
                    break;
                case "undAdm" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    // Cria o objeto
                    undAdm = new UndAdm();
                    undAdm.setId( Long.parseLong(dados[0]) );
                    undAdm.setSiglaUnAdm(dados[1]);
                    undAdm.setMinInat( Integer.parseInt(dados[2]) );
                    undAdm.setNomeUnd(dados[3]);
                    undAdm.setTipoUnd(1);
                    undAdm.setUltPort(dados[5]);
                    undAdm.setAnoPort( Integer.parseInt(dados[6]) );
                    undAdm.setUltNumExped( Integer.parseInt(dados[7]) );
                    undAdm.setUltNumProp( Integer.parseInt(dados[8]) );
                    // Salva o objeto no banco
                    undAdmDAO.salvar(undAdm);
                    break;
            }
        }
    }

    @Before
    public void casoTestPrepararExecucao(){
        controladorProPorta = new ControladorProPorta();
    }

    @Test
    public void casoTestBuscarPessoas()throws ParseException {
        List<Pessoa> lista = controladorProPorta.buscarPessoas();
        Assert.assertEquals(3, lista.size());
    }

    @Test
    public void casoTestBuscarUndAdm()throws ParseException {
        List<UndAdm> lista = controladorProPorta.buscarUndAdm();
        Assert.assertEquals(3, lista.size());
    }

    @Test
    public void proPortaComDesignacaoComReferencia() {
        // Testa o cenário típico
        // Criação de portaria proposta com designação e referência com cancelamento de portaria
    }

    @Test
    public void proPortaComDesignacaoSemReferecia() {
        // Testa o cenário alternativo 1
        // Criação de portaria proposta com designação e sem referência
    }

    @Test
    public void proPortaSemDesignacaoComReferecia() {
        // Testa o cenário alternativo 2
        // Criação de portaria proposta com referência com cancelamento de portaria e sem designação
    }

    @Test
    public void proPortaSemDesignacaoSemReferecia() {
        // Testa o cenário alternativo 3
        // Criação de portaria proposta sem referência nem designação
    }

    @Test
    public void proPortaRegraNegocioCamObri() {
        // Testa a regra de negócio CamObri
        // O assunto deve ter no mínimo um item, a dtIniVig e o resumo são campos obrigatórios.
    }

    @Test
    public void proPortaRegraNegocioAtribGerados() {
        // Testa a regra de negócio AtribGerados
        // O siglaUndId é preenchido de acordo com o atributo
    }

    @AfterClass
    public static void casoTestResultados() throws IOException {

        //Aqui deve ser verificado os resultados da exceção do Grupo G1 e G2, normalmente aqui
    }


}



