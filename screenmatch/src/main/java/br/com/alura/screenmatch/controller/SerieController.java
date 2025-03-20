package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/serie")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> series(){
        return serieService.retornaTodasSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> top5(){
        return serieService.retornaTop5();
    }

    @GetMapping("/lancamento")
    public List<SerieDTO> lancamentos(){
        return serieService.retornaRecentes();
    }

    @GetMapping("/{id}")
    public SerieDTO retornaSerie(@PathVariable Long id){
        System.out.println("RETORNA SERIE: "+id);
        return serieService.retornaSerie(id);
    }

    @GetMapping("/inicio")
    public String inicio(){
        return "Inicio";
    }
}