package br.ufg.inf.fabrica.conporta022018.controlador.proPorta;

import br.ufg.inf.fabrica.conporta022018.controlador.ControladorProPorta;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.*;
import br.ufg.inf.fabrica.conporta022018.util.Extrator;
import br.ufg.inf.fabrica.conporta022018.util.LerArquivo;
import br.ufg.inf.fabrica.conporta022018.util.csv.ExtratorCSV;
import org.junit.*;

import javax.sound.sampled.Port;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ControladorProPortaTest{
    private static ControladorProPorta controladorProPorta;

    private static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static Extrator extrator = new ExtratorCSV();

    @BeforeClass
    public static void casoTestPrepararCenario() throws IOException, ParseException {
        final String CAMINHO_CSV = "src/test/java/br/ufg/inf/fabrica/conporta022018/controlador/proPorta/ProPortaDadosTest.csv";
        final String REGRA = ";";
        List<String> dadosSoftware = new ArrayList<>();
        extrator = new ExtratorCSV();
        LerArquivo lerArquivo = new LerArquivo();
        String tabelaAtual = " ";
        String dados[];
        String linha;

        dadosSoftware = lerArquivo.lerArquivo(CAMINHO_CSV);

        for (int index = 0; index < dadosSoftware.size(); index++) {

            linha = dadosSoftware.get(index);

            if (linha.equals("pessoa") || linha.equals("portaria") || linha.equals("undAdm") || linha.equals("designado") || linha.equals("recebedora")){
                tabelaAtual = linha;
                index++;
                continue;
            }

            switch (tabelaAtual){
                case "pessoa" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosPessoa(dados);
                    break;
                case "undAdm" :
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosUndAdm(dados);
                    break;
                case "recebedora":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosRecebedora(dados);
                    break;
                case "designado":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosDesignado(dados);
                    break;
                case "portaria":
                    extrator.setTexto(linha);
                    dados = extrator.getResultado(REGRA);
                    trataDadosPortaria(dados);
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
        Assert.assertEquals(7, lista.size());
        Assert.assertEquals("José Carlos Santos", lista.get(0).getNomePes());
        Assert.assertEquals("Maria José", lista.get(1).getNomePes());
        Assert.assertEquals("João Tavares", lista.get(2).getNomePes());
        Assert.assertEquals("Matheus Costa", lista.get(3).getNomePes());
        Assert.assertEquals("Beatriz Silva", lista.get(4).getNomePes());
        Assert.assertEquals("Lucas Teixeira", lista.get(5).getNomePes());
        Assert.assertEquals("Vitor Almeida", lista.get(6).getNomePes());
    }

    @Test
    public void casoTestBuscarUndAdm()throws ParseException {
        List<UndAdm> lista = controladorProPorta.buscarUndAdm();
        Assert.assertEquals(3, lista.size());
        Assert.assertEquals("Instituto de Informática", lista.get(0).getNomeUnd());
        Assert.assertEquals("Instituto de Matemática", lista.get(1).getNomeUnd());
        Assert.assertEquals("Faculdade de Educação Física", lista.get(2).getNomeUnd());
    }

    @Test
    public void casoTestBuscarPortarias()throws ParseException {
        List<Portaria> lista = controladorProPorta.buscarPortarias();
        Assert.assertEquals(3, lista.size());
        Assert.assertEquals("Novo coordenador e vice-coordenador de curso", lista.get(0).getAssunto());
        Assert.assertEquals("Novo presidente do NDE", lista.get(1).getAssunto());
        Assert.assertEquals("Criação da Liga INF, IME e FEF", lista.get(2).getAssunto());
    }

    @Test
    public void proPortaComDesignacaoComReferencia() throws IOException {
        // Testa o cenário típico
        // Criação de portaria proposta com designação e referência com cancelamento de portaria

        // Cria o mapa de variáveis
        Map<String, Object> parametros = new HashMap<String, Object>();

        // Preenche o mapa
        String assunto = "Portaria Com Designacao e Com referência";
        parametros.put("assunto", assunto);

        Date dtIniVig = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();
        parametros.put("dtIniVig", dtIniVig);

        int horasDesig = 220;
        parametros.put("horasDesig", horasDesig);

        String resumo = "Um resumo da portaria criada com designação e com referência";
        parametros.put("resumo", resumo);


        // Cria a lista vazia de designados

        List<Designado> designados = new ArrayList<>();

        // Busca as pessoas no banco (Simula o que o front ia fazer)
        List<Pessoa> listaPessoas = controladorProPorta.buscarPessoas();

        // Seleciona a primeira e a segunda (O usuário iria escolher) já criando o objeto designado
        Designado designado = new Designado();
        designado.setDesignado(listaPessoas.get(0));
        designado.setTipFuncDesig(FuncaoDesig.MEMBRO);
        designado.setDescrFuncDesig("Descrição teste 1");
        designados.add(designado);
        Designado designado2 = new Designado();
        designado2.setDesignado(listaPessoas.get(1));
        designado2.setTipFuncDesig(FuncaoDesig.COORDENADOR);
        designado2.setDescrFuncDesig("Descrição do Coordenador Teste");
        designado2.setHorasDefFuncDesig(200);
        designados.add(designado2);
        parametros.put("designados", designados);

        // Cria a lista vazia de Referências
        List<Referencia> referencias = new ArrayList<>();

        // Busca as portarias cadastradas no banco
        List<Portaria> listaPortarias = controladorProPorta.buscarPortarias();

        // Seleciona a primeira (O usuário iria escolher qual desejar) já criando o objeto referencia
        Referencia referencia = new Referencia();
        referencia.setEhCancelamento(true);
        referencia.setReferencia(listaPortarias.get(0));
        referencias.add(referencia);
        parametros.put("referencias", referencias);

        // Cria a lista vazia de recebedoras
        List<Recebedora> recebedoras = new ArrayList<>();

        // Busca as unidades administrativas
        List<UndAdm> listaUndAdm = controladorProPorta.buscarUndAdm();

        // Seleciona a primeira (O usuário iria escolher qual desejar) já criando o objeto recebedora
        Recebedora recebedora = new Recebedora();
        recebedora.setUnidadeRecebedora(listaUndAdm.get(0));
        recebedoras.add(recebedora);
        parametros.put("recebedoras", recebedoras);

        // Busca o expeditor (Usa também o primeiro da lista de unidades administrativas)
        UndAdm undAdmExpedidora = listaUndAdm.get(0);
        parametros.put("expedidora", undAdmExpedidora);

        // Tenta salvar
        Portaria portaria = controladorProPorta.salvar(parametros);

        // Busca os dados pra ver se a portaria foi salva corretamente
        PortariaDAO portariaDAO = new PortariaDAO();
        portaria = portariaDAO.buscar(portaria.getId());

        Assert.assertEquals(assunto, portaria.getAssunto());
        Assert.assertEquals(dtIniVig, portaria.getDtIniVig());
        Assert.assertEquals(horasDesig, portaria.getHorasDesig());
        Assert.assertEquals(resumo, portaria.getResumo());
        Assert.assertEquals(designados, portaria.getDesignados());
        Assert.assertEquals(referencias, portaria.getReferencias());
        Assert.assertEquals(recebedoras, portaria.getUndRecebedora());
        Assert.assertEquals(PortariaStatus.PROPOSTA, portaria.getStatus());
    }

    @Test
    public void proPortaComDesignacaoSemReferecia() throws IOException {
        // Testa o cenário alternativo 1
        // Criação de portaria proposta com designação e sem referência
        // Cria o mapa de variáveis
        Map<String, Object> parametros = new HashMap<String, Object>();

        // Preenche o mapa
        String assunto = "Portaria Com Designacao e Com referência";
        parametros.put("assunto", assunto);

        Date dtIniVig = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();
        parametros.put("dtIniVig", dtIniVig);

        int horasDesig = 220;
        parametros.put("horasDesig", horasDesig);

        String resumo = "Um resumo da portaria criada com designação e com referência";
        parametros.put("resumo", resumo);


        // Cria a lista vazia de designados

        List<Designado> designados = new ArrayList<>();

        // Busca as pessoas no banco (Simula o que o front ia fazer)
        List<Pessoa> listaPessoas = controladorProPorta.buscarPessoas();

        // Seleciona a primeira e a segunda (O usuário iria escolher) já criando o objeto designado
        Designado designado = new Designado();
        designado.setDesignado(listaPessoas.get(0));
        designado.setTipFuncDesig(FuncaoDesig.MEMBRO);
        designado.setDescrFuncDesig("Descrição teste 1");
        designados.add(designado);
        Designado designado2 = new Designado();
        designado2.setDesignado(listaPessoas.get(1));
        designado2.setTipFuncDesig(FuncaoDesig.COORDENADOR);
        designado2.setDescrFuncDesig("Descrição do Coordenador Teste");
        designado2.setHorasDefFuncDesig(200);
        designados.add(designado2);
        parametros.put("designados", designados);

        // Cria a lista vazia de recebedoras
        List<Recebedora> recebedoras = new ArrayList<>();

        // Busca as unidades administrativas
        List<UndAdm> listaUndAdm = controladorProPorta.buscarUndAdm();

        // Seleciona a primeira (O usuário iria escolher qual desejar) já criando o objeto recebedora
        Recebedora recebedora = new Recebedora();
        recebedora.setUnidadeRecebedora(listaUndAdm.get(0));
        recebedoras.add(recebedora);
        parametros.put("recebedoras", recebedoras);

        // Busca o expeditor (Usa também o primeiro da lista de unidades administrativas)
        UndAdm undAdmExpedidora = listaUndAdm.get(0);
        parametros.put("expedidora", undAdmExpedidora);

        // Tenta salvar
        Portaria portaria = controladorProPorta.salvar(parametros);

        // Busca os dados pra ver se a portaria foi salva corretamente
        PortariaDAO portariaDAO = new PortariaDAO();
        portaria = portariaDAO.buscar(portaria.getId());

        Assert.assertEquals(assunto, portaria.getAssunto());
        Assert.assertEquals(dtIniVig, portaria.getDtIniVig());
        Assert.assertEquals(horasDesig, portaria.getHorasDesig());
        Assert.assertEquals(resumo, portaria.getResumo());
        Assert.assertEquals(designados, portaria.getDesignados());
        Assert.assertEquals(recebedoras, portaria.getUndRecebedora());
        Assert.assertEquals(PortariaStatus.PROPOSTA, portaria.getStatus());
    }

    @Test
    public void proPortaSemDesignacaoComReferecia() throws IOException {
        // Testa o cenário alternativo 2
        // Criação de portaria proposta com referência com cancelamento de portaria e sem designação

        // Cria o mapa de variáveis
        Map<String, Object> parametros = new HashMap<String, Object>();

        // Preenche o mapa
        String assunto = "Portaria Com Designacao e Com referência";
        parametros.put("assunto", assunto);

        Date dtIniVig = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();
        parametros.put("dtIniVig", dtIniVig);

        int horasDesig = 220;
        parametros.put("horasDesig", horasDesig);

        String resumo = "Um resumo da portaria criada com designação e com referência";
        parametros.put("resumo", resumo);

        // Cria a lista vazia de Referências
        List<Referencia> referencias = new ArrayList<>();

        // Busca as portarias cadastradas no banco
        List<Portaria> listaPortarias = controladorProPorta.buscarPortarias();

        // Seleciona a primeira (O usuário iria escolher qual desejar) já criando o objeto referencia
        Referencia referencia = new Referencia();
        referencia.setEhCancelamento(true);
        referencia.setReferencia(listaPortarias.get(0));
        referencias.add(referencia);
        parametros.put("referencias", referencias);

        // Cria a lista vazia de recebedoras
        List<Recebedora> recebedoras = new ArrayList<>();

        // Busca as unidades administrativas
        List<UndAdm> listaUndAdm = controladorProPorta.buscarUndAdm();

        // Seleciona a primeira (O usuário iria escolher qual desejar) já criando o objeto recebedora
        Recebedora recebedora = new Recebedora();
        recebedora.setUnidadeRecebedora(listaUndAdm.get(0));
        recebedoras.add(recebedora);
        parametros.put("recebedoras", recebedoras);

        // Busca o expeditor (Usa também o primeiro da lista de unidades administrativas)
        UndAdm undAdmExpedidora = listaUndAdm.get(0);
        parametros.put("expedidora", undAdmExpedidora);

        // Tenta salvar
        Portaria portaria = controladorProPorta.salvar(parametros);

        // Busca os dados pra ver se a portaria foi salva corretamente
        PortariaDAO portariaDAO = new PortariaDAO();
        portaria = portariaDAO.buscar(portaria.getId());

        Assert.assertEquals(assunto, portaria.getAssunto());
        Assert.assertEquals(dtIniVig, portaria.getDtIniVig());
        Assert.assertEquals(horasDesig, portaria.getHorasDesig());
        Assert.assertEquals(resumo, portaria.getResumo());
        Assert.assertEquals(referencias, portaria.getReferencias());
        Assert.assertEquals(recebedoras, portaria.getUndRecebedora());
        Assert.assertEquals(PortariaStatus.PROPOSTA, portaria.getStatus());
    }

    @Test
    public void proPortaSemDesignacaoSemReferecia() throws IOException {
        // Testa o cenário alternativo 3
        // Criação de portaria proposta sem referência nem designação

        // Cria o mapa de variáveis
        Map<String, Object> parametros = new HashMap<String, Object>();

        // Preenche o mapa
        String assunto = "Portaria Com Designacao e Com referência";
        parametros.put("assunto", assunto);

        Date dtIniVig = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();
        parametros.put("dtIniVig", dtIniVig);

        int horasDesig = 220;
        parametros.put("horasDesig", horasDesig);

        String resumo = "Um resumo da portaria criada com designação e com referência";
        parametros.put("resumo", resumo);

        // Cria a lista vazia de recebedoras
        List<Recebedora> recebedoras = new ArrayList<>();

        // Busca as unidades administrativas
        List<UndAdm> listaUndAdm = controladorProPorta.buscarUndAdm();

        // Seleciona a primeira (O usuário iria escolher qual desejar) já criando o objeto recebedora
        Recebedora recebedora = new Recebedora();
        recebedora.setUnidadeRecebedora(listaUndAdm.get(0));
        recebedoras.add(recebedora);
        parametros.put("recebedoras", recebedoras);

        // Busca o expeditor (Usa também o primeiro da lista de unidades administrativas)
        UndAdm undAdmExpedidora = listaUndAdm.get(0);
        parametros.put("expedidora", undAdmExpedidora);

        // Tenta salvar
        Portaria portaria = controladorProPorta.salvar(parametros);

        // Busca os dados pra ver se a portaria foi salva corretamente
        PortariaDAO portariaDAO = new PortariaDAO();
        portaria = portariaDAO.buscar(portaria.getId());

        Assert.assertEquals(assunto, portaria.getAssunto());
        Assert.assertEquals(dtIniVig, portaria.getDtIniVig());
        Assert.assertEquals(horasDesig, portaria.getHorasDesig());
        Assert.assertEquals(resumo, portaria.getResumo());
        Assert.assertEquals(recebedoras, portaria.getUndRecebedora());
        Assert.assertEquals(PortariaStatus.PROPOSTA, portaria.getStatus());
    }

    @Test
    public void proPortaRegraNegocioCamObri() throws IOException {
        // Testa a regra de negócio CamObri
        // O assunto, a dtIniVig, o resumo e o expedidor são campos obrigatórios.

        // Cria o mapa de variáveis
        Map<String, Object> parametros = new HashMap<String, Object>();
        String assunto = "Portaria Com Designacao e Com referência";
        String resumo = "Um resumo da portaria criada com designação e com referência";
        Date dtIniVig = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();
        List<UndAdm> listaUndAdm = controladorProPorta.buscarUndAdm();
        UndAdm undAdmExpedidora = listaUndAdm.get(0);

        // Testa cada variável separadamente

        // Sem assunto
        try {
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "assunto, dtIniVig, resumo e expedidora são obrigatórios");
        }

        // assunto de tipo incorreto
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", 1);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O assunto deve ser do tipo String");
        }

        // Sem resumo
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "assunto, dtIniVig, resumo e expedidora são obrigatórios");
        }

        // resumo de tipo incorreto
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", 1);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O resumo deve ser do tipo String");
        }

        // Sem dtIniVig
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("expedidora", undAdmExpedidora);
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "assunto, dtIniVig, resumo e expedidora são obrigatórios");
        }

        // dtIniVig de tipo incorreto
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", 1);
            parametros.put("expedidora", undAdmExpedidora);
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O dtIniVig deve ser do tipo Date");
        }

        // Sem expedidora
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "assunto, dtIniVig, resumo e expedidora são obrigatórios");
        }

        // expedidora de tipo incorreto
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", 1);
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O expedidora deve ser do tipo UndAdm");
        }

    }

    @Test
    public void proPortaTestTipoParametrosIncorretos() throws IOException {
        // Testa se algum parâmetro pode ser passado com o tipo incorreto
        // Trata a excecão de todos os cenários "Ocorreu um erro na validação" e a regra de negócio CamTipoDados
        // Testa apenas os campos opcionais, pois os obrigatórios foram testados no método anterior

        // Cria o mapa de variáveis com os parametros obrigatórios
        Map<String, Object> parametros = new HashMap<String, Object>();
        String assunto = "Portaria Com Designacao e Com referência";
        String resumo = "Um resumo da portaria criada com designação e com referência";
        Date dtIniVig = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();
        List<UndAdm> listaUndAdm = controladorProPorta.buscarUndAdm();
        UndAdm undAdmExpedidora = listaUndAdm.get(0);

        // Testa cada variável separadamente

        // dtFimVig
        try {
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            parametros.put("dtFimVig", 1);
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O dtFimVig deve ser do tipo Date");
        }

        // dPublicDou
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            parametros.put("dPublicDou", 1);
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O dPublicDou deve ser do tipo Date");
        }

        // horasDesig
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            parametros.put("horasDesig", "Teste");
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O horasDesig deve ser do tipo int");
        }

        // arqPdf
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            parametros.put("arqPdf", "Teste");
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O arqPdf deve ser do tipo File");
        }

        // designados
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            parametros.put("designados", "Teste");
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O designados deve ser do tipo List<Designado>");
        }

        // referencias
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            parametros.put("referencias", "Teste");
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O referencias deve ser do tipo List<Referencia>");
        }

        // recebedoras
        try {
            parametros = new HashMap<String, Object>();
            parametros.put("assunto", assunto);
            parametros.put("resumo", resumo);
            parametros.put("dtIniVig", dtIniVig);
            parametros.put("expedidora", undAdmExpedidora);
            parametros.put("recebedoras", "Teste");
            Portaria portaria = controladorProPorta.salvar(parametros);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "O recebedoras deve ser do tipo List<Recebedora>");
        }
    }

    @Test
    public void proPortaRegraNegocioAtribGerados() throws IOException {
        // Testa a regra de negócio AtribGerados
        // o seqId é preenchido com o próximo número do atributo ultNumProp da unidade administrativa expedidora,
        // o ultNumProp da unidade administrativa expedidora também é atualizado, o status é preenchido com o valor “Proposta”.

        // Cadastra a portaria

        // Cria o mapa de variáveis
        Map<String, Object> parametros = new HashMap<String, Object>();

        // Preenche o mapa
        String assunto = "Portaria Com Designacao e Com referência";
        parametros.put("assunto", assunto);

        Date dtIniVig = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();
        parametros.put("dtIniVig", dtIniVig);

        int horasDesig = 220;
        parametros.put("horasDesig", horasDesig);

        String resumo = "Um resumo da portaria criada com designação e com referência";
        parametros.put("resumo", resumo);

        // Cria a lista vazia de recebedoras
        List<Recebedora> recebedoras = new ArrayList<>();

        // Busca as unidades administrativas
        List<UndAdm> listaUndAdm = controladorProPorta.buscarUndAdm();

        // Seleciona a primeira (O usuário iria escolher qual desejar) já criando o objeto recebedora
        Recebedora recebedora = new Recebedora();
        recebedora.setUnidadeRecebedora(listaUndAdm.get(0));
        recebedoras.add(recebedora);
        parametros.put("recebedoras", recebedoras);

        // Busca o expeditor (Usa também o primeiro da lista de unidades administrativas)
        UndAdm undAdmExpedidora = listaUndAdm.get(0);
        int ultnum = undAdmExpedidora.getUltNumProp() + 1;
        parametros.put("expedidora", undAdmExpedidora);

        // Tenta salvar
        Portaria portaria = controladorProPorta.salvar(parametros);

        // Verifica se salvou o seqId com o próximo numero prop
        PortariaDAO portariaDAO = new PortariaDAO();
        portaria = portariaDAO.buscar(portaria.getId());
        Assert.assertEquals(ultnum, portaria.getSeqId());
        // Verifica o status
        Assert.assertEquals(PortariaStatus.PROPOSTA, portaria.getStatus());

        // Verifica se o ultNumPro foi persistido
        UndAdmDAO undAdmDAO = new UndAdmDAO();
        undAdmExpedidora = undAdmDAO.buscar(undAdmExpedidora.getId());
        Assert.assertEquals(undAdmExpedidora.getUltNumProp(), (Integer)ultnum);
    }

    @Test
    public void proPortaTodosCamposPreenchidos() throws IOException {
        // Testa a persistência com todos os campo possíveis preenchidos
        // Cria o mapa de variáveis
        Map<String, Object> parametros = new HashMap<String, Object>();

        // Preenche o mapa
        String assunto = "Portaria Com Designacao e Com referência";
        parametros.put("assunto", assunto);

        Date dtIniVig = new GregorianCalendar(2019, Calendar.FEBRUARY, 1).getTime();
        parametros.put("dtIniVig", dtIniVig);

        Date dtFimVig = new GregorianCalendar(2020, Calendar.FEBRUARY, 1).getTime();
        parametros.put("dtFimVig", dtFimVig);

        Date dPublicDou = new GregorianCalendar(2019, Calendar.MARCH, 15).getTime();
        parametros.put("dPublicDou", dPublicDou);

        int horasDesig = 220;
        parametros.put("horasDesig", horasDesig);

        String resumo = "Um resumo da portaria criada com designação e com referência";
        parametros.put("resumo", resumo);

        // Coloca o arquivo pdf
        String dir = System.getProperty("user.dir");
        // Cria o arquivo
        File arqPdf = new File(dir+"/src/test/resources/arq_1.pdf");
        parametros.put("arqPdf", arqPdf);
        // Transforma o arquivo para byte[] para comparar
        byte[] arqPdfBytes = Files.readAllBytes(arqPdf.toPath());

        // Cria a lista vazia de designados

        List<Designado> designados = new ArrayList<>();

        // Busca as pessoas no banco (Simula o que o front ia fazer)
        List<Pessoa> listaPessoas = controladorProPorta.buscarPessoas();

        // Seleciona a primeira e a segunda (O usuário iria escolher) já criando o objeto designado
        Designado designado = new Designado();
        designado.setDesignado(listaPessoas.get(0));
        designado.setTipFuncDesig(FuncaoDesig.MEMBRO);
        designado.setDescrFuncDesig("Descrição teste 1");
        designados.add(designado);
        Designado designado2 = new Designado();
        designado2.setDesignado(listaPessoas.get(1));
        designado2.setTipFuncDesig(FuncaoDesig.COORDENADOR);
        designado2.setDescrFuncDesig("Descrição do Coordenador Teste");
        designado2.setHorasDefFuncDesig(200);
        designados.add(designado2);
        parametros.put("designados", designados);

        // Cria a lista vazia de Referências
        List<Referencia> referencias = new ArrayList<>();

        // Busca as portarias cadastradas no banco
        List<Portaria> listaPortarias = controladorProPorta.buscarPortarias();

        // Seleciona a primeira (O usuário iria escolher qual desejar) já criando o objeto referencia
        Referencia referencia = new Referencia();
        referencia.setEhCancelamento(true);
        referencia.setReferencia(listaPortarias.get(0));
        referencias.add(referencia);
        parametros.put("referencias", referencias);

        // Cria a lista vazia de recebedoras
        List<Recebedora> recebedoras = new ArrayList<>();

        // Busca as unidades administrativas
        List<UndAdm> listaUndAdm = controladorProPorta.buscarUndAdm();

        // Seleciona a primeira (O usuário iria escolher qual desejar) já criando o objeto recebedora
        Recebedora recebedora = new Recebedora();
        recebedora.setUnidadeRecebedora(listaUndAdm.get(0));
        recebedoras.add(recebedora);
        parametros.put("recebedoras", recebedoras);

        // Busca o expeditor (Usa também o primeiro da lista de unidades administrativas)
        UndAdm undAdmExpedidora = listaUndAdm.get(0);
        parametros.put("expedidora", undAdmExpedidora);

        // Tenta salvar
        Portaria portaria = controladorProPorta.salvar(parametros);

        // Busca os dados pra ver se a portaria foi salva corretamente
        PortariaDAO portariaDAO = new PortariaDAO();
        portaria = portariaDAO.buscar(portaria.getId());

        Assert.assertEquals(assunto, portaria.getAssunto());
        Assert.assertEquals(dtIniVig, portaria.getDtIniVig());
        Assert.assertEquals(dtFimVig, portaria.getDtFimVig());
        Assert.assertEquals(dPublicDou, portaria.getDtPublicDou());
        Assert.assertEquals(horasDesig, portaria.getHorasDesig());
        Assert.assertEquals(resumo, portaria.getResumo());
        Assert.assertEquals(designados, portaria.getDesignados());
        Assert.assertEquals(referencias, portaria.getReferencias());
        Assert.assertEquals(recebedoras, portaria.getUndRecebedora());
        Assert.assertEquals(PortariaStatus.PROPOSTA, portaria.getStatus());
        Assert.assertArrayEquals(arqPdfBytes, portaria.getArqPdf());
    }

    private static void trataDadosPessoa(String dados[]){
        // Instancia os objetos
        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa pessoa = new Pessoa();

        // Preenche o objeto
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
        pessoaDAO.abrirTransacao();
        pessoaDAO.salvar(pessoa);
        pessoaDAO.commitarTransacao();
    }

    private static void trataDadosUndAdm(String dados[]){
        // Instancia os objetos
        UndAdmDAO undAdmDAO = new UndAdmDAO();
        UndAdm undAdm = new UndAdm();

        // Preenche o objeto
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
        undAdmDAO.abrirTransacao();
        undAdmDAO.salvar(undAdm);
        undAdmDAO.commitarTransacao();
    }

    private static void trataDadosRecebedora(String dados[]) throws ParseException {
        // Instancia os objetos
        RecebedoraDAO recebedoraDAO = new RecebedoraDAO();
        Recebedora recebedora = new Recebedora();

        // Preenche o objeto
        recebedora.setId( Long.parseLong(dados[0]) );

        if(!dados[1].isEmpty()){
            Date dtCienciaReeb = formatter.parse(dados[1]);
            recebedora.setDtCienciaReeb(dtCienciaReeb);
        }

        UndAdmDAO undAdmDAO = new UndAdmDAO();
        UndAdm undRecebedora = undAdmDAO.buscar( Long.parseLong(dados[2]) );
        recebedora.setUnidadeRecebedora(undRecebedora);

        // Salva o objeto no banco
        recebedoraDAO.abrirTransacao();
        recebedoraDAO.salvar(recebedora);
        recebedoraDAO.commitarTransacao();
    }

    private static void trataDadosDesignado(String dados[]) throws ParseException{
        // Instancia os objetos
        DesignadoDAO designadoDAO = new DesignadoDAO();
        Designado designado = new Designado();

        // Preenche o objeto
        designado.setId( Long.parseLong(dados[0]) );

        if(!dados[1].isEmpty()){
            Date dtCienciaDesig = formatter.parse(dados[1]);
            designado.setDtCienciaDesig(dtCienciaDesig);
        }

        designado.setDescrFuncDesig(dados[2]);

        if(!dados[3].isEmpty()){
            designado.setHorasDefFuncDesig( Integer.parseInt(dados[3]) );
        }

        if(!dados[4].isEmpty()){
            designado.setHorasExecFuncDesig( Integer.parseInt(dados[4]) );
        }

        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa pessoa = pessoaDAO.buscar( Long.parseLong(dados[5]) );
        designado.setDesignado(pessoa);

        designado.setTipFuncDesig(FuncaoDesig.valueOf(dados[6]));

        // Salva o objeto no banco
        designadoDAO.abrirTransacao();
        designadoDAO.salvar(designado);
        designadoDAO.commitarTransacao();
    }

    private static void trataDadosPortaria(String dados[]) throws ParseException {
        // Instancia os objetos
        PortariaDAO portariaDAO = new PortariaDAO();
        Portaria portaria = new Portaria();

        // Preenche o objeto
        portaria.setId( Long.parseLong(dados[0]) );
        portaria.setAnoId( Integer.parseInt(dados[1]) );
        portaria.setSeqId( Integer.parseInt(dados[2]) );
        portaria.setAssunto(dados[3]);

        if(!dados[4].isEmpty()){
            Date dtExped = formatter.parse(dados[4]);
            portaria.setDtExped(dtExped);
        }

        Date dtIniVig = formatter.parse(dados[5]);
        portaria.setDtIniVig(dtIniVig);

        if(!dados[6].isEmpty()){
            Date dtFimVig = formatter.parse(dados[6]);
            portaria.setDtFimVig(dtFimVig);
        }

        if(!dados[7].isEmpty()){
            Date dtPublicDou = formatter.parse(dados[7]);
            portaria.setDtPublicDou(dtPublicDou);
        }

        if(!dados[8].isEmpty()){
            portaria.setHorasDesig( Integer.parseInt(dados[8]) );
        }

        portaria.setResumo(dados[9]);

        portaria.setTextoCompleto(dados[10]);

        // Verifica se existe arquivo pdf
        if(!dados[11].isEmpty()){
            // Pega o path do projeto
            String dir = System.getProperty("user.dir");
            // Cria o arquivo
            File arquivo = new File(dir+"/src/test/resources/"+dados[11]);
            if(arquivo.exists()){
                // Caso o arquivo exista, tenta gravar os bytes
                try {
                    byte[] fileContent = Files.readAllBytes(arquivo.toPath());
                    portaria.setArqPdf(fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        portaria.setSiglaUndId(dados[12]);

        portaria.setStatus(PortariaStatus.valueOf(dados[13]));

        if(!dados[14].isEmpty()){
            PessoaDAO pessoaDAO = new PessoaDAO();
            Pessoa expeditor = pessoaDAO.buscar( Long.parseLong(dados[14]) );
            portaria.setExpedidor(expeditor);
        }

        if(!dados[15].isEmpty()){
            UndAdmDAO undAdmDAO = new UndAdmDAO();
            UndAdm undAdmExpedidora = undAdmDAO.buscar( Long.parseLong(dados[15]) );
            portaria.setUnidadeExpedidora(undAdmExpedidora);
        }

        if(!dados[16].isEmpty()){
            extrator.setTexto(dados[16]);
            String recebedorasId[] = extrator.getResultado("-");
            List<Recebedora> listaRecebedoras = new ArrayList<>();
            RecebedoraDAO recebedoraDAO = new RecebedoraDAO();

            for(int i = 0; i < recebedorasId.length; i++){
                Recebedora recebedora = recebedoraDAO.buscar( Long.parseLong(recebedorasId[i]) );
                listaRecebedoras.add(recebedora);
            }

            portaria.setUndRecebedora(listaRecebedoras);
        }

        if(!dados[17].isEmpty()){
            extrator.setTexto(dados[17]);
            String designadosId[] = extrator.getResultado("-");
            List<Designado> listaDesignados = new ArrayList<>();
            DesignadoDAO designadoDAO = new DesignadoDAO();

            for(int i = 0; i < designadosId.length; i++){
                Designado designado = designadoDAO.buscar( Long.parseLong(designadosId[i]) );
                listaDesignados.add(designado);
            }

            portaria.setDesignados(listaDesignados);
        }

        // Salva o objeto no banco
        portariaDAO.abrirTransacao();
        portariaDAO.salvar(portaria);
        portariaDAO.commitarTransacao();
    }

}



