

package br.ufg.inf.fabrica.conporta022018.controlador;

import java.util.List;

public class ControladorVisuInterf {
    
    List<String> URLsPermitidas = Permissoes.getURLs();
    Gson gson = new Gson();
    String URLsPermitidasJson = gson.toJson(URLsPermitidas);
    
    System.out.println(URLsPermitidasJson); //trocar por return. 

//    ISso vai me ajudar ao fazer os testes;
//    Type type = new TypeToken<List<String>>() {}.getType();
//    List<String> youtube_List = gson.fromJson(youtubeListStr, type);
//    for(String url : youtube_List){
//        System.out.println(url);
//    }
    }
    
    
}
