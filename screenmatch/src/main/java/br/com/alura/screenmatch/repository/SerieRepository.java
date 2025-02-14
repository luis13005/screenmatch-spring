package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    Optional<Serie> findSerieByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);
    Optional<Serie> findTop5ByOrderByAvaliacaoDesc();
    Optional<Serie> findByGenero(Categoria nomeGenero);
    Optional<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int temporadas,double avaliacao);
    @Query("select s from Serie s where s.totalTemporadas <= :totalTemporada and s.avaliacao >= :avaliacao")
    List<Serie> findSerieTemporadaAvalicao(Integer totalTemporada,Double avaliacao);
    @Query("SELECT DISTINCT e FROM Serie s INNER JOIN episodios e WHERE e.titulo ILIKE %:trechoEp%")
    List<Episodio> consultaEpisodioTrecho(String trechoEp);
    @Query("SELECT e FROM Serie s JOIN episodios e where s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> top5EpisodiosSerie(Serie serie);
}