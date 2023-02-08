package local.appfood.api.controller;

import static local.appfood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static local.appfood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import local.appfood.domain.model.Cozinha;
import local.appfood.domain.model.Restaurante;
import local.appfood.domain.repository.CozinhaRepository;
import local.appfood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteFrete(String nome){

        return restauranteRepository.findComFreteGratis(nome);
    }
}
