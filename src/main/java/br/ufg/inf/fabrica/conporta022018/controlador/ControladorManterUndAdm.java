/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.fabrica.conporta022018.controlador;
import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;
import javax.persistence.NoResultException;

import java.util.HashMap;
import java.util.Map;

public class ControladorManterUndAdm {

    private final int TEMPO_MAXIMO = 60;
    private final int TEMPO_MINIMO = 15;
    private final String JPQL_BUSCAR_UNIDADE = "SELECT u FROM UndAdm u WHERE u.siglaUnAdm = :sigla";
    private UndAdm undAdm;
    private UndAdmDAO dao;
    private Map<String, Object> map;

    public ControladorManterUndAdm() {
        this.dao = new UndAdmDAO();
        this.undAdm = new UndAdm();
        this.map = new HashMap<>();
    }

    public void editarTimeOut(int minInat, String sigla) throws Exception {
        try {
            this.map.put("sigla", sigla);
            this.undAdm = dao.pesquisarUmJPQLCustomizada(JPQL_BUSCAR_UNIDADE, map);

            if (minInat <= this.TEMPO_MAXIMO && minInat >= this.TEMPO_MINIMO) {
                undAdm.setMinInat(minInat);
                dao.abrirTransacao();
                try {
                    dao.salvar(undAdm);
                    dao.commitarTransacao();
                } catch (Exception exc) {
                    dao.rollBackTransacao();
                }
            }else {
                throw new Exception("Tempo Inválido! Por favor, Digite um Tempo para inatividade entre 15 e 60 " +
                        "segundos, inclusive-os.");
            }
        } catch (NoResultException e) {
            throw new Exception("A Unidade informada não foi localizada.");
        }
    }
}
