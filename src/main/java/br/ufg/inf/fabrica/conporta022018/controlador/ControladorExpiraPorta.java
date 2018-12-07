/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.*;

import java.util.List;
import java.util.Calendar

public class ControladorExpiraPorta {

    private PortariaDAO portariaDAO;

    public ControladorExpiraPorta() {
        this.portariaDAO = new PortariaDAO();
    }

    public List<Portaria> buscarPortariasAExpirar(){
        //busca as portarias cuja a data de expiração seja igual a data atual

        String query = "SELECT p FROM Portaria WHERE Portaria.Status = status AND p.dtFimVig = data";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", PortariaStatus.Ativa);
        params.put("data", Calendar.getInstance().getTime());

        List<Portaria> portariasExpirando = portariaDAO.pesquisarJPQLCustomizada(query, params);

        return portariasExpirando;

    }

    public void expirarPortarias (List<Portaria> portariasAExpirar){
        //Altera o status das portarias que estão próximas a expirar
        portariaDAO.abrirTransacao();
        for(int i = 0; i < portariasAExpirar.size(); i++){
	        portariasAExpirar.get(i).setStatus(PortariaStatus.Expirada);
	        portariaDAO.salvar(portariasAExpirar.get(i));
        }
            portariaDAO.commitarTransacao();
    }
}
