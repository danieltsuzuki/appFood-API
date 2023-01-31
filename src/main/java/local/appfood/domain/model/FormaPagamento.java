package local.appfood.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
}
