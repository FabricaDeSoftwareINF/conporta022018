package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;


public class ControladorEmtRel {

    private PortariaDAO portariaDAO = new PortariaDAO();
    private UndAdmDAO undAdmDAO = new UndAdmDAO();
    private DesignadoDAO designadoDAO = new DesignadoDAO();

    public boolean buscarPortaria(Long id) {
        // Busca a portaria com esse id
        Portaria portaria = this.portariaDAO.buscar(id);

        // Verifica se encontrou a portaria
        try {
            id = portaria.getId();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean buscarUndAdm(Long id) {
        // Busca a unidade com esse id
        UndAdm undAdm = this.undAdmDAO.buscar(id);

        // Verifica se encontrou a unidade
        try {
            id = undAdm.getId();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean buscarDesignado(Long id) {
        // Busca o designado com esse id
        Designado designado = this.designadoDAO.buscar(id);

        // Verifica se encontrou a designado
        try {
            id = designado.getId();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean portariaExiste(Long id) {

        Portaria portaria = portariaDAO.buscar(id);

        if (portaria != null) {
            if (portaria.getStatus() == PortariaStatus.ATIVA) {
                if (!portaria.getDesignados().isEmpty()) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public String getDadosRel(long id) {
        String String = "";
        Portaria portaria = portariaDAO.buscar(id);
        Designado designado = designadoDAO.buscar(id);
        UndAdm undAdm = this.undAdmDAO.buscar(id);
        try {
            if (portaria.getStatus() == PortariaStatus.ATIVA) {
                if (designado.getHorasDefFuncDesig() > 0) {
                    if (portaria.getHorasDesig() > 0) {
                        if (portaria.getDtExped() != null) {
                            if (portaria.getArqPdf().length > 0) {
                                String = "Dados encontrados, prontos para gerar relatorio";
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
                throw new IllegalArgumentException("Não foi encontrado dados para gerar o relatorio");
        }
        return String = "";
    }

}


        



