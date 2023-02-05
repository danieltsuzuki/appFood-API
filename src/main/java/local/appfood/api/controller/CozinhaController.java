package local.appfood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import local.appfood.domain.exception.EntidadeEmUsoExcpetion;
import local.appfood.domain.exception.EntidadeNaoEncontradaException;
import local.appfood.domain.model.Cozinha;
import local.appfood.domain.repository.CozinhaRepository;
import local.appfood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
        if(cozinha.isPresent()){
            return ResponseEntity.ok(cozinha.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha){
        Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);

        if(cozinhaAtual.isPresent()){
            BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
            cadastroCozinhaService.salvar(cozinhaAtual.get());
            return ResponseEntity.ok(cozinhaAtual.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<?> remover(@PathVariable Long cozinhaId){
        try{
            cadastroCozinhaService.excluir(cozinhaId);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(EntidadeEmUsoExcpetion e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @PatchMapping("/{cozinhaId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long cozinhaId, @RequestBody Map<String, Object> campos){
        Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);

        if(cozinhaAtual.isPresent()){
            return ResponseEntity.notFound().build();
        }
        merge(campos, cozinhaAtual.get());
        return atualizar(cozinhaId, cozinhaAtual.get());
    }

    private void merge(Map<String, Object> dadosOrigem, Cozinha cozinhaDestino){
        ObjectMapper objectMapper = new ObjectMapper();
        Cozinha cozinhaOrigem = objectMapper.convertValue(dadosOrigem, Cozinha.class);

        System.out.println(dadosOrigem);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) ->{
            Field field = ReflectionUtils.findField(Cozinha.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, cozinhaOrigem);

            System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

            ReflectionUtils.setField(field, cozinhaDestino, novoValor);
        });
    }
}
