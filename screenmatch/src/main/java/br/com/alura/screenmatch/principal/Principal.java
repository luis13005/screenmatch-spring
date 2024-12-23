package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoAPI api = new ConsumoAPI();
    private ConverteDados converte = new ConverteDados();

    private final String ENDERECO   = "https://omdbapi.com/?t=";
    private final String API_KEY    = "&apikey=8133b1c1";
    private final String TEMPORADA  = "&season=";

    public void exibeSerie(){

        System.out.println("Digite a serie desejada: ");

        var nomeSerie = leitura.nextLine();

        var json = api.retornaDados(ENDERECO+ URLEncoder.encode(nomeSerie)+API_KEY);
        DadosSerie serie = converte.ObterDados(json, DadosSerie.class);

        System.out.println(serie);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= serie.totalTemporadas();i++){
            json = api.retornaDados(ENDERECO+URLEncoder.encode(nomeSerie)+TEMPORADA+i+API_KEY);
            System.out.println(json);
            DadosTemporada temporada = converte.ObterDados(json,DadosTemporada.class);
            temporadas.add(temporada);
        }
        temporadas.forEach(System.out::println);
        System.out.println("Total temporadas "+temporadas.size());

//        temporadas.forEach(t -> t.episodio().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodio().stream()).collect(Collectors.toList());

        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

//        dadosEpisodios.forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodio().stream()
                        .map(e -> (new Episodio(t.temporadaNumro(),e))
                )).collect(Collectors.toList());

        episodios.forEach(System.out::println);
    }
}
