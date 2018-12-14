package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Curso;
import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        Date data = calendar.getTime();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dataDeVerdade = formato.format(data);
        data = formato.parse(dataDeVerdade);

        Curso curso = new Curso();

        ControladorDisc controladorDisc = new ControladorDisc();

        controladorDisc.criarDiscente("222.789.333-89", 35,  data, curso);
        controladorDisc.excluirDiscente("222.789.333-89", data);
        controladorDisc.alterarDiscente("764.098.612-09", data, data);
        controladorDisc.buscarDiscente("222.789.333-89");
        controladorDisc.buscarDiscente("764.098.612-09");

    }
}
