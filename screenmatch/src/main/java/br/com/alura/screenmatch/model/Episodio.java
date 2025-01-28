package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long episodio_id;
    private int numeroTemporada;
    private String titulo;
    private int numeroEpisodio;
    private LocalDate dataLancamento;
    private double avaliacao;
    @ManyToOne
    @JoinColumn(name = "serieId")
    private Serie serie;

    public Episodio(){}

    public Episodio(int numeroTemporada,DadosEpisodio episodio){

        this.numeroTemporada = numeroTemporada;
        this.titulo = episodio.titulo();
        this.numeroEpisodio = episodio.numero();

        try {
            this.dataLancamento = LocalDate.parse(episodio.dataLancamento());
        }catch (DateTimeParseException e){
            this.dataLancamento = null;
        }

        try {
            this.avaliacao = Double.valueOf(episodio.avaliacao());
        }catch (NumberFormatException e){
            this.avaliacao = 0;
        }
    }

    public void setNumeroTemporada(int numeroTemporada){
        this.numeroTemporada = numeroTemporada;
    }

    public int getNumeroTemporada(){
        return this.numeroTemporada;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public int getNumeroEpisodio(){
        return this.numeroEpisodio;
    }

    public void setNumeroEpisodio(int numeroEpisodio){
        this.numeroEpisodio = numeroEpisodio;
    }

  public void setDataLancamento(LocalDate dataLancamento){
        this.dataLancamento = dataLancamento;
  }

    public LocalDate getDataLancamento(){
        return this.dataLancamento;
    }

    public double getAvaliacao(){
        return this.avaliacao;
    };

    public void setAvaliacao(double avaliacao){
        this.avaliacao = avaliacao;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Serie getSerie() {
        return serie;
    }

    @Override
    public String toString() {
        return  "Temporada=" + numeroTemporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", dataLancamento=" + dataLancamento +
                ", avaliacao=" + avaliacao;
    }

    public Long getEpisodio_id() {
        return episodio_id;
    }

    public void setEpisodio_id(Long episodio_id) {
        this.episodio_id = episodio_id;
    }
}
