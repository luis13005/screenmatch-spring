package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.print.DocFlavor;
import java.beans.Encoder;
import java.net.URL;
import java.net.URLEncoder;
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

		var endereco = "https://www.omdbapi.com/?t="+URLEncoder.encode(scanner.nextLine())+"&apikey=8133b1c1";

		System.out.println(endereco);
		ConsumoAPI api = new ConsumoAPI();
		String json = api.retornaFilme(endereco);
		System.out.println(json);

		ConverteDados dado = new ConverteDados();

		DadosSerie serie = dado.ObterDados(json, DadosSerie.class);

		System.out.println(serie);
	}
}
