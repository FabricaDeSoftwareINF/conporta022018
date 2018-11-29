/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.conporta022018.controlador.visuporta;

import com.google.gson.Gson;
import java.io.Serializable;

/**
 *
 * @author David Matheus
*/
public class ControladorVisuPorta {
    
   
    public static void main(String[] args) {
    String jsonPortaria;
   

   Carro carro = new Carro();
    Gson gson = new Gson();
   String jsoncr = gson.toJson(carro);
  
        }
    
        
    public static class Carro implements Serializable { 
    String modelo = "\\dd";
    String cor = "\\dd";       
    int cavalos = 3;
    String motor = "\\dd";
    }  
}
    


