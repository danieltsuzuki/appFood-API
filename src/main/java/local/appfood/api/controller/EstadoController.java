package local.appfood.api.controller;

import local.appfood.domain.model.Estado;
import local.appfood.domain.repository.EstadoRepository;
import local.appfood.infrastructure.repository.EstadoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    @Autowired
    private EstadoRepository estadoRepository;
    @GetMapping
    public List<Estado> listar(){
        return estadoRepository.listar();
    }

    @PostMapping
    public Estado adicionar(@RequestBody Estado estado){
        return estadoRepository.salvar(estado);
    }
}
