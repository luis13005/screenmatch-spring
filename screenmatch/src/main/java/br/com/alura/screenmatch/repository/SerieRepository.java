package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    Optional<Serie> findSerieByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);
    List<Serie> findTop5ByOrderByAvaliacaoDesc();
    Optional<Serie> findByGenero(Categoria nomeGenero);
    Optional<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int temporadas,double avaliacao);
    @Query("select s from Serie s where s.totalTemporadas <= :totalTemporada and s.avaliacao >= :avaliacao")
    List<Serie> findSerieTemporadaAvalicao(Integer totalTemporada,Double avaliacao);
    @Query("SELECT DISTINCT e FROM Serie s INNER JOIN episodios e WHERE e.titulo ILIKE %:trechoEp%")
    List<Episodio> consultaEpisodioTrecho(String trechoEp);
    @Query("SELECT e FROM Serie s JOIN episodios e where s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> top5EpisodiosSerie(Serie serie);
    @Query("SELECT e from Serie s INNER JOIN episodios e where s = :serie AND YEAR(e.dataLancamento) >= :ano ORDER BY e.dataLancamento")
    List<Episodio> buscaEpisodiosApartirDeSerieData(Serie serie, int ano);
    @Query(value = "select s.* from serie s " +
            "inner join episodio e on s.serie_id = e.serie_id "+
            "group by s.serie_id, s.serie_id"+
            " order by max(e.data_lancamento)",nativeQuery = true)
    List<Serie> lancamentosMaisRecentes();
}
//@Query("SELECT s FROM Serie s JOIN s.episodios e  ORDER BY e.dataLancamento DESC LIMIT 10")
//    //GROUP BY
//    List<Serie> buscaRecentes();