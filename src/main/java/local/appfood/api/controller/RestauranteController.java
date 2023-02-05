package local.appfood.api.controller;

import jakarta.persistence.EntityManager;
import local.appfood.domain.exception.EntidadeNaoEncontradaException;
import local.appfood.domain.model.Cozinha;
import local.appfood.domain.model.Restaurante;
import local.appfood.domain.repository.RestauranteRepository;
import local.appfood.domain.service.CadastroCozinhaService;
import local.appfood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<?> buscar(@PathVariable Long restauranteId){
        Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
        if(restaurante.isPresent()){
            return ResponseEntity.ok().body(restaurante.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
        try{
            restaurante =  cadastroRestauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        }
        catch(EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

        if(restauranteAtual.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
        cadastroRestauranteService.salvar(restauranteAtual.get());
        return ResponseEntity.ok(restauranteAtual.get());
    }


}
