package local.appfood.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import local.appfood.domain.model.Cozinha;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class CozinhaXmlWrapper {
    @JsonProperty("cozinha")
    @JacksonXmlElementWrapper(useWrapping = false)
    @NonNull
    private List<Cozinha> cozinhas;
}
