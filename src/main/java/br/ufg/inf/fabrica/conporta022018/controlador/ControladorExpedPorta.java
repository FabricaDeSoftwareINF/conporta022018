package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe responsável por fazer as operações lógicas necessárias para realizar a expedição de portarias.
 *
 * @author RafaelBP
 * @since 1.0
 */
public class ControladorExpedPorta {

    PortariaDAO portaDAO;
    PessoaDAO pessoaDAO;
    UndAdmDAO undAdmDAO;

    private final short[] chaveAssinatura = new short[]{1, 1, 1, 1, 1};
    private final String caracteresHexadecimais = "0123456789ABCDEF";

    public ControladorExpedPorta() {
        portaDAO = new PortariaDAO();
        pessoaDAO = new PessoaDAO();
        undAdmDAO = new UndAdmDAO();
    }

    /**
     * Expede uma portaria com status "Proposta", registrando o expedidor que fez a operação, por meio de uma assinatura
     * eletrônica, e a data de expedição. Também atualiza o identificador lógico para um identificador de portaria
     * expedida.
     *
     * @param idPorta Identificador da portaria a ser expedida
     * @return O código indicando o resultado da operação. Os casos correspondentes a cada código são como segue:<br>
     * <ol>
     *     <li>Caso de sucesso</li>
     *     <li>Caso alternativo(portaria removida durante a expedição) - Deve ser chamado o caso de uso ProPorta</li>
     *     <li>Portaria inválida - Cancelada ou Ativa</li>
     *     <li>Portaria inválida - Período de vigência expirado</li>
     *     <li>Designado inválido - Inexistente</li>
     *     <li>Portaria referenciada - Inexistentes, canceladas ou propostas </li>
     *     <li>Expedidor inválido - Não pertence à unidade administrativa que propôs a portaria</li>
     * </ol>
     */
    public int expedPorta(long idPorta, long idUsuario){

        Portaria portaria, referenciada;
        Pessoa expedidor, designado;

        ControladorCancPortRef controladorCancPortRef = new ControladorCancPortRef();
        ControladorEncPort controladorEncPortaria = new ControladorEncPort();

        pessoaDAO.abrirTransacao();
        portaDAO.abrirTransacao();

        expedidor = pessoaDAO.buscar(idUsuario);
        portaria = portaDAO.buscar(idPorta);

        // Portaria inexistente, a interface deve perguntar ao usuário se ele deseja criar uma portaria
        if(portaria.equals(new Portaria())){
            return 2;
        }

        // Verificação de cenários inválidos
        if (!portaria.getStatus().equals(PortariaStatus.PROPOSTA)) {
            // O status da portaria não é "proposta", a portaria já foi expedida, cancelada ou expirada
            return 3;
        }
        if (portaria.getDtFimVig() != null && portaria.getDtFimVig().before(new Date())) {
            // A data final do período de vigência já expirou
            return 4;
        }
        if (!expedidor.getServidor().getUndAdm().getSiglaUnAdm().
                equals(portaria.getSiglaUndId())) {
            // O servidor não pertence à unidade administrativa que propôs a portaria
            return 7;
        }

        for(int i = 0; i < portaria.getReferencias().size(); i++){
            referenciada = portaDAO.buscar(portaria.getReferencias().get(i).getPortariaReferenciada().getId());
            if(referenciada.equals(new Portaria()) ||
                    referenciada == null ||
                    !portaria.getStatus().equals(PortariaStatus.ATIVA)){
                // Uma das portarias referenciadas não existe ou não está ativa
                return 6;
            }
        }
        for(int i = 0; i < portaria.getDesignados().size(); i++){
            designado = pessoaDAO.buscar(portaria.getDesignados().get(i).getDesignado().getId());
            if(designado.equals(new Pessoa()) || designado == null){
                // Um dos designados não existe
                return 5;
            }
        }

        // Caso de sucesso

        // Atualização de dados
        portaria.setSeqId(expedidor.getServidor().getUndAdm().getUltNumExped());
        portaria.setAnoId(expedidor.getServidor().getUndAdm().getAnoPort());

        // Incrementa o número de portarias expedidas pela unidade administrativa
        expedidor.getServidor().getUndAdm().setUltNumExped(expedidor.getServidor().getUndAdm().getUltNumExped() + 1);
        // Alterar o status da portaria para "ativa"
        portaria.setStatus(PortariaStatus.ATIVA);
        // Atribuir a data atual na data de expedição
        portaria.setDtExped(new Date());

        // Assinatura da expedição, persistência da portaria, encaminhamento para ciência e cancelamento de referenciadas
        portaria.setAssinatura(assinar(new Long[]{expedidor.getServidor().getId(), portaria.getId()}));
        portaria.setExpedidor(expedidor);
        controladorEncPortaria.encPortariaCiencia(portaria);
        controladorCancPortRef.cancelarPortariaReferenciada(idPorta);

        portaDAO.salvar(portaria);
        undAdmDAO.salvar(expedidor.getServidor().getUndAdm());
        portaDAO.commitarTransacao();
        undAdmDAO.commitarTransacao();
        return 1;
    }


