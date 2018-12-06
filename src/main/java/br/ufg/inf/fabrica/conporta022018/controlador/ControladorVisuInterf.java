package br.ufg.inf.fabrica.conporta022018.controlador;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

public class ControladorVisuInterf {
    
    public static void main(String[] args) {
        
    }
    
    public static String visuInterf() {
        
        
        
        Gson gson = new Gson();
        String permissoesJson = gson.toJson(buscarPermissao(usuario));
        return permissoesJson;

}

    
}
