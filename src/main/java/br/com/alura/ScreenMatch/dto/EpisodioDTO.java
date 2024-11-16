package br.com.alura.ScreenMatch.dto;

import java.time.LocalDate;

public record EpisodioDTO(

        Integer temporada,
        String titulo,
        Integer numeroEpisodio) {
}
