package br.ufg.inf.fabrica.conporta022018.controlador.visuporta;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ControladorVisuPort {
    
    private Portaria portaria;

    public static String conversorPortariaJson(Portaria portaria) {
        
        this.portaria = portaria;
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getName().toLowerCase().contains("modelo")
                                || f.getName().toLowerCase().contains("cor");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                })
                .create();

        String jsoncr = gson.toJson(this.portaria);
        System.out.println(jsoncr);
        return jsoncr;

    }

}

//public class  ControladorVisuPort() {
//    
//    Portaria portaria;
//
//    public String visuPort(Portaria portaria) throws Exception {
//        
//
//        Gson gson = gsonBuilder.create();
//        String portariaJson = gson.toJson(portaria);
//
//        return portariaJson;
//   
//}
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
//            @Override
//            public boolean shouldSkipField(FieldAttributes f) {
//                return f.getName().toLowerCase().contains("permissao");
//            }
//
//            @Override
//            public boolean shouldSkipClass(Class<?> incomingClass) {
//                return false;
//            }
//
//    
//}
//    

