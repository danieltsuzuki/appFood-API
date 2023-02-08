package local.appfood.domain.repository;

import local.appfood.domain.model.Estado;
import local.appfood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    interface RestauranteRepositoryQueries {
        List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

        List<Restaurante> findComFreteGratis(String nome);
    }
}
