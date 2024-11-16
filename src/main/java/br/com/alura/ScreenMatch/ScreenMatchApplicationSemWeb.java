//package br.com.alura.ScreenMatch;
//
//import br.com.alura.ScreenMatch.model.Categoria;
//import br.com.alura.ScreenMatch.model.Episodio;
//import br.com.alura.ScreenMatch.model.Serie;
//import br.com.alura.ScreenMatch.repository.SerieRepository;
//import br.com.alura.ScreenMatch.service.ConsultaOMDBAPI;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.Scanner;
//
//@SpringBootApplication
//public class ScreenMatchApplicationSemWeb implements CommandLineRunner {
//
//    private Scanner scanner = new Scanner(System.in);
//    final int OPCAO_MENU_SAIR = 0;
//    final int OPCAO_MENU_BUSCAR_SERIE_PELO_NOME = 1;
//    final int OPCAO_MENU_BUSCAR_EPISODIOS_POR_SERIE = 2;
//    final int OPCAO_MENU_MOSTRAR_TODAS_AS_SERIES = 3;
//    final int OPCAO_MENU_BUSCAR_SERIE_POR_TITULO = 4;
//    final int OPCAO_MENU_BUSCAR_SERIES_POR_ATOR = 5;
//    final int OPCAO_MENU_BUSCAR_TOP_5_SERIES = 6;
//    final int OPCAO_MENU_BUSCAR_POR_CATEGORIA = 7;
//    final int OPCAO_MENU_BUSCAR_SERIES_POR_NUMERO_TEMPORADAS_E_AVALIACAO = 8;
//    final int OPCAO_MENU_BUSCAR_EPISODIO_POR_TITULO = 9;
//    final int OPCAO_MENU_BUSCAR_TOP_5_EPISODIOS_POR_SERIE = 10;
//    final int OPCAO_MENU_BUSCAR_EPISODIOS_A_PARTIR_DE_DATA = 11;
//    @Autowired
//    private SerieRepository serieRepository;
//
//    public void exibeMenu() {
//        System.out.println(OPCAO_MENU_BUSCAR_SERIE_PELO_NOME + " - Buscar serie pelo nome");
//        System.out.println(OPCAO_MENU_BUSCAR_EPISODIOS_POR_SERIE + " - Buscar episodio por serie");
//        System.out.println(OPCAO_MENU_MOSTRAR_TODAS_AS_SERIES + " - Mostrar todas as series");
//        System.out.println(OPCAO_MENU_BUSCAR_SERIE_POR_TITULO + " - Buscar série por titulo");
//        System.out.println(OPCAO_MENU_BUSCAR_SERIES_POR_ATOR + " - Buscar séries por ator");
//        System.out.println(OPCAO_MENU_BUSCAR_TOP_5_SERIES + " - Buscar as top 5 séries");
//        System.out.println(OPCAO_MENU_BUSCAR_POR_CATEGORIA + " - Buscar séries por categoria");
//        System.out.println(OPCAO_MENU_BUSCAR_SERIES_POR_NUMERO_TEMPORADAS_E_AVALIACAO + " - Buscar séries por nº temporadas e avaliação");
//        System.out.println(OPCAO_MENU_BUSCAR_EPISODIO_POR_TITULO + " - Buscar episódio por titulo");
//        System.out.println(OPCAO_MENU_BUSCAR_TOP_5_EPISODIOS_POR_SERIE + " - Buscar os top 5 episódios da série");
//        System.out.println(OPCAO_MENU_BUSCAR_EPISODIOS_A_PARTIR_DE_DATA + " - Buscar episódios a partir de uma data");
//        System.out.println(OPCAO_MENU_SAIR + " - Sair");
//        System.out.println("Digite a opcao desejada");
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ScreenMatchApplicationSemWeb.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
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
//                    Serie serie = ConsultaOMDBAPI.buscaSeriePeloNome(nomeSerie);
//                    serieRepository.save(serie);
//				}
//				break;
//
//                case OPCAO_MENU_BUSCAR_EPISODIOS_POR_SERIE:{
//                    List<Serie> series = serieRepository.findAll();
//                    series.stream()
//                            .sorted(Comparator.comparing(Serie::getId))
//                            .forEach(System.out::println);
//                    System.out.println("Escolha uma série informando o id:");
//                    Long idSerie = scanner.nextLong();
//
//                    Optional<Serie> serie = series.stream().filter(s -> s.getId().equals(idSerie)).findFirst();
//                    if (serie.isEmpty()) {
//                        System.out.println("Serie não encontrada");
//                        break;
//                    }
//
//                    System.out.println("Serie escolhida -> " + serie);
//                    List<Episodio> episodios = ConsultaOMDBAPI.buscaEpisodiosSerie(serie.get());
//                    serie.get().setEpisodios(episodios);
//                    serieRepository.save(serie.get());
//                }
//                break;
//
//				case OPCAO_MENU_MOSTRAR_TODAS_AS_SERIES:
//                    serieRepository.findAll().stream()
//                            .forEach(System.out::println);;
//					break;
//
//                case OPCAO_MENU_BUSCAR_SERIE_POR_TITULO: {
//                    System.out.println("Digite o titulo da serie:");
//                    scanner.nextLine();
//                    String titulo = scanner.nextLine();
//                    Optional<List<Serie>> seriesEncontradas = serieRepository.findByTituloContainingIgnoreCase(titulo);
//                    if (seriesEncontradas.isEmpty()) {
//                        System.out.println("Nenhuma série encontrada com os parâmetros informados");
//                        break;
//                    }
//                    System.out.println("Series encontradas:");
//                    seriesEncontradas.get().forEach(s -> System.out.println(s.getTitulo()));
//                }
//                break;
//
//                case OPCAO_MENU_BUSCAR_SERIES_POR_ATOR:
//                {
//                    System.out.println("Digite o nome do ator:");
//                    scanner.nextLine();
//                    String nomeAtor = scanner.nextLine();
//                    System.out.println("Avaliações a partir do valor:");
//                    Double avaliacoes = scanner.nextDouble();
//
//                    Optional<List<Serie>> seriesEncontradas = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacoes);
//                    if (seriesEncontradas.isEmpty()) {
//                        System.out.println("Nenhuma série encontrada com os parâmetros informados");
//                        break;
//                    }
//                    System.out.println("Series encontradas:");
//                    seriesEncontradas.get().forEach(s -> System.out.println(s.getTitulo() + " atores " + s.getAtores()));
//                }
//                break;
//
//                case OPCAO_MENU_BUSCAR_TOP_5_SERIES:{
//                    Optional<List<Serie>> seriesEncontradas = serieRepository.findTop5ByOrderByAvaliacaoDesc();
//                    if (seriesEncontradas.isEmpty()) {
//                        System.out.println("Nenhuma série encontrada com os parâmetros informados");
//                        break;
//                    }
//
//                    seriesEncontradas.get().forEach(s -> System.out.println(s.getTitulo() + " avalicao " + s.getAvaliacao()));
//                }
//                break;
//
//                case OPCAO_MENU_BUSCAR_POR_CATEGORIA:
//                {
//                    System.out.println("Categorias");
//                    for (Categoria categoria : Categoria.values()) {
//                        System.out.println(categoria);
//                    }
//                    scanner.nextLine();
//                    System.out.println("Informe a categoria:");
//                    String categoria = scanner.nextLine();
//                    Optional<List<Serie>> seriesEncontradas = serieRepository.findByGenero(Categoria.fromString(categoria));
//                    if (seriesEncontradas.isEmpty()) {
//                        System.out.println("Nenhuma série encontrada com os parâmetros informados");
//                        break;
//                    }
//                    seriesEncontradas.get().forEach(s-> System.out.println(s.getTitulo() + " categoria " + s.getGenero()));
//                }
//                break;
//                case OPCAO_MENU_BUSCAR_SERIES_POR_NUMERO_TEMPORADAS_E_AVALIACAO:
//                {
//                    System.out.println("Séries até quantas temporadas?");
//                    Integer qtdTemporadas = scanner.nextInt();
//                    System.out.println("Séries com uma avaliação mínima de quanto?");
//                    Double avaliacaoMinima = scanner.nextDouble();
//                    Optional<List<Serie>> seriesEncontradas = serieRepository.seriesPorTemporadaEAvaliacao(qtdTemporadas, avaliacaoMinima);
//                    // serieRepository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(qtdTemporadas, avaliacaoMinima);
//                    if (seriesEncontradas.isEmpty()) {
//                        System.out.println("Nenhuma série encontrada com os parâmetros informados");
//                        break;
//                    }
//                    seriesEncontradas.get().forEach(s-> System.out.println(s.getTitulo() + " qtd. temporada " + s.getTotalTemporadas() + " avaliacao " + s.getAvaliacao()));
//                }
//                break;
//
//                case OPCAO_MENU_BUSCAR_EPISODIO_POR_TITULO:
//                {
//                    System.out.println("Digite o titulo do episodio:");
//                    scanner.nextLine();
//                    String titulo = scanner.nextLine();
//
//                    Optional<List<Episodio>> episodiosEncontradas = serieRepository.episodiosPorTitulo(titulo);
//                    if (episodiosEncontradas.isEmpty()) {
//                        System.out.println("Nenhum episódio encontrado com os parâmetros informados");
//                        break;
//                    }
//                    System.out.println("Episódios encontrados:");
//                    episodiosEncontradas.get().forEach(e -> System.out.println("ep. " + e.getNumero() + " - " + e.getTitulo() + " série " + e.getSerie().getTitulo() + "(id " + e.getSerie().getId() + ")" + " temp." + e.getTemporada()));
//
//                }
//                break;
//
//                case OPCAO_MENU_BUSCAR_TOP_5_EPISODIOS_POR_SERIE:
//                {
//                    System.out.println("Séries");
//                    serieRepository.findAll().stream()
//                            .sorted(Comparator.comparing(Serie::getId))
//                            .forEach(s-> System.out.println("Id " + s.getId() + " - " + s.getTitulo()));
//                    System.out.println("Informe o id da série desejada:");
//                    Long idSerie = scanner.nextLong();
//                    Optional<List<Episodio>> episodiosEncontradas = serieRepository.top5EpisodiosSerie(idSerie);
//                    if (episodiosEncontradas.isEmpty()) {
//                        System.out.println("Nenhum episódio encontrado com os parâmetros informados");
//                        break;
//                    }
//                    System.out.println("Episódios encontrados:");
//                    episodiosEncontradas.get().forEach(e -> System.out.println("ep. " + e.getNumero() + " - " + e.getTitulo() + " avaliação " + e.getAvaliacao() + " temp." + e.getTemporada()));
//                }
//                break;
//
//                case OPCAO_MENU_BUSCAR_EPISODIOS_A_PARTIR_DE_DATA:
//                {
//                    System.out.println("Séries");
//                    serieRepository.findAll().stream()
//                            .sorted(Comparator.comparing(Serie::getId))
//                            .forEach(s-> System.out.println("Id " + s.getId() + " - " + s.getTitulo()));
//                    System.out.println("Informe o id da série desejada:");
//                    Long idSerie = scanner.nextLong();
//                    System.out.println("Digite o ano limite de lançamento:");
//                    var anoLancamento = scanner.nextInt();
//                    scanner.nextLine();
//                    Optional<List<Episodio>> episodiosEncontrados = serieRepository.episodiosSeriePorAno(idSerie, anoLancamento);
//                    if (episodiosEncontrados.isEmpty()) {
//                        System.out.println("Nenhum episódio encontrado com os parâmetros informados");
//                        break;
//                    }
//                    System.out.println("Episódios encontrados:");
//                    episodiosEncontrados.get().forEach(e -> System.out.println("ep. " + e.getNumero() + " - " + e.getTitulo() + " data lançamento " + e.getDataLancamento() + " temp." + e.getTemporada()));
//                }
//                break;
//
//                default:
//                    System.out.println("Opção inválida");
//			}
//		}
//		while (opcaoMenu != OPCAO_MENU_SAIR);
//    }
//}
