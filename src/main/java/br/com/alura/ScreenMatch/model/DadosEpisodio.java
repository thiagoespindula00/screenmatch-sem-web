package br.com.alura.ScreenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                            @JsonAlias("Episode") Integer numero,
                            @JsonAlias("imdbRating") String avaliacao,
                            @JsonAlias("Released") String dataLancamento) {

    @Override
    public String toString() {
        return "Titulo Episodio: " + this.titulo() + "\n" +
                "Numero: " + this.numero() + "\n" +
                "Avalicao: " + this.avaliacao() + "\n" +
                "Data lancamento: " + this.dataLancamento();
    }
}
