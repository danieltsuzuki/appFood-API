package local.appfood.domain.service;

import local.appfood.domain.exception.EntidadeEmUsoExcpetion;
import local.appfood.domain.exception.EntidadeNaoEncontradaException;
import local.appfood.domain.model.Estado;
import local.appfood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {
    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado){
        return estadoRepository.salvar(estado);
    }

    public void excluir(Long estadoId){
        try{
            estadoRepository.remover(estadoId);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha " +
                            "com o código %d "
                    , estadoId));
        }catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoExcpetion(String.format("Cozinha de código %d não " +
                            "pode ser removida, pois " +
                            "está em uso",
                    estadoId ));
        }
    }
}
