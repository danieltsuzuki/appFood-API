package local.appfood.api.controller;

import local.appfood.domain.exception.EntidadeEmUsoExcpetion;
import local.appfood.domain.exception.EntidadeNaoEncontradaException;
import local.appfood.domain.model.Cidade;
import local.appfood.domain.repository.CidadeRepository;
import local.appfood.domain.service.CadastroCidadeService;
import local.appfood.domain.service.CadastroEstadoService;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId){
        Optional<Cidade> cidade = cidadeRepository.findById(cidadeId);
        if(cidade.isPresent()){
            return ResponseEntity.ok(cidade.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Cidade adicionar(@RequestBody Cidade cidade){
        return cadastroCidadeService.salvar(cidade);
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade){
        Optional<Cidade> cidadeAtual = cidadeRepository.findById(cidadeId);
        if(cidadeAtual.isPresent()){
            BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");
            cadastroCidadeService.salvar(cidadeAtual.get());
            return ResponseEntity.ok(cidadeAtual.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> remover(@PathVariable Long cidadeId){
        try{
            cadastroCidadeService.excluir(cidadeId);
            return ResponseEntity.noContent().build();
        }catch(EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(EntidadeEmUsoExcpetion e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
