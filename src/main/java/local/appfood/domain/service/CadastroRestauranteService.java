package local.appfood.domain.service;

import local.appfood.domain.exception.EntidadeNaoEncontradaException;
import local.appfood.domain.model.Cozinha;
import local.appfood.domain.model.Restaurante;
import local.appfood.domain.repository.CozinhaRepository;
import local.appfood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
        if(cozinha == null){
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Não existe cadastro de cozinha com " +
                                    "códiigo %d"
                            , cozinhaId));
        }
        restaurante.setCozinha(cozinha);
        return restauranteRepository.salvar(restaurante);
    }
}
