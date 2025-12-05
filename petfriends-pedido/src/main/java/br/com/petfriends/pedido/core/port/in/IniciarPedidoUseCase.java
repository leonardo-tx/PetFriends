package br.com.petfriends.pedido.core.port.in;

import br.com.petfriends.pedido.core.command.IniciarPedidoCommand;

import java.util.concurrent.CompletableFuture;

public interface IniciarPedidoUseCase {
    CompletableFuture<Void> iniciar(IniciarPedidoCommand command);
}
