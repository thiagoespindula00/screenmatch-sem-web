package br.com.alura.ScreenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") Double avaliacao,
                         @JsonAlias("Genre") String genero,
                         @JsonAlias("Year") String ano) {

    @Override
    public String toString() {
        return "Titulo: " + this.titulo() + "\n" +
                "Total temporadas: " + this.totalTemporadas() + "\n" +
                "Avaliacao: " + this.avaliacao() + "\n" +
                "Genero: " + this.genero() + "\n" +
                "Ano: " + this.ano() + "\n";
    }
}
