package br.com.alura.ScreenMatch;

import br.com.alura.ScreenMatch.model.DadosSerie;
import br.com.alura.ScreenMatch.service.ConsumoAPI;
import br.com.alura.ScreenMatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoAPI = new ConsumoAPI();
		var json = consumoAPI.getDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");

		ConverteDados converteDados = new ConverteDados();
		DadosSerie dadosSerie = converteDados.getDados(json, DadosSerie.class);

		System.out.println(dadosSerie);
	}
}
