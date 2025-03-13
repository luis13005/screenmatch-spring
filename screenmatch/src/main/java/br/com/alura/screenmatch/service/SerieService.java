package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> retornaTodasSeries(){
        return serieRepository.findAll().stream()
                .map(s -> new SerieDTO(s.getSerieId(),s.getTitulo(),s.getGenero(),s.getAtores(),s.getPoster(),
                        s.getSinopse(),s.getTotalTemporadas(),s.getAvaliacao())).collect(Collectors.toList());
    }
}
