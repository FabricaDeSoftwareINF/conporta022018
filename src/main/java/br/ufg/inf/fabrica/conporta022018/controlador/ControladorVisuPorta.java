package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.Recebedora;
import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ControladorVisuPorta {
    

    public String conversorPortariaJson(Long id) throws Exception {
       
        Portaria portaria = new PortariaDAO().buscar(id);
        
        if(portaria == null) {
            throw new Exception("ID de portaria inválido"); 
        }
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return     f.getName().equals("assunto")
                                || f.getName().equals("arqPdf")
                                || f.getName().equals("assinatura")
                                || f.getName().equals("seqId")
                                || f.getName().equals("horasDesig")
                                || f.getName().equals("nivel")
                                || f.getName().equals("horasDefFuncDesig")
                                || f.getName().equals("horasExecFuncDesig")
                                || f.getName().equals("ultNumExped")
                                || f.getName().equals("ultNumProp")
                                || f.getName().equals("minInat");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                })
                .create();

        String portariaJson = gson.toJson(portaria);
        return portariaJson;

    }

} 