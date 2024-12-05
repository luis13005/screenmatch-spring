package br.com.alura.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String retornaFilme(String endereco){

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();

        HttpResponse<String> response = null;
        try{
            response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        String json = response.body();
        return json;
    }
}
