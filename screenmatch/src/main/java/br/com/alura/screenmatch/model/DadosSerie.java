package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title")        String titulo,
                         @JsonAlias("Genre")        String genero,
                         @JsonAlias("Actors")       String atores,
                         @JsonAlias("Poster")       String poster,
                         @JsonAlias("Plot")         String sinopse,
                         @JsonAlias("totalSeasons") int totalTemporadas,
                         @JsonAlias("imdbRating")   String avaliacao){
    @Override
    public String toString() {
        return "Title: "+this.titulo
                +"\nGenre: "+this.genero
                +"\ntotalSeasons: "+this.totalTemporadas
                +"\nActors: "+this.atores
                +"\nPoster: "+this.poster
                +"\nPlot: "+this.sinopse
                +"\nimdbRating: "+this.avaliacao;
    }
}