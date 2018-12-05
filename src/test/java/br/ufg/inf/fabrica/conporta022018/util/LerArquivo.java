package br.ufg.inf.fabrica.conporta022018.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LerArquivo {

    public List<String> lerArquivo (String nomeArquivo) throws IOException {

        List<String> dadosArquivos = new ArrayList<>();

        BufferedReader br;
        String linha;

        br = new BufferedReader(new FileReader(nomeArquivo));

        while ((linha = br.readLine()) != null) {
            dadosArquivos.add(linha);
        }

        return dadosArquivos;
    }

}