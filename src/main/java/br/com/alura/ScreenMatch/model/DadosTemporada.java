package br.com.alura.ScreenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") Integer numero,
                             @JsonAlias("Episodes") List<DadosEpisodio> episodios) {

    @Override
    public String toString() {
        String toString = "Temporada: " + this.numero() + "\n";
        for (DadosEpisodio episodio : episodios) {
            toString += episodio.toString() + "\n";
        }
        return toString;
    }
}
