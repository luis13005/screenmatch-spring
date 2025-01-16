package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultaChatGPT;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

public class Serie {

    private String titulo;
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;
    private Integer totalTemporadas;
    private Double avaliacao;

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.genero = Categoria.fromString(dadosSerie.genero().split(", ")[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        this.sinopse = ConsultaChatGPT.Consultar(dadosSerie.sinopse()).trim();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "\nTitle: "+this.titulo
                +"\nGenre: "+this.genero
                +"\ntotalSeasons: "+this.totalTemporadas
                +"\nActors: "+this.atores
                +"\nPoster: "+this.poster
                +"\nPlot: "+this.sinopse
                +"\nimdbRating: "+this.avaliacao;
    }
}
