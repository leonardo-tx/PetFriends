package br.com.petfriends.pedido.core.port.in;

import br.com.petfriends.pedido.core.command.EntregarPedidoCommand;

import java.util.concurrent.CompletableFuture;

public interface EntregarPedidoUseCase {
    CompletableFuture<Void> entregar(EntregarPedidoCommand command);
}