    /**
     * Faz a assinatura de uma portaria expedida, concatenando os identificadores recebidos e fazendo operações de
     * deslocamento de bits no vetor de bytes resultante da concatenação. Estas operações são feitas baseadas na chave,
     * definida no atributo chaveAssinatura, que deve ter como número de posições o mesmo número de bytes necessários na
     * assinatura, atualmente 5.
     *
     * @param identificadores Identificadores que serão incluídos na assinatura. Atualmente são recebidos os
     *                        identificadores do expedidor e da portaria.
     * @return Cadeia de caracteres que representa o código hexadecimal da assinatura gerada.
     */
    public String assinar(Long[] identificadores){

        ByteBuffer identificadoresEmBytes = ByteBuffer.allocate(16);
        ByteBuffer assinaturaEmBytes = ByteBuffer.allocate(5);

        /* Retira os dois primeiros bytes do identificador do expedidor e o
            primeiro byte do identificador da portaria*/
        for(int i = 0; i < identificadores.length; i++){
            identificadoresEmBytes.putLong(identificadores[i]);
        }
        assinaturaEmBytes.put(identificadoresEmBytes.get(6));
        assinaturaEmBytes.put(identificadoresEmBytes.get(7));
        assinaturaEmBytes.put(identificadoresEmBytes.get(13));
        assinaturaEmBytes.put(identificadoresEmBytes.get(14));
        assinaturaEmBytes.put(identificadoresEmBytes.get(15));

        for(int i = 0; i < assinaturaEmBytes.capacity(); i++){
            assinaturaEmBytes.put(i,
                    rotacionaByte(assinaturaEmBytes.get(i), chaveAssinatura[i]));
        }

        // conversão para hexadecimal e depois para char
        StringBuilder sb = new StringBuilder();
        for (byte b : assinaturaEmBytes.array()) {
            sb.append(String.format("%02X ", b));
        }

        /* A cadeia de caracteres gerada possui espaços entre cada par de caracteres hexadecimais, portanto é necessário
        retirar todos os espaços em branco da assinatura.
        String.trim() não funcionou, por algum motivo desconhecido, portanto foi usado String.replace()*/
        return sb.toString().replace(" ", "");
    }

