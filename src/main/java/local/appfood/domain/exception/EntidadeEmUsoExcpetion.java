package local.appfood.domain.exception;

public class EntidadeEmUsoExcpetion extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntidadeEmUsoExcpetion(String mensagem){
        super(mensagem);
    }

}
