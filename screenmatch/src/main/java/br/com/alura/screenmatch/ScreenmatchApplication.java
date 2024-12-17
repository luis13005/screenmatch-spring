package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.print.DocFlavor;
import java.beans.Encoder;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o filme que deseja");

		var escolha = URLEncoder.encode(scanner.nextLine());
		var endereco = "https://www.omdbapi.com/?t="+escolha+"&apikey=8133b1c1";

		ConsumoAPI api = new ConsumoAPI();
		var json = api.retornaDados(endereco);

		ConverteDados dado = new ConverteDados();

		DadosSerie serie = dado.ObterDados(json, DadosSerie.class);
		System.out.println(serie);

		json = api.retornaDados("https://omdbapi.com/?t="+escolha+"&season=1&episode=2&apikey=8133b1c1");
		DadosEpisodio episodio = dado.ObterDados(json , DadosEpisodio.class);
		System.out.println(episodio);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<= serie.totalTemporadas(); i++){
			json = api.retornaDados("https://omdbapi.com/?t="+escolha+"&season="+i+"&apikey=8133b1c1");
			DadosTemporada temporada = dado.ObterDados(json, DadosTemporada.class);
			temporadas.add(temporada);
		}
		temporadas.forEach(System.out::println);
	}
}
