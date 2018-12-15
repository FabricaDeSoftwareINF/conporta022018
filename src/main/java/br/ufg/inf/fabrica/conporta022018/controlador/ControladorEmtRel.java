package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;
import net.bytebuddy.asm.Advice;

import java.text.ParseException;
import java.util.List;


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


    public boolean BuscarListaDesignado(){

        try{

        DesignadoDAO designadoDAO = new DesignadoDAO();
        Portaria portariaDAO = new Portaria();
        List<Designado> listaDesignados = designadoDAO.buscarTodos();

        Long id = listaDesignados.get(listaDesignados.size()).getId();

        boolean existe = portariaDAO.getId() == id;

         return existe;
            }catch (Exception e){
            throw new IllegalArgumentException("Erro ao buscar dados de designados");
     }
  }

    public boolean BuscarListaPortaria(){

        try{

            PortariaDAO portariaDAO = new PortariaDAO();
            Portaria portaria = new Portaria();
            List<Portaria> listaPortarias = portariaDAO.buscarTodos();

            Long id = listaPortarias.get(listaPortarias.size()).getId();

            boolean existe = portaria.getId() == id;

            return existe;
        }catch (Exception e){
            throw new IllegalArgumentException("Erro ao buscar dados de designados");
        }
    }

    public boolean BuscarListaUndAdm(){

        try{

            UndAdmDAO undAdmDAO = new UndAdmDAO();
            Portaria portaria = new Portaria();
            List<UndAdm> listaUnAdm = undAdmDAO.buscarTodos();

            Long id = listaUnAdm.get(listaUnAdm.size()).getId();

            boolean existe = portaria.getId() == id;

            return existe;

            }catch (Exception e){
            throw new IllegalArgumentException("Erro ao buscar dados de designados");
        }
    }

    public boolean buscarUndAdm(Long id) {

        UndAdm undAdm = this.undAdmDAO.buscar(id);
        try {
            id = undAdm.getId();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean buscarDesignado(Long id) {
        Designado designado = this.designadoDAO.buscar(id);

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


        



