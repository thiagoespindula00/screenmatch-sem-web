package br.com.alura.ScreenMatch;

import br.com.alura.ScreenMatch.model.DadosSerie;
import br.com.alura.ScreenMatch.model.DadosTemporada;
import br.com.alura.ScreenMatch.service.ConsumoAPI;
import br.com.alura.ScreenMatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	private Scanner scanner = new Scanner(System.in);
	final int OPCAO_MENU_SAIR = 0;
	final int OPCAO_MENU_BUSCAR_SERIE_PELO_NOME = 1;
	final int OPCAO_MENU_MOSTRAR_DADOS_SERIE = 2;
	final int OPCAO_MENU_MOSTRAR_DADOS_TEMPORADAS = 3;

	final String ENDERECO = "https://www.omdbapi.com/?";
	final String API_KEY = "apikey=6585022c";
	String urlAPI = "";
	ConsumoAPI consumoAPI = new ConsumoAPI();
	ConverteDados converteDados = new ConverteDados();
	String json = "";

	public void exibeMenu() {
		System.out.println(OPCAO_MENU_BUSCAR_SERIE_PELO_NOME + " - Buscar serie pelo nome");
		System.out.println(OPCAO_MENU_MOSTRAR_DADOS_SERIE + " - Mostrar dados serie");
		System.out.println(OPCAO_MENU_MOSTRAR_DADOS_TEMPORADAS + " - Mostrar dados temporadas");
		System.out.println(OPCAO_MENU_SAIR + " - Sair");
		System.out.println("Digite a opcao desejada");
	}

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		DadosSerie dadosSerie = null;
		List<DadosTemporada> temporadas = new ArrayList<>();

		int opcaoMenu = 0;
		do {
			exibeMenu();
			opcaoMenu = scanner.nextInt();
			switch (opcaoMenu) {
				case OPCAO_MENU_BUSCAR_SERIE_PELO_NOME:
				{
					System.out.println("Digite o nome da serie desejada");
					scanner.nextLine();
					String nomeSerie = scanner.nextLine();
					urlAPI = ENDERECO + API_KEY + "&t=" + nomeSerie.replace(" ", "+");
					json = consumoAPI.getDados(urlAPI);
					dadosSerie = converteDados.getDados(json, DadosSerie.class);

					temporadas.clear();
					for (int idx = 1; idx <= dadosSerie.totalTemporadas(); idx++) {
						json = consumoAPI.getDados(urlAPI + "&season=" + idx);
						DadosTemporada temporada =converteDados.getDados(json, DadosTemporada.class);
						temporadas.add(temporada);
					}
				}
				break;

				case OPCAO_MENU_MOSTRAR_DADOS_SERIE:
					if (dadosSerie == null) {
						System.out.println("Nenhuma série cadastrada!");
						break;
					}
					System.out.println(dadosSerie);
					break;

				case OPCAO_MENU_MOSTRAR_DADOS_TEMPORADAS:
					if (temporadas.size() == 0) {
						System.out.println("Nenhuma temporada cadastrada!");
						break;
					}

					for (DadosTemporada temporada : temporadas) {
						System.out.println(temporada);
						System.out.println();
					}

					break;
			}
		}
		while (opcaoMenu != OPCAO_MENU_SAIR);
	}
}
