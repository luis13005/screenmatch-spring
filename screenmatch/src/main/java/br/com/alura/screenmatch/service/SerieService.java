package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> retornaTodasSeries(){
        return obtemDado(serieRepository.findAll());
    }

    public List<SerieDTO> retornaTop5(){
        return obtemDado(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> retornaRecentes(){
        return obtemDado(serieRepository.buscaRecentes());
    }

    public SerieDTO retornaSerie(Long id) {
        Optional<Serie> optionalSerie = serieRepository.findById(id);
        System.out.println(optionalSerie);
        if (optionalSerie.isPresent()) {
            Serie s = optionalSerie.get();
            return new SerieDTO(s.getSerieId(),s.getTitulo(),s.getTotalTemporadas(),s.getAvaliacao(),s.getGenero(),s.getAtores(),
                    s.getPoster(), s.getSinopse());
        }
        return null;
    }

    private List<SerieDTO> obtemDado(List<Serie> serieList){
        return serieList.stream()
                .map(s -> new SerieDTO(s.getSerieId(),s.getTitulo(),s.getTotalTemporadas(),s.getAvaliacao(),s.getGenero(),s.getAtores(),s.getPoster(),
                        s.getSinopse())).collect(Collectors.toList());
    }
}
