package local.appfood.domain.repository;

import local.appfood.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
