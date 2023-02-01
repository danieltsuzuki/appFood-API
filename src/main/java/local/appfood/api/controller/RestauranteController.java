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

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.listar();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<?> buscar(@PathVariable Long restauranteId){
        Restaurante restaurante = restauranteRepository.buscar(restauranteId);
        if(restaurante != null){
            return ResponseEntity.ok().body(restaurante);
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
        Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);

        if(restauranteAtual == null){
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
        cadastroRestauranteService.salvar(restauranteAtual);
        return ResponseEntity.ok(restauranteAtual);
    }
}
