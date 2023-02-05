package local.appfood.infrastructure.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import local.appfood.domain.model.Restaurante;
import local.appfood.domain.repository.EstadoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements EstadoRepository.RestauranteRepositoryQueries {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
        var jpql = "from Restaurante where nome like :nome and taxaFrete between" +
                ":taxaInicial and :taxaFinal";
        return manager.createQuery(jpql, Restaurante.class).setParameter("nome", "%" + nome + "%")
                .setParameter("taxaInicial", taxaInicial).setParameter("taxaFinal", taxaFinal)
                .getResultList();
    }
}
