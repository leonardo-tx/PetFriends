package br.com.petfriends.pedido.core.port.in;

import br.com.petfriends.pedido.core.command.CancelarPedidoCommand;

import java.util.concurrent.CompletableFuture;

public interface CancelarPedidoUseCase {
    CompletableFuture<Void> cancelar(CancelarPedidoCommand command);
}
