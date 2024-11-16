package br.com.alura.ScreenMatch.service;

import br.com.alura.ScreenMatch.dto.EpisodioDTO;
import br.com.alura.ScreenMatch.dto.SerieDTO;
import br.com.alura.ScreenMatch.model.Categoria;
import br.com.alura.ScreenMatch.model.Serie;
import br.com.alura.ScreenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream().map(s-> new SerieDTO(
                s.getId(),
                s.getTitulo(),
                s.getTotalTemporadas(),
                s.getAvaliacao(),
                s.getGenero(),
                s.getAno(),
                s.getPoster(),
                s.getAtores(),
                s.getSinopse()
        )).collect(Collectors.toList());
    }

    public List<SerieDTO> getTodasAsSeries() {
        return converteDados(serieRepository.findAll());
    }

    public List<SerieDTO> getTop5Series() {
        return converteDados(serieRepository.findTop5ByOrderByAvaliacaoDesc().get());

    }

    public List<SerieDTO> getLancamentos() {
        return converteDados(serieRepository.seriesLancamento().get());
    }

    public SerieDTO getSerie(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId(),
                    s.getTitulo(),
                    s.getTotalTemporadas(),
                    s.getAvaliacao(),
                    s.getGenero(),
                    s.getAno(),
                    s.getPoster(),
                    s.getAtores(),
                    s.getSinopse());
        }

        return null;
    }

    public List<EpisodioDTO> getTodasTemporadasSerie(Long id) {
        return serieRepository.episodiosTodasTemporadas(id).stream().map(e -> new EpisodioDTO(
                e.getTemporada(),
                e.getTitulo(),
                e.getNumero()
        )).collect(Collectors.toList());
    }
    public List<EpisodioDTO> getEpisodiosTemporada(Long idSerie, Integer numeroTemporada) {
        return serieRepository.episodiosTemporada(idSerie, numeroTemporada).stream().map(e -> new EpisodioDTO(
                e.getTemporada(),
                e.getTitulo(),
                e.getNumero()
        )).collect(Collectors.toList());
    }

    public List<SerieDTO> getSeriesPorCategoria(String nomeCategoria) {
        return converteDados(serieRepository.findByGenero(Categoria.fromString(nomeCategoria)).get());
    }

    public List<EpisodioDTO> getTop5EpisodiosTemporada(Long id) {
        return serieRepository.top5EpisodiosSerie(id).get().stream().map(e-> new EpisodioDTO(
                e.getTemporada(),
                e.getTitulo(),
                e.getNumero()
        )).collect(Collectors.toList());
    }
}
