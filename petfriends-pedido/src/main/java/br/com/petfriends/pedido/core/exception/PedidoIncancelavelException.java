package br.com.petfriends.pedido.core.exception;

public final class PedidoIncancelavelException extends CoreException {
    public PedidoIncancelavelException() {
        super("pedido.incancelavel", "O pedido precisa estar pendente para ser cancelado.");
    }
}
