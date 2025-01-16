package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
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

        series = serieList.stream()
                        .map(n -> new Serie(n))
                                .collect(Collectors.toList());

        series.stream().sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
//        var json = api.retornaDados(ENDERECO+ URLEncoder.encode(nomeSerie)+API_KEY);
//        DadosSerie serie = converte.ObterDados(json, DadosSerie.class);
//
//        System.out.println(serie);
//
//        List<DadosTemporada> temporadas = new ArrayList<>();
//
//        for (int i = 1; i <= serie.totalTemporadas();i++){
//            json = api.retornaDados(ENDERECO+URLEncoder.encode(nomeSerie)+TEMPORADA+i+API_KEY);
//            System.out.println(json);
//            DadosTemporada temporada = converte.ObterDados(json,DadosTemporada.class);
//            temporadas.add(temporada);
//        }
//        temporadas.forEach(System.out::println);
//        System.out.println("Total temporadas "+temporadas.size());

//        temporadas.forEach(t -> t.episodio().forEach(e -> System.out.println(e.titulo())));

//        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodio().stream()).collect(Collectors.toList());
//
//       List<DadosEpisodio> novaLista = dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .limit(5)
//               .toList();
//
//        System.out.println(novaLista);
//        System.out.println("Escolha um episodio ");
//
//        var usuario = leitura.nextLine();
//
//        Optional<DadosEpisodio> first = novaLista.stream()
//                .filter(e -> e.titulo().contains(usuario))
//                .findFirst();
//
//        System.out.println(first);


//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.episodio().stream()
//                        .map(e -> (new Episodio(t.temporadaNumro(),e))
//                )).collect(Collectors.toList());

//        episodios.forEach(System.out::println);

//        System.out.println("A partir de qual ano deseja ver os episódios");
//        var ano = leitura.nextInt();
//        LocalDate dataBusca = LocalDate.of(ano,1,1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                "Temporada: "+e.getNumeroTemporada()+
//                        " Nome: "+e.getTitulo()+
//                        " Numero: "+e.getNumeroEpisodio()+
//                        " Data Lancamento: "+e.getDataLancamento().format(formatador)
//                ));


//        Map<Integer, Double> mediaTemporada = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getNumeroTemporada,
//                        Collectors.averagingDouble(Episodio::getAvaliacao)));
//
//        System.out.println(mediaTemporada);
//
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
//
//        System.out.println(est);


    }
