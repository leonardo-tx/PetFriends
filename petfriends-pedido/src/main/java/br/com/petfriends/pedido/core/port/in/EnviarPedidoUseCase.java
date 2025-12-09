package br.com.petfriends.pedido.core.port.in;

import br.com.petfriends.pedido.core.command.EnviarPedidoCommand;

import java.util.concurrent.CompletableFuture;

public interface EnviarPedidoUseCase {
    CompletableFuture<Void> enviar(EnviarPedidoCommand command);
}
