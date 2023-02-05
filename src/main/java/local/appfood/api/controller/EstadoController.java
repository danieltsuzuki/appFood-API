package local.appfood.api.controller;

import local.appfood.domain.exception.EntidadeEmUsoExcpetion;
import local.appfood.domain.exception.EntidadeNaoEncontradaException;
import local.appfood.domain.model.Estado;
import local.appfood.domain.repository.EstadoRepository;
import local.appfood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar(){
        return estadoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado){
        return cadastroEstadoService.salvar(estado);
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId){
        Optional<Estado> estado = estadoRepository.findById(estadoId);
        if(estado.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(estado.get());
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado){
        Optional<Estado> estadoAtual = estadoRepository.findById(estadoId);
        if(estadoAtual.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
        cadastroEstadoService.salvar(estadoAtual.get());
        return ResponseEntity.ok(estadoAtual.get());
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> remover(@PathVariable Long estadoId){
        try{
            cadastroEstadoService.excluir(estadoId);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(EntidadeEmUsoExcpetion e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
