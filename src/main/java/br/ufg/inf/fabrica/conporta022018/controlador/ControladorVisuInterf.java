package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Perfil;
import br.ufg.inf.fabrica.conporta022018.modelo.Permissao;
import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.Recebedora;
import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;


public class ControladorVisuInterf {
    
    
    private static final ControladorConAcess controladorConAcess = new ControladorConAcess();

    public String exibidorURL(Pessoa pessoa) throws Exception {
        
        List<Perfil> perfils = controladorConAcess.buscarPerfil(pessoa);
        List<Permissao> urls = new ArrayList<>();
        
        for (Perfil perfil : perfils) {
            urls.add((Permissao) perfil.getPermissoes());
        }
      
        
        if(urls == null || urls.size() <= 0) {
            throw new Exception("Perfil Inválido"); 
        }
        
        Gson gson = new Gson();
        String urlJson = gson.toJson(urls);
        return urlJson;

    }

} 