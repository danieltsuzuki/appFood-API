package local.appfood.domain.service;

import local.appfood.domain.exception.EntidadeEmUsoExcpetion;
import local.appfood.domain.exception.EntidadeNaoEncontradaException;
import local.appfood.domain.model.Cidade;
import local.appfood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {
    @Autowired
    private CidadeRepository cidadeRepository;
    
    public Cidade salvar(Cidade cidade){
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId){
        try{
            cidadeRepository.deleteById(cidadeId);
        }catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cozinha " +
                                    "com o código %d "
                            , cidadeId));
        }catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoExcpetion(
                    String.format("Cozinha de código %d não " +
                                    "pode ser removida, pois " +
                                    "está em uso",
                            cidadeId ));
        }
    }
}
