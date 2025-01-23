package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.format.datetime.DateFormatter;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private List<DadosSerie> serieList = new ArrayList<>();
    private SerieRepository serieRepository;

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void exibeSerie() {
        var opcao = -1;
        while (opcao != 0) {
            System.out.println("""
                    
                    1 - Buscar Série
                    2 - Buscar Episódio
                    3 - Mostrar Pesquisa
                    
                    0 - sair
                    """);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerie();
                    break;
                case 2:
                    buscarEpisodio();
                    break;
                case 3:
                    seriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }
        private void buscarSerie(){
            Serie serie = new Serie(buscarDados());

            serieRepository.save(serie);
            System.out.println(serie.toString());
        }

        private DadosSerie buscarDados() {
            System.out.println("\nDigite a Série desejada: ");
            var nomeSerie = leitura.nextLine();
            String url = ENDERECO+URLEncoder.encode(nomeSerie)+API_KEY;

            var json = api.retornaDados(url);
            DadosSerie dadosSerie = converte.ObterDados(json, DadosSerie.class);

           boolean verificaSerie = serieList.stream().anyMatch(n -> n.titulo().toLowerCase().equals(nomeSerie.toLowerCase()));

            if(!verificaSerie) {
                serieList.add(dadosSerie);
            }

            return dadosSerie;
        }

        private void buscarEpisodio(){
            DadosSerie serie = buscarDados();

            for (int i = 1; i <= serie.totalTemporadas(); i++) {
                String urlEpisodio = ENDERECO+URLEncoder.encode(serie.titulo())+TEMPORADA+i+API_KEY;
                var json = api.retornaDados(urlEpisodio);
                DadosTemporada temporada = converte.ObterDados(json, DadosTemporada.class);
                System.out.println(temporada);
            }
        }

    private void seriesBuscadas(){
        List<Serie> series = new ArrayList<>();

        series = serieRepository.findAll();

        series.stream().sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
    }
