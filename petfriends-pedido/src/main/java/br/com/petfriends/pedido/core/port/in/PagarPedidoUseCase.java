package br.com.petfriends.pedido.core.port.in;

import br.com.petfriends.pedido.core.command.PagarPedidoCommand;

import java.util.concurrent.CompletableFuture;

public interface PagarPedidoUseCase {
    CompletableFuture<Void> pagar(PagarPedidoCommand command);
}
