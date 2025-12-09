package br.com.petfriends.pedido.core.port.in;

import br.com.petfriends.pedido.core.command.AdicionarEntregaAoPedidoCommand;

import java.util.concurrent.CompletableFuture;

public interface AdicionarEntregaAoPedidoUseCase {
    CompletableFuture<Void> adicionarEntrega(AdicionarEntregaAoPedidoCommand command);
}
