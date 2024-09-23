package br.com.alura.ScreenMatch;

import br.com.alura.ScreenMatch.model.DadosEpisodio;
import br.com.alura.ScreenMatch.model.DadosSerie;
import br.com.alura.ScreenMatch.model.DadosTemporada;
import br.com.alura.ScreenMatch.model.Episodio;
import br.com.alura.ScreenMatch.service.ConsumoAPI;
import br.com.alura.ScreenMatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
//		DadosSerie dadosSerie = null;
//		List<DadosTemporada> temporadas = new ArrayList<>();
//
//		int opcaoMenu = 0;
//		do {
//			exibeMenu();
//			opcaoMenu = scanner.nextInt();
//			switch (opcaoMenu) {
//				case OPCAO_MENU_BUSCAR_SERIE_PELO_NOME:
//				{
//					System.out.println("Digite o nome da serie desejada");
//					scanner.nextLine();
//					String nomeSerie = scanner.nextLine();
//					urlAPI = ENDERECO + API_KEY + "&t=" + nomeSerie.replace(" ", "+");
//					json = consumoAPI.getDados(urlAPI);
//					dadosSerie = converteDados.getDados(json, DadosSerie.class);
//
//					temporadas.clear();
//					for (int idx = 1; idx <= dadosSerie.totalTemporadas(); idx++) {
//						json = consumoAPI.getDados(urlAPI + "&season=" + idx);
//						DadosTemporada temporada =converteDados.getDados(json, DadosTemporada.class);
//						temporadas.add(temporada);
//					}
//				}
//				break;
//
//				case OPCAO_MENU_MOSTRAR_DADOS_SERIE:
//					if (dadosSerie == null) {
//						System.out.println("Nenhuma série cadastrada!");
//						break;
//					}
//					System.out.println(dadosSerie);
//					break;
//
//				case OPCAO_MENU_MOSTRAR_DADOS_TEMPORADAS:
//					if (temporadas.size() == 0) {
//						System.out.println("Nenhuma temporada cadastrada!");
//						break;
//					}
//
//					for (DadosTemporada temporada : temporadas) {
//						System.out.println(temporada);
//						System.out.println();
//					}
//
//					break;
//			}
//		}
//		while (opcaoMenu != OPCAO_MENU_SAIR);


//		List<String> nomes = Arrays.asList("Jacque", "Paulo", "Nico", "Iasmin", "Rodrigo", "Nazaré", "Pedro", "Angel", "Nelson");
//
//		nomes.stream()
//				.sorted()
//				.filter(elemento -> elemento.startsWith("N"))
//				.map(elemento -> elemento.toUpperCase())
//				.limit(3)
//				.forEach(System.out::println);


        DadosSerie dadosSerie = null;
        List<DadosTemporada> temporadas = new ArrayList<>();
        // System.out.println("Digite o nome da serie desejada");
        String nomeSerie = "Game of Thrones"; //scanner.nextLine();
        urlAPI = ENDERECO + API_KEY + "&t=" + nomeSerie.replace(" ", "+");
        json = consumoAPI.getDados(urlAPI);
        System.out.println("deu boa");
        dadosSerie = converteDados.getDados(json, DadosSerie.class);

        for (int idx = 1; idx <= dadosSerie.totalTemporadas(); idx++) {
            json = consumoAPI.getDados(urlAPI + "&season=" + idx);
            DadosTemporada temporada = converteDados.getDados(json, DadosTemporada.class);
            temporadas.add(temporada);
        }


        // temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        // Joga todos os espisodios dentro do listTodosEpisodios
        List<DadosEpisodio> listaTodosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .toList(); // Transforma em lista e não deixa adicionar novos elementos dentro da lista

//        System.out.println("Top 10 episodios");
//        listaTodosEpisodios.stream()
//                .filter(e-> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e-> System.out.println("Primeiro filtro (N/A) " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e-> System.out.println("Ordenado " + e))
//                .limit(10)
//                .peek(e-> System.out.println("Limite " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e-> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

//        System.out.println();
//
//        // Transformando os DadosEpisodio em Episodio (o .map aparentemente faz isso, ele transforma os dados)
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t-> t.episodios().stream()
                        .map(dadosEpisodio -> new Episodio(t.numero(), dadosEpisodio)))
                .toList();

        episodios.forEach(System.out::println);

        System.out.println();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(episodio -> episodio.getTitulo().toLowerCase().contains("bastards"))
                .findFirst();

        if (episodioBuscado.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println(episodioBuscado.get());
        }
//
//        System.out.println("Filtrar a partir do ano:");
//        var ano = scanner.nextInt();
//        scanner.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, Month.JANUARY, 1);
//        episodios.stream()
//                .filter(e-> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> {
//                    System.out.println(
//                            "Temporada: " + e.getTemporada() +
//                            " Episodio: " + e.getNumero() +
//                            " Data lançamento: " + e.getDataLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
//                    );
//                });

    }
}
