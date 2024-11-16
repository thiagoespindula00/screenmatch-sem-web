package br.com.alura.ScreenMatch.controller;

import br.com.alura.ScreenMatch.dto.EpisodioDTO;
import br.com.alura.ScreenMatch.dto.SerieDTO;
import br.com.alura.ScreenMatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    private List<SerieDTO> getSeries() {
        return serieService.getTodasAsSeries();
    }

    @GetMapping("/top5")
    private List<SerieDTO> getTop5() {
        return serieService.getTop5Series();
    }

    @GetMapping("/lancamentos")
    private List<SerieDTO> getLancamentos() {
        return serieService.getLancamentos();
    }

    @GetMapping("/{id}")
    private SerieDTO getSerie(@PathVariable Long id) {
        return serieService.getSerie(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    private List<EpisodioDTO> getTodasTemporadasSerie(@PathVariable Long id) {
        return serieService.getTodasTemporadasSerie(id);
    }

    @GetMapping("/{id}/temporadas/top")
    private List<EpisodioDTO> getTop5EpisodiosTemporada(@PathVariable Long id) {
        return serieService.getTop5EpisodiosTemporada(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    private List<EpisodioDTO> getEpisodiosTemporada(@PathVariable Long id, @PathVariable Integer numeroTemporada) {
        return serieService.getEpisodiosTemporada(id, numeroTemporada);
    }

    @GetMapping("/categoria/{nomeCategoria}")
    private List<SerieDTO> getSeriesPorCategoria(@PathVariable String nomeCategoria) {
        return serieService.getSeriesPorCategoria(nomeCategoria);
    }
}
