package local.appfood.api.controller;

import local.appfood.domain.model.Cozinha;
import local.appfood.domain.model.Restaurante;
import local.appfood.domain.repository.CozinhaRepository;
import local.appfood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/teste")
public class TesteController {
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(String nome){
        return cozinhaRepository.findByNomeContaining(nome);
    }
    @GetMapping("/restaurantes/por-taxa")
    public List<Restaurante> restaurantesPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantesPorNome(String nome, Long cozinhaId){
        return restauranteRepository.consultarPorNomeEId(nome, cozinhaId);
    }


    @GetMapping("restaurantes/por-nome-e-frete")
    public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }
}
