package backend.cohive.Estoque.service;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
