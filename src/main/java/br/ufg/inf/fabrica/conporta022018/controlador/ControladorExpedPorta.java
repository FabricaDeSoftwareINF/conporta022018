package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;

import java.nio.ByteBuffer;

/**
 * Classe responsável por fazer as operações lógicas necessárias para realizar a expedição de portarias.
 *
 * @author RafaelBP
 * @since 1.0
 */
public class ControladorExpedPorta {

    private final short[] chaveAssinatura = new short[]{1, 1, 1, 1, 1};

    public int expedPorta(int idPorta){

        Portaria portaria = new Portaria();
        PortariaDAO portaDAO = new PortariaDAO();

        
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
    public char[] assinar(Integer[] identificadores){

        ByteBuffer identificadoresEmBytes = ByteBuffer.allocate(8);
        ByteBuffer assinaturaEmBytes = ByteBuffer.allocate(5);

        /* Retira os dois primeiros bytes do identificador do expedidor e o
            primeiro byte do identificador da portaria*/
        for(int i = 0; i < identificadores.length; i++){
            identificadoresEmBytes.putInt(identificadores[i]);
        }
        assinaturaEmBytes.put(identificadoresEmBytes.get(2));
        assinaturaEmBytes.put(identificadoresEmBytes.get(3));
        assinaturaEmBytes.put(identificadoresEmBytes.get(5));
        assinaturaEmBytes.put(identificadoresEmBytes.get(6));
        assinaturaEmBytes.put(identificadoresEmBytes.get(7));

        for(int i = 0; i < assinaturaEmBytes.capacity(); i++){
            assinaturaEmBytes.put(i,
                    rotacionaByte(assinaturaEmBytes.get(i), chaveAssinatura[i]));
        }

        // conversão para hexadecimal e depois para char
        StringBuilder sb = new StringBuilder();
        for (byte b : assinaturaEmBytes.array()) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim().toCharArray();
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
