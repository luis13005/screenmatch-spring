package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import jakarta.persistence.*;

import java.util.List;

public record SerieDTO(Long serieId,
                       String titulo,
                       Categoria genero,
                       String atores,
                       String poster,
                       String sinopse,
                       Integer totalTemporadas,
                       Double avaliacao) {
}
