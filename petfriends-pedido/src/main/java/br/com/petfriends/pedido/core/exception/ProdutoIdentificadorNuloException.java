package br.com.petfriends.pedido.core.exception;

public final class ProdutoIdentificadorNuloException extends CoreException {
    public ProdutoIdentificadorNuloException() {
        super("produto.id.nulo", "O identificador do produto não pode ser nulo.");
    }
}
