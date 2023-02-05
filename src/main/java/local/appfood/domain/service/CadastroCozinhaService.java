package local.appfood.domain.service;

import local.appfood.domain.exception.EntidadeEmUsoExcpetion;
import local.appfood.domain.exception.EntidadeNaoEncontradaException;
import local.appfood.domain.model.Cozinha;
import local.appfood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {
    @Autowired
    private CozinhaRepository cozinhaRepository;
    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId){
        try{
            cozinhaRepository.deleteById(cozinhaId);
        }
        catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cozinha com o código %d", cozinhaId));
        }
        catch(DataIntegrityViolationException  e){
            throw new EntidadeEmUsoExcpetion(
                    String.format("Cozinha de código %d não pode ser removida, pois está em uso", cozinhaId));
        }
    }
}
