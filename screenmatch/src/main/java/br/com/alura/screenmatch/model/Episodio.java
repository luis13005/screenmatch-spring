package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private int numeroTemporada;
    private String titulo;
    private int numeroEpisodio;
    private LocalDate dataLancamento;
    private double avaliacao;

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

    @Override
    public String toString() {
        return  "Temporada=" + numeroTemporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", dataLancamento=" + dataLancamento +
                ", avaliacao=" + avaliacao;
    }
}