    /**
     * Faz a autenticação de uma portaria expedida, descriptografando o código de assinatura informado e recuperando as
     * informações da portaria e do expedidor (usuário), verificando se o expedidor da portaria expedida confere com o
     * expedidor recuperado.
     *
     * @param assinatura Código de assinatura eletrônica gerada para a portaria no momento de expedição.
     * @return Mensagem de resultado da autenticação, sendo que os possíveis valores são:
     * <ul>
     *     <li>"O código é inválido." (O código não é hexadecimal)</li>
     *     <li>"O código está incorreto." (Os identificadores não correspondem com nenhum registro de portaria ou
     *          usuário, a portaria não foi expedida ou o expedidor da portaria encontrada não corresponde com o
     *          expedidor encontrado)</li>
     *     <li>"Esta portaria foi expedida no dia [Dia] de [Mes] de [Ano] por [Expedidor]" (Caso de sucesso)</li>
     * </ul>
     */
    public String autenticar(String assinatura){

        for(char caractereAssinatura : assinatura.toCharArray()){
            if(!caracteresHexadecimais.contains("" + caractereAssinatura)){
                return "O código é inválido.";
            }
        }

        String resultado;
        char[] assinaturaEmChar = assinatura.toCharArray();
        ByteBuffer identificadoresEmBytes = ByteBuffer.allocate(16);
        ByteBuffer assinaturaEmBytes = ByteBuffer.allocate(5);

        //  conversao de texto hexadecimal para byte
        for (int i = 0; i < assinaturaEmChar.length; i += 2){
            assinaturaEmBytes.put(hexToByte(assinatura.substring(i, i + 1)));
        }

        // rotaciona os bytes para a posição original para adquirir os bytes com os identificadores
        for(int i = 0; i < assinaturaEmBytes.capacity(); i++){
            assinaturaEmBytes.put(i, rotacionaByte(assinaturaEmBytes.get(i), (short) (8 - chaveAssinatura[i])));
        }

        identificadoresEmBytes.putLong(0);
        identificadoresEmBytes.put(6, assinaturaEmBytes.get(0));
        identificadoresEmBytes.put(7, assinaturaEmBytes.get(1));
        identificadoresEmBytes.put(13, assinaturaEmBytes.get(2));
        identificadoresEmBytes.put(14, assinaturaEmBytes.get(3));
        identificadoresEmBytes.put(15, assinaturaEmBytes.get(4));

        portaDAO.abrirTransacao();
        pessoaDAO.abrirTransacao();

        Portaria portariaAuth = portaDAO.buscar(identificadoresEmBytes.getLong());
        Pessoa expedidorAuth = pessoaDAO.buscar(identificadoresEmBytes.getLong());

        portaDAO.commitarTransacao();
        pessoaDAO.commitarTransacao();
        
        if (portariaAuth.equals(new Portaria()) || portariaAuth == null ||
                expedidorAuth.equals(new Pessoa()) || expedidorAuth == null) {
            resultado = "O código está incorreto.";
        } else if(portariaAuth.getStatus().equals(PortariaStatus.PROPOSTA)){
            resultado = "O código está incorreto.";
        } else if(portariaAuth.getExpedidor().equals(expedidorAuth)){
            Calendar diaEmCalendar = Calendar.getInstance();
            diaEmCalendar.setTime(portariaAuth.getDtExped());

            resultado = "Esta portaria foi expedida no dia " +
                    diaEmCalendar.get(Calendar.DAY_OF_MONTH) + " de " +
                    diaEmCalendar.get(Calendar.MONTH) + " de " +
                    diaEmCalendar.get(Calendar.YEAR) + " por " +
                    portariaAuth.getExpedidor().getNomePes() + ".";
        } else {
            resultado = "O código está incorreto.";
        }

        return resultado;
    }

    /**
     * Faz a rotaçao de um byte recebido para a direita, sendo que o bit da extrema direita vai para a posição da
     * extrema esquerda.
     *
     * @param byteARotacionar Byte recebido pela função que será rotacionado o número de vezes desejado.
     * @param numRotacoes Número de vezes que o byte deve ser rotacionado.
     * @return Byte depois da aplicação das rotações desejadas.
     */
    private byte rotacionaByte(byte byteARotacionar, short numRotacoes){

        short byteParaShort;

        for (int i = 0; i < numRotacoes; i++) {

            // armazena o byte original. O cast deve ser feito para verificação de par ou ímpar posterior.
            byteParaShort = (short) byteARotacionar;

            // rotaciona o byte original. O cast deve ser feito porque a operação bitwise converte o byte para inteiro.
            byteARotacionar = (byte) (byteARotacionar >> 1);

            // se o último bit do byte original for 1...
            if ((byteParaShort % 2) != 0 && byteParaShort > 0 ||
                    (byteParaShort % 2) == 0 && byteParaShort < 0){
                // joga este bit para a primeira posição, efetuando a rotação
                byteARotacionar += 128;
            }
        }

        return byteARotacionar;
    }

    private byte hexToByte(String byteEmString){
        int hexEmByte = (Character.digit(byteEmString.charAt(0), 16) << 4) +
                Character.digit(byteEmString.charAt(1), 16);
        return (byte) hexEmByte;
    }
}
