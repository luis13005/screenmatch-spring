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
    private List<Serie> series = new ArrayList<>();

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
                    4 - Buscar Série por Titulo
                    5 - Buscar Série por Ator
                    6 - Buscar top 5
                    7 - Buscar por Categoria
                    8 - Temporadas menores ou igual á
                    
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

                    case 4:
                buscarSeriePorTitulo();
                    break;

                case 5:
                    buscarSeriePorAtor();
                    break;

                case 6:
                    buscarTop5();
                    break;

                case 7:
                    buscarCategoria();
                    break;

                case 8:
                    buscarTemporadasMenor();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarTemporadasMenor() {
        System.out.println("Digite o maximo de temporadas: ");
        var total = leitura.nextInt();

        System.out.println("Avaliação maior que: ");
        var avaliacaoTotal = leitura.nextDouble();

        Optional<Serie> totalTemporada = serieRepository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(total,avaliacaoTotal);

        if (totalTemporada.isPresent()){
            totalTemporada.stream()
                    .forEach(s -> System.out.println("Temporadas: "+s.getTotalTemporadas()+" Avaliação: "+s.getAvaliacao()+" Série: "+s.getTitulo()));
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
            seriesBuscadas();

            System.out.println("Digite o nome da série: ");
            var nomeSerie = leitura.nextLine();

            Optional<Serie> serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

            if(serieBuscada.isPresent()) {
                Serie serie = serieBuscada.get();

                List<DadosTemporada> temporadas = new ArrayList<>();

                for (int i = 1; i <= serie.getTotalTemporadas(); i++) {


                    String urlEpisodio = ENDERECO + URLEncoder.encode(serie.getTitulo()) + TEMPORADA + i + API_KEY;
                    var json = api.retornaDados(urlEpisodio);
                    DadosTemporada temporada = converte.ObterDados(json, DadosTemporada.class);
                    temporadas.add(temporada);
                    System.out.println(temporada);
                }

                List<Episodio> episodios = temporadas.stream()
                        .flatMap(s -> s.episodio().stream()
                                .map(e -> new Episodio(s.temporadaNumero(),e)))
                        .collect(Collectors.toList());

                serie.setEpisodios(episodios);

                serieRepository.save(serie);

            }else{
                System.out.println("Serie não encontrada");
            }
        }

    private void seriesBuscadas(){
            series = serieRepository.findAll();

            series.stream().sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
        }

    private void buscarSeriePorTitulo() {
        System.out.println("\nDigite a Série desejada: ");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()){
            System.out.println("Dados da Série: "+serieBuscada.get());
        }else {
            System.out.println("Serie não encontrada!");
        }
    }

    private void buscarSeriePorAtor() {
        System.out.println("Escreva o nome do Ator: ");
        var atorNome = leitura.nextLine();

        System.out.println("Digite a avaliação: ");
        var avaliacaoSerie = leitura.nextDouble();

        Optional<Serie> serieBuscada = serieRepository
                .findSerieByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(atorNome,avaliacaoSerie);

        if (serieBuscada.isPresent()){
            System.out.println("O ator "+atorNome+" Participou das Séries: ");
            serieBuscada.stream().forEach(s ->
                            System.out.println( s.getTitulo()+ " Avaliação: "+s.getAvaliacao()));
        }
    }
    private void buscarTop5() {
        Optional<Serie> serieTop5 = serieRepository.findTop5ByOrderByAvaliacaoDesc();

        serieTop5.stream()
                .forEach(s -> System.out.println(s.getTitulo()+ " Avaliação: "+s.getAvaliacao()));
    }

    private void buscarCategoria() {
        System.out.println("Digite o Genero: ");
        var generoNome = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(generoNome);

        Optional<Serie> serieOptional = serieRepository.findByGenero(categoria);

        if (serieOptional.isPresent()){
            serieOptional.stream()
                    .forEach(s -> System.out.println(s.getTitulo()+ " Genero: "+s.getGenero()));
        }
    }
}