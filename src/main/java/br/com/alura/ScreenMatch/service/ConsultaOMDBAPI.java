package br.com.alura.ScreenMatch.service;

import br.com.alura.ScreenMatch.model.Categoria;
import br.com.alura.ScreenMatch.model.Episodio;
import br.com.alura.ScreenMatch.model.Serie;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class ConsultaOMDBAPI {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private record DadosSerie(@JsonAlias("Title") String titulo,
                             @JsonAlias("totalSeasons") Integer totalTemporadas,
                             @JsonAlias("imdbRating") String avaliacao,
                             @JsonAlias("Genre") String genero,
                             @JsonAlias("Year") String ano,
                             @JsonAlias("Poster") String poster,
                             @JsonAlias("Actors") String atores,
                             @JsonAlias("Plot") String sinopse) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record DadosEpisodio(@JsonAlias("Title") String titulo,
                                @JsonAlias("Episode") Integer numero,
                                @JsonAlias("imdbRating") String avaliacao,
                                @JsonAlias("Released") String dataLancamento) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record DadosTemporada(@JsonAlias("Season") Integer numero,
                                 @JsonAlias("Episodes") List<DadosEpisodio> episodios) {
    }

    private static final String ENDERECO = "https://www.omdbapi.com/?";
    private static final String API_KEY = "apikey=6585022c";
    private static String urlAPI = "";
    private static String json;

    public static Serie buscaSeriePeloNome(String nomeSerie) {
        urlAPI = ENDERECO + API_KEY + "&t=" + nomeSerie.replace(" ", "+");
        json  = ConsumoAPI.getDados(urlAPI);
        DadosSerie dadosSerie = ConverteDados.getDados(json, DadosSerie.class);
        Serie serie = new Serie();
        serie.setTitulo(dadosSerie.titulo());
        serie.setTotalTemporadas(dadosSerie.totalTemporadas());
        serie.setAvaliacao(OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0d));
        serie.setGenero(Categoria.fromString(dadosSerie.genero().split(",")[0].trim()));
        serie.setAno(dadosSerie.ano());
        serie.setPoster(dadosSerie.poster());
        serie.setAtores(dadosSerie.atores());
        serie.setSinopse(ConsultaMyMemoryAPI.traduzir(dadosSerie.sinopse())); // ConsultaChatGPT.traduzir(dadosSerie.sinopse()).trim();
        return serie;
    }

    public static List<Episodio> buscaEpisodiosSerie(Serie serie) {
        List<Episodio> episodios = new ArrayList<>();
        for (int idx = 1; idx <= serie.getTotalTemporadas(); idx++) {
            urlAPI  = ENDERECO + API_KEY + "&t=" + serie.getTitulo().replace(" ", "+") + "&season=" + idx;
            json = ConsumoAPI.getDados(urlAPI);
            DadosTemporada dadosTemporada = ConverteDados.getDados(json, DadosTemporada.class);
            dadosTemporada.episodios().stream()
                    .forEach(e-> {
                        Episodio episodio = new Episodio();
                        episodio.setTemporada(dadosTemporada.numero());
                        episodio.setTitulo(e.titulo());
                        episodio.setNumero(e.numero());
                        try {
                            episodio.setAvaliacao(Double.parseDouble(e.avaliacao()));
                        }
                        catch (NumberFormatException ex) {
                            episodio.setAvaliacao(0d);
                        }
                        try {
                            episodio.setDataLancamento(LocalDate.parse(e.dataLancamento()));
                        }
                        catch (DateTimeParseException ex) {
                            episodio.setDataLancamento(null);
                        }
                        episodios.add(episodio);
                    });
        }
        return episodios;
    }
}
