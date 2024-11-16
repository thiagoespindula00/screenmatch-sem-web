package br.com.alura.ScreenMatch.repository;

import br.com.alura.ScreenMatch.model.Categoria;
import br.com.alura.ScreenMatch.model.Episodio;
import br.com.alura.ScreenMatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<List<Serie>> findByTituloContainingIgnoreCase(String titulo);
    Optional<List<Serie>> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avalicao);

    Optional<List<Serie>> findTop5ByOrderByAvaliacaoDesc();

    Optional<List<Serie>> findByGenero(Categoria categoria);

    Optional<List<Serie>> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer totalTemporadas, Double avalicao);

    // Usando JPQL
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :qtdTemporadas AND s.avaliacao >= :ava")
    Optional<List<Serie>> seriesPorTemporadaEAvaliacao(Integer qtdTemporadas, Double ava);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:tituloEpisodio%")
    Optional<List<Episodio>> episodiosPorTitulo(String tituloEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :idSerie ORDER BY e.avaliacao DESC LIMIT 5")
    Optional<List<Episodio>> top5EpisodiosSerie(Long idSerie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :idSerie AND YEAR(e.dataLancamento) >= :anoLancamento")
    Optional<List<Episodio>> episodiosSeriePorAno(Long idSerie, int anoLancamento);

    @Query("SELECT s FROM Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    Optional<List<Serie>> seriesLancamento();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :idSerie")
    List<Episodio> episodiosTodasTemporadas(Long idSerie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :idSerie AND e.temporada = :numeroTemporada")
    List<Episodio> episodiosTemporada(Long idSerie, Integer numeroTemporada);
}
