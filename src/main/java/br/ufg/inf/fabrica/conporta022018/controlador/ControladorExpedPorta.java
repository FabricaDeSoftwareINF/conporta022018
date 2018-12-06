package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Classe responsável por fazer as operações lógicas necessárias para realizar a expedição de portarias.
 *
 * @author RafaelBP
 * @since 1.0
 */
public class ControladorExpedPorta {

    private final short[] chaveAssinatura = new short[]{1, 1, 1, 1, 1};

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
     *     <li>Portaria referenciada - Inexistentes, canceladas ou propostas (não é necessário(?))</li>
     *     <li>Expedidor inválido - Não pertence à unidade administrativa que propôs a portaria</li>
     * </ol>
     */
    public int expedPorta(long idPorta, long idUsuario){

        Portaria portaria = new Portaria();
        PortariaDAO portaDAO = new PortariaDAO();
        Pessoa pessoa = new Pessoa();
        PessoaDAO pessoaDAO = new PessoaDAO();
        UndAdm undAdm = new UndAdm();
        UndAdmDAO undAdmDAO = new UndAdmDAO();
        ControladorCancPortRef controladorCancPortRef = new ControladorCancPortRef();
        ControladorEncPortaria controladorEncPortaria = new ControladorEncPortaria();
        boolean designadosExistem = true;

        pessoa = pessoaDAO.buscar(idUsuario);
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
        if (pessoa.getServidor().getUndAdm().getSiglaUnAdm() == portaria.getUnidadeExpedidora().getSiglaUnAdm()) {
            // O servidor não pertence à unidade administrativa que propôs a portaria
            return 7;
        }
        for(int i = 0; i < portaria.getDesignados().size(); i++){
            if(pessoaDAO.buscar(portaria.getDesignados().get(i).getDesignado().getId()).equals(new Pessoa())){
                designadosExistem = false;
            }
        }
        if (!designadosExistem) {
            // Um ou mais designados não existem
            return 5;
        } else {
            // Caso de sucesso

            // Atualização de dados
            portaria.setSeqId(portaria.getUnidadeExpedidora().getUltNumExped());

            // Incrementa o número de portarias expedidas pela unidade administrativa
            portaria.getUnidadeExpedidora().setUltNumExped(portaria.getUnidadeExpedidora().getUltNumExped() + 1);
            // Alterar o status da portaria para "ativa"
            portaria.setStatus(PortariaStatus.ATIVA);
            // Atribuir a data atual na data de expedição
            portaria.setDtExped(new Date());

            // Assinatura da expedição, persistência da portaria, encaminhamento para ciência e cancelamento de referenciadas
            portaria.setAssinatura(assinar(new Long[]{pessoa.getServidor().getId(), portaria.getId()}));
            controladorEncPortaria.encPortariaCiencia(idPorta);
            controladorCancPortRef.cancelarPortarias(idPorta);
            portaDAO.salvar(portaria);
            // Persistência da unidade expedidora para
            undAdmDAO.salvar(portaria.getUnidadeExpedidora());

            portaDAO.commitarTransacao();
            undAdmDAO.commitarTransacao();
            return 1;
        }
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

        ByteBuffer identificadoresEmBytes = ByteBuffer.allocate(8);
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
}
