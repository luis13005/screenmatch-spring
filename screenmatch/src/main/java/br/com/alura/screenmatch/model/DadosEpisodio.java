package br.com.alura.screenmatch.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                           @JsonAlias("Episode") int numero,
                           @JsonAlias("Released") String dataLancamento,
                            @JsonAlias("ImbdRating") String avaliacao) {
}