package br.com.alura.ScreenMatch.dto;

import br.com.alura.ScreenMatch.model.Categoria;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double avaliacao,
        Categoria genero,
        String ano,
        String poster,
        String atores,
        String sinopse) {
}